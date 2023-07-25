package com.example.pipayshopapi.service.Impl;

import com.example.pipayshopapi.entity.UserInfo;
import com.example.pipayshopapi.mapper.UserInfoMapper;
import com.example.pipayshopapi.service.IUserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户数据表 服务实现类
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

}
