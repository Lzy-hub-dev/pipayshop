package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.pipayshopapi.entity.UserInfo;
import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.entity.vo.PageVO;
import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.entity.vo.UserInfoVO;
import com.example.pipayshopapi.mapper.UserInfoMapper;
import com.example.pipayshopapi.service.UserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .set("user_name", userInfoVO.getUserName())
                .set("personal_profile", userInfoVO.getPersonalProfile())
                .set("language", userInfoVO.getLanguage())
                .set("email", userInfoVO.getEmail())
                .set("age", userInfoVO.getAge()));
        return result>0;
    }

}
