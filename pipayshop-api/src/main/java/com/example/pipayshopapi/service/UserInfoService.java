package com.example.pipayshopapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.UserInfo;
import com.example.pipayshopapi.entity.dto.LoginDTO;
import com.example.pipayshopapi.entity.vo.ItemMinInfoVo;
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
     * */
    UserInfoVO selectUserInfoByUid(String uid);

    /**
     * 根据用户Id更改用户数据表的基本信息
     * */
    boolean updateUserInfoByUid(UserInfoVO userInfoVO);

    /**
     * 根据用户Id更改用户语言标识
     * */
    boolean updateLanguageByUid(String uid,String language);

    /**
     * 根据用户Id更改用户国家标识
     * */
    boolean updateCountryByUid(String uid,String country);

    /**
     *根据用户Id更改用户国家标识
     */
    boolean uploadUserImage(String userId, MultipartFile file);

    ItemMinInfoVo getItemInfoByUid(String userId);

    String getItemIdByUserId(String userId);

    /**
     * 根据用户Id判断用户是否能发布实体店
     * */
    Integer releaseShopIsNotById(String uid);

    /**
     * 用户登录注册
     */
    UserInfo login(LoginDTO loginDTO);
}
