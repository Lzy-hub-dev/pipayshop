package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.entity.UserInfo;
import com.example.pipayshopapi.entity.vo.ItemMinInfoVo;
import com.example.pipayshopapi.entity.vo.UserInfoVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.mapper.ItemInfoMapper;
import com.example.pipayshopapi.mapper.UserInfoMapper;
import com.example.pipayshopapi.service.UserInfoService;
import com.example.pipayshopapi.util.FileUploadUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

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

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private ItemInfoMapper itemInfoMapper;

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
