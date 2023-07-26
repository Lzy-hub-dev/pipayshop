package com.example.pipayshopapi.service;

import com.example.pipayshopapi.entity.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.entity.vo.PageVO;
import com.example.pipayshopapi.entity.vo.UserInfoVO;

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

}
