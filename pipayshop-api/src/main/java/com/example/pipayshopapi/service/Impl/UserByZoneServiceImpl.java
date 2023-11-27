package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.entity.FirstZone;
import com.example.pipayshopapi.entity.FirstZoneUser;
import com.example.pipayshopapi.entity.UserByZone;
import com.example.pipayshopapi.entity.dto.UserByZoneInvitationCodeDTO;
import com.example.pipayshopapi.mapper.FirstZoneMapper;
import com.example.pipayshopapi.mapper.FirstZoneUserMapper;
import com.example.pipayshopapi.mapper.UserByZoneMapper;
import com.example.pipayshopapi.service.UserByZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserByZoneServiceImpl extends ServiceImpl<UserByZoneMapper, UserByZone> implements UserByZoneService {

    @Autowired
    UserByZoneMapper userByZoneMapper;
    @Autowired
    FirstZoneMapper firstZoneMapper;
    @Autowired
    FirstZoneUserMapper firstZoneUserMapper;

}
