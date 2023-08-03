package com.example.pipayshopapi.service;

import com.example.pipayshopapi.entity.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.vo.UserInfoVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 用户数据表 服务类
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
public interface UserInfoService extends IService<UserInfo> {


    /**
     * 根据用户Id查找用户数据表的基本信息
     * @param uid
     * @return
     * */
    UserInfoVO selectUserInfoByUid(String uid);

    /**
     * 根据用户Id更改用户数据表的基本信息
     * @param userInfoVO
     * @return
     * */
    boolean updateUserInfoByUid(UserInfoVO userInfoVO);

    /**
     * 根据用户Id更改用户语言标识
     * @param uid
     * @param language
     * @return
     * */
    boolean updateLanguageByUid(String uid,String language);

    /**
     * 根据用户Id更改用户国家标识
     * @param uid
     * @param country
     * @return
     * */
    boolean updateCountryByUid(String uid,String country);

    /**
     *根据用户Id更改用户国家标识
     * @param userId
     * @param file
     * @return
     */
    boolean uploadUserImage(String userId, MultipartFile file);

    ItemMinInfoVo getItemInfoByUid(String userId);
}
