package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.entity.UserInfo;
import com.example.pipayshopapi.entity.enums.Country;
import com.example.pipayshopapi.entity.enums.Language;
import com.example.pipayshopapi.entity.vo.UserInfoVO;
import com.example.pipayshopapi.exception.BusinessException;
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
    public boolean updateLanguageByUid(String uid, Integer language) {
        Language language1;
        switch (language){
            case 1:language1 = Language.LANGUAGE_1;break;
            case 2:language1 = Language.LANGUAGE_2;break;
            case 3:language1 = Language.LANGUAGE_3;break;
            case 4:language1 = Language.LANGUAGE_4;break;
            case 5:language1 = Language.LANGUAGE_5;break;
            case 6:language1 = Language.LANGUAGE_6;break;
            case 7:language1 = Language.LANGUAGE_7;break;
            case 8:language1 = Language.LANGUAGE_8;break;
            case 9:language1 = Language.lLANGUAGE_9;break;
            case 10:language1 = Language.lLANGUAGE_10;break;
            default: throw new BusinessException("修改语言失败");
        }
        int result = userInfoMapper.update(null, new UpdateWrapper<UserInfo>()
                                                            .eq("uid", uid)
                                                            .set("language", language1.getLanguageId()));
        return result > 0;
    }


    /**
     * 根据用户Id更改用户国家标识
     *
     * @param userId
     * @param file
     * @return
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


    /**
     * 根据用户Id更改用户国家标识
     * */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateCountryByUid(String uid, Integer country) {

        Country country1;
        switch (country){
            case 1:country1=Country.COUNTRY_1;break;
            case 2:country1=Country.COUNTRY_2;break;
            case 3:country1=Country.COUNTRY_3;break;
            case 4:country1=Country.COUNTRY_4;break;
            case 5:country1=Country.COUNTRY_5;break;
            case 6:country1=Country.COUNTRY_6;break;
            case 7:country1=Country.COUNTRY_7;break;
            case 8:country1=Country.COUNTRY_8;break;
            case 9:country1=Country.COUNTRY_9;break;
            case 10:country1=Country.COUNTRY_10;break;
            default: throw new BusinessException("修改国家失败");
        }

        int result = userInfoMapper.update(null, new UpdateWrapper<UserInfo>()
                                                            .eq("uid", uid)
                                                            .set("Country", country1.getCountryId()));

        return result > 0;
    }

}
