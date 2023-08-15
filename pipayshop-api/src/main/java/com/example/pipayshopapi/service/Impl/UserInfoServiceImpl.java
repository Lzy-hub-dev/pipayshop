package com.example.pipayshopapi.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.entity.ItemInfo;
import com.example.pipayshopapi.entity.LoginRecord;
import com.example.pipayshopapi.entity.ShopInfo;
import com.example.pipayshopapi.entity.UserInfo;
import com.example.pipayshopapi.entity.dto.LoginDTO;
import com.example.pipayshopapi.entity.vo.ItemMinInfoVo;
import com.example.pipayshopapi.entity.vo.UserInfoVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.mapper.*;
import com.example.pipayshopapi.service.UserInfoService;
import com.example.pipayshopapi.util.Constants;
import com.example.pipayshopapi.util.FileUploadUtil;
import com.example.pipayshopapi.util.StringUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

/**
 * <p>
 * 用户数据表 服务实现类
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

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
    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserInfo login(LoginDTO loginDTO) {
        String userId = loginDTO.getUserId();
        // 根据pi_name查询数据库
        String userName = loginDTO.getUserName();
        UserInfo userInfo = userInfoMapper.selectOne(new QueryWrapper<UserInfo>().eq("pi_name", userName));
        // 根据是否为空选择是否进行注册登录
        if (userInfo != null) {
            // 刷新记录当前登录的时间f
            userInfoMapper.update(null, new UpdateWrapper<UserInfo>()
                    .eq("pi_name", userName).set("last_login", new Date()));
            //更新token
            if (!userInfo.getAccessToken().equals(loginDTO.getAccessToken())) {
                userInfo.setAccessToken(loginDTO.getAccessToken());
                userInfoMapper.updateById(userInfo);
            }
            // 记录登录
            String ip = getIp();
            String region = getIp2Region(ip);
            LoginRecord loginRecord = new LoginRecord(userId, ip, region, new Date());
            loginRecordMapper.insert(loginRecord);
            // 已注册
            return userInfo;
        }
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.minepi.com/v2/me")
                .addHeader("Authorization", "Bearer " + loginDTO.getAccessToken())
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
            newUser.setAccessToken(loginDTO.getAccessToken());
            newUser.setUid(userId);
            newUser.setUserImage(Constants.AVATAR_IMAG);
            // 插入数据
            int insert = userInfoMapper.insert(newUser);
            //创建用户账号
            insert += accountInfoMapper.createAccount(userId);
            // 记录登录
            String ip = getIp();
            String region = getIp2Region(ip);
            LoginRecord loginRecord = new LoginRecord(userId, ip, region, new Date());
            loginRecordMapper.insert(loginRecord);

            if (insert < 2){throw new BusinessException(REGISTER_FALSE);}
            // 给新用户开一家网店
            ItemInfo itemInfo = new ItemInfo(null, StringUtil.generateShortId(), userName, false, null, 0.0, null, null,
                    null, userId, 0, Constants.AVATAR_IMAG, 1);
            int insert1 = itemInfoMapper.insert(itemInfo);
            if (insert1 < 1) {
                log.error("给新用户开一家网店失败");
                throw new RuntimeException();
            }
            // 获取最新的注册后的用户数据（包含默认值）
            return userInfoMapper.selectOne(new QueryWrapper<UserInfo>().eq("uid", userId));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
                                                            .set(!"".equals(userName) && userInfoVO.getUserName() != null,"user_name", userInfoVO.getUserName())
                                                            .set(!"".equals(userInfoVO.getPersonalProfile() ) && userInfoVO.getPersonalProfile() != null,"personal_profile", userInfoVO.getPersonalProfile())
                                                            .set(userInfoVO.getLanguage() != null ,"language", userInfoVO.getLanguage())
                                                            .set(!"".equals(userInfoVO.getEmail()) && userInfoVO.getEmail() != null,"email", userInfoVO.getEmail())
                                                            .set(userInfoVO.getAge() != null,"age", userInfoVO.getAge()));
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
    @Transactional(rollbackFor = Exception.class)
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

        String uploadFile = FileUploadUtil.uploadFile(file, FileUploadUtil.AVATAR);
        if (StringUtils.isEmpty(uploadFile)) {
            throw new BusinessException("文件上传失败");
        }
        int update = userInfoMapper.update(null, new LambdaUpdateWrapper<UserInfo>()
                .eq(UserInfo::getUid, userId)
                .set(UserInfo::getUserImage, uploadFile));
        if (update <= 0) {
            FileUploadUtil.deleteFile(uploadFile);
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
    @Transactional(rollbackFor = Exception.class)
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
}
