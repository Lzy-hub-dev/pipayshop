package com.example.pipayshopapi.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.entity.UserInfo;
import com.example.pipayshopapi.entity.dto.LoginDTO;
import com.example.pipayshopapi.entity.vo.ItemMinInfoVo;
import com.example.pipayshopapi.entity.vo.UserInfoVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.mapper.AccountInfoMapper;
import com.example.pipayshopapi.mapper.ItemInfoMapper;
import com.example.pipayshopapi.mapper.UserInfoMapper;
import com.example.pipayshopapi.service.UserInfoService;
import com.example.pipayshopapi.util.FileUploadUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
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


    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserInfo login(LoginDTO loginDTO) {
        String userId = loginDTO.getUserId();
        // 根据user_id查询数据库
        UserInfo userInfo = userInfoMapper.selectOne(new QueryWrapper<UserInfo>().eq("uid", userId));
        // 根据是否为空选择是否进行注册登录
        if (userInfo != null) {
            // 刷新记录当前登录的时间f
            userInfoMapper.update(null, new UpdateWrapper<UserInfo>()
                    .eq("uid", userId).set("last_login", new Date()));
            //更新token
            if (!userInfo.getAccessToken().equals(loginDTO.getAccessToken())) {
                userInfo.setAccessToken(loginDTO.getAccessToken());
                userInfoMapper.updateById(userInfo);
            }
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

            if (!jsonObject1.getString("uid").equals(loginDTO.getUserId())) {
                return null;
            }

            //新用户
            UserInfo newUser = new UserInfo();
            // 属性转移
            newUser.setUserName(loginDTO.getUserName());
            newUser.setAccessToken(loginDTO.getAccessToken());
            newUser.setUid(loginDTO.getUserId());
            // 插入数据
            int insert = userInfoMapper.insert(newUser);
            //创建用户账号
            insert += accountInfoMapper.createAccount(loginDTO.getUserId());


            if (insert < 2){throw new BusinessException(REGISTER_FALSE);}
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
        int result = userInfoMapper.update(null, new UpdateWrapper<UserInfo>()
                                                            .eq("uid", userInfoVO.getUid())
                                                            .set(!"".equals(userInfoVO.getUserName()) && userInfoVO.getUserName() != null,"user_name", userInfoVO.getUserName())
                                                            .set(!"".equals(userInfoVO.getPersonalProfile() ) && userInfoVO.getPersonalProfile() != null,"personal_profile", userInfoVO.getPersonalProfile())
                                                            .set(userInfoVO.getLanguage() != null ,"language", userInfoVO.getLanguage())
                                                            .set(!"".equals(userInfoVO.getEmail()) && userInfoVO.getEmail() != null,"email", userInfoVO.getEmail())
                                                            .set(userInfoVO.getAge() != null,"age", userInfoVO.getAge()));
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
}
