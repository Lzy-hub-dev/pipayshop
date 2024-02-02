package com.example.pipayshopapi.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.config.CommonConfig;
import com.example.pipayshopapi.entity.*;
import com.example.pipayshopapi.entity.dto.LoginDTO;
import com.example.pipayshopapi.entity.dto.RegisterDTO;
import com.example.pipayshopapi.entity.dto.UserRegisterDTO;
import com.example.pipayshopapi.entity.vo.ItemMinInfoVo;
import com.example.pipayshopapi.entity.vo.ResponseResultVO;
import com.example.pipayshopapi.entity.vo.UserInfoVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.mapper.*;
import com.example.pipayshopapi.service.UserInfoService;
import com.example.pipayshopapi.util.*;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.lionsoul.ip2region.xdb.Searcher;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 用户数据表 服务实现类
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService, UserDetailsService {

    private static final String REGISTER_FALSE = "注册失败，请联系后台";
    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private ItemInfoMapper itemInfoMapper;

    @Resource
    private AccountInfoMapper accountInfoMapper;

    private Searcher searcher;

    @Resource
    private LoginRecordMapper loginRecordMapper;

    @Resource
    private ShopInfoMapper shopInfoMapper;

    @Resource
    private AuthenticationManager authenticationManager;


    @Resource
    private RedisUtil<String> redisUtil;

    @Resource
    private UserRegisterMapper userRegisterMapper;

    @Resource
    ImageMapper imageMapper;


    @Resource
    RabbitTemplate rabbitTemplate;

    /**
     *  验证登录
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfoVO userInfoVO = new UserInfoVO();
        String userName=loginDTOTmp.getUserName();
        //根据用户名查询用户信息
        LambdaQueryWrapper<UserInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserInfo::getUid,username);
        UserInfo user = userInfoMapper.selectOne(wrapper);
        //如果查询不到数据就通过抛出异常来给出提示
        if(Objects.isNull(user)){
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://api.minepi.com/v2/me")
                    .addHeader("Authorization", "Bearer " + loginDTOTmp.getAccessToken())
                    .build();
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    return null;
                }
                String string = response.body().string();
                JSONObject jsonObject1 = JSON.parseObject(string);

                if (!jsonObject1.getString("username").equals(userName)) {
                    return null;
                }

                //新用户
                UserInfo newUser = new UserInfo();
                // 属性转移
                newUser.setPiName(userName);
                newUser.setUserName(userName);
                newUser.setAccessToken(loginDTOTmp.getAccessToken());
                newUser.setUid(userName);
                newUser.setUserImage(Constants.AVATAR_IMAG);
                // 插入数据
                int insert = userInfoMapper.insert(newUser);
                // 创建用户账号
                insert += accountInfoMapper.createAccount(userName);
                // 记录登录
                String ip = getIp();
                String region = getIp2Region(ip);
                LoginRecord loginRecord = new LoginRecord(userName, ip, region, new Date(),userName);
                loginRecordMapper.insert(loginRecord);

                if (insert < 2){throw new BusinessException(REGISTER_FALSE);}
                // 给新用户开一家网店
                ItemInfo itemInfo = new ItemInfo(null, StringUtil.generateShortId(), userName, false, null, 0.0, null, null,
                        null, userName, 0, Constants.AVATAR_IMAG, 1);
                int insert1 = itemInfoMapper.insert(itemInfo);
                if (insert1 < 1) {
                    log.error("给新用户开一家网店失败");
                    throw new RuntimeException();
                }

                BeanUtils.copyProperties(newUser,userInfoVO);
                //把新用户数据广播出去
                rabbitTemplate.convertAndSend("userExchanges","",userName+"_"+userName);
                return new LoginUser(userInfoVO);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // 刷新记录当前登录的时间f
        userInfoMapper.update(null, new UpdateWrapper<UserInfo>()
                .eq("pi_name", userName).set("last_login", new Date()));
        //更新token
        if (!user.getAccessToken().equals(loginDTOTmp.getAccessToken())) {
            user.setAccessToken(loginDTOTmp.getAccessToken());
            userInfoMapper.updateById(user);
        }
        // 记录登录
        String ip = getIp();
        String region = getIp2Region(ip);
        LoginRecord loginRecord = new LoginRecord(user.getUid(), ip, region, new Date(),userName);
        loginRecordMapper.insert(loginRecord);
        BeanUtils.copyProperties(user,userInfoVO);
        //封装成UserDetails对象返回
        return new LoginUser(userInfoVO);
    }

    /**
     * 登录接口
     */
    private LoginDTO loginDTOTmp;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResultVO login(LoginDTO loginDTO) {
        loginDTOTmp=loginDTO;
        // 根据pi_name查询数据库
        String userName = loginDTO.getUserName();
        // 将用户信息发给authentication
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName,userName);
        // 调用mapper层的UserDetailService方法，校验信息
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        // 验证不通过
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("发生异常");
        }
        // 获取用户
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        UserInfoVO userInfo = loginUser.getUserInfoVO();
        String userId=userInfo.getUid();
        userInfo.setUserImage(imageMapper.selectPath(userInfo.getUserImage()));
        // 生产jwt
        String jwt = JwtUtil.createJWT(userId);
        //authenticate存入redis
//        redisUtil.setCacheObject("login:"+userId,loginUser,1, TimeUnit.DAYS);

        //存入SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken1 =
                new UsernamePasswordAuthenticationToken(loginUser,null,null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken1);
        //把token响应给前端
        HashMap<String,Object> map = new HashMap<>();
        map.put("token",jwt);
        map.put("user",userInfo);
        return new ResponseResultVO(200,"登陆成功",map);
    }

    @Override
    public ResponseResultVO logout(String userId) {

        redisUtil.deleteObject("login:"+userId);
        return new ResponseResultVO(200,"退出成功",null);
    }

    /**
     * 根据用户Id查找用户数据表的基本信息
     * */
    @Override
    public UserInfoVO selectUserInfoByUid(String uid) {
        return userInfoMapper.selectUserInfoByUid(uid);
    }

    /**
     * 根据用户Id更改用户数据表的信息
     * */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateUserInfoByUid(UserInfoVO userInfoVO) {
        String userName = userInfoVO.getUserName();
        String uid = userInfoVO.getUid();
        int result = userInfoMapper.update(null, new UpdateWrapper<UserInfo>()
                                                            .eq("uid", uid)
                                                            .set(!"".equals(userInfoVO.getCountry()),"country",userInfoVO.getCountry())
                                                            .set(userInfoVO.getLanguage() != null ,"language", userInfoVO.getLanguage())
                                                            .set(!"".equals(userInfoVO.getEmail()) && userInfoVO.getEmail() != null,"email", userInfoVO.getEmail()));

        // 要求网店名和用户名保持一致，如果修改了用户名要求同步网店名
        if (userName != null && !"".equals(userName)){
            int update = itemInfoMapper.update(null, new UpdateWrapper<ItemInfo>()
                    .eq("uid", uid)
                    .eq("status", 0)
                    .set("item_name", userName));
            if (update < 1) {
                throw new RuntimeException();
            }
        }
        return result > 0;
    }

    /**
     * 根据用户Id更改用户语言标识
     * */
    @Override
    public boolean updateLanguageByUid(String uid, String language) {
        int result = userInfoMapper.update(null, new UpdateWrapper<UserInfo>()
                                                            .eq("uid", uid)
                                                            .set("language", language));
        return result > 0;
    }


    /**
     * 更改头像
     */
    @Override
    public boolean uploadUserImage(String userId, MultipartFile file) {
        List<String> sizeList = new ArrayList<>();
        sizeList.add(ImageConstants.USER_IMAGE_SIZE_SMALL);
        String imageId = FileUploadUtil.allUploadImageData(file, imageMapper, FileUploadUtil.AVATAR,sizeList);
        // 外键关联目标图片数据
        int update = userInfoMapper.update(null, new LambdaUpdateWrapper<UserInfo>()
                .eq(UserInfo::getUid, userId)
                .set(UserInfo::getUserImage, imageId));
        if (update < 1) {
            log.error("外键关联目标图片数据");
            return false;
        }
        return true;
    }

    @Override
    public ItemMinInfoVo getItemInfoByUid(String userId) {
        return itemInfoMapper.getItemInfoByUid(userId);
    }

    /**
     * 根据用户Id判断用户是否能发布实体店
     * */
    @Override
    public Integer releaseShopIsNotById(String uid) {
        return userInfoMapper.selectShopNumber(uid);
    }




    /**
     * 根据用户Id更改用户国家标识
     * */
    @Override
    public boolean updateCountryByUid(String uid, String country) {
        int result = userInfoMapper.update(null, new UpdateWrapper<UserInfo>()
                                                            .eq("uid", uid)
                                                            .set("Country", country));

        return result > 0;
    }

    @Override
    public String getItemIdByUserId(String userId) {
        return userInfoMapper.getItemIdByUserId(userId);
    }

    private String getIp(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ipAddress = null;
        try {
            // 获取请求客户端的ip
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (ipAddress.equals("127.0.0.1")||ipAddress.equals("0:0:0:0:0:0:0:1")) {
                    ipAddress = "127.0.0.1";
                }
            }
            // 判断ip是否符合规格
            if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
                // = 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress="";
        }
        return ipAddress;
    }
    public String getIp2Region(String ipAddress){


        if ("127.0.0.1".equals(ipAddress) || ipAddress.startsWith("192.168")) {
            return "局域网 ip";
        }
        String dbPath;
        if (searcher == null) {
            try {
                // 加载ip2region 文件
                searcher=Searcher.newWithFileOnly("pipayshop-api/src/main/resources/ipdb/ip2region.xdb");

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String region = null;
        String errorMessage = null;
        try {
            // 获取地区
            region = searcher.search(ipAddress);
        } catch (Exception e) {
            errorMessage = e.getMessage();
            if (errorMessage != null && errorMessage.length() > 256) {
                errorMessage = errorMessage.substring(0,256);
            }
            e.printStackTrace();
        }
        // 输出 region
        return region;

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean upToVipUser(String userId) {
        int update = userInfoMapper.update(null, new UpdateWrapper<UserInfo>()
                .eq("uid", userId)
                .eq("status", 0)
                .set("level", 1));
        // 将其名下的商店店铺都升级为vip店铺
        shopInfoMapper.update(null, new UpdateWrapper<ShopInfo>()
                .eq("uid", userId)
                .eq("status", 0)
                .set("membership", 1));
        return update > 0;
    }

    @Override
    public boolean isVipUser(String uid) {
        Long count = userInfoMapper.selectCount(new QueryWrapper<UserInfo>()
                .eq("uid", uid)
                .eq("status", 0)
                .eq("level", 1));
        return count.intValue() == 1;
    }


    @Override
    public boolean insertRegisterData(UserRegisterDTO userRegisterDTO) {
        // 将密码进行BCrypt加密再存到数据库中
        String passWord = userRegisterDTO.getPassword();
        String hashPassword = StringUtil.hashPassword(passWord);
        userRegisterDTO.setPassword(hashPassword);
        Long count = userRegisterMapper.selectCount(new QueryWrapper<UserRegister>()
                            .eq("uid", userRegisterDTO.getUid()));
        if (0 == count){
            // 插入数据
            int insert = userRegisterMapper.insertRegisterData(userRegisterDTO);
            return insert > 0;
        }
        // 更新数据
        int update = userRegisterMapper.update(null, new UpdateWrapper<UserRegister>()
                                        .eq("uid", userRegisterDTO.getUid())
                                        .set("password", userRegisterDTO.getPassword()));
        return update > 0;

    }

    @Override
    public ResponseResultVO userRegister(String sessionId, RegisterDTO registerDTO) {
        try {
            // 校验码匹配
            if (!registerDTO.getCheckCode().equalsIgnoreCase(redisUtil.get(Constants.CHECK_CODE_PRE + sessionId))){
                log.error("sessionID:========================"+sessionId);
                // 验证码不一致
                throw new BusinessException("图片验证码不正确");
            }
            // 根据用户名查询数据
            UserRegister userRegister = userRegisterMapper.selectOne(new QueryWrapper<UserRegister>()
                    .eq("pi_name", registerDTO.getPiName())
                    .eq("del_flag", 0)
                    .select("pi_name", "password", "uid"));
            if (userRegister == null){
                throw new BusinessException("账号或密码不正确");
            }
            // 校验密码
            boolean flag = StringUtil.checkPassword(registerDTO.getPassword(), userRegister.getPassword());
            if (!flag){
                throw new BusinessException("密码不正确");
            }
            // 获取用户数据
            String uid = userRegister.getUid();
            UserInfo userInfo = userInfoMapper.selectOne(new QueryWrapper<UserInfo>().eq("uid", uid).eq("status", 0));
            if (userInfo == null){
                throw new BusinessException("用户数据不存在");
            }
            UserInfoVO userInfoVO = new UserInfoVO();
            BeanUtils.copyProperties(userInfo,userInfoVO);
            // 生成token
            String jwt = JwtUtil.createJWT(uid);
            //把token响应给前端
            HashMap<String,Object> map = new HashMap<>(1);
            map.put("token",jwt);
            map.put("user",userInfoVO);
            return new ResponseResultVO(200,"登陆成功",map);
        } finally {
            // 无论是否成功，都要令上一次的验证码失效
            redisUtil.del(Constants.CHECK_CODE_PRE + sessionId);
        }
    }
}
