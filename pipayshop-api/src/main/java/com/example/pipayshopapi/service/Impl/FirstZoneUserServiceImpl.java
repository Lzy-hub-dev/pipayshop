package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.entity.FirstZoneUser;
import com.example.pipayshopapi.mapper.FirstZoneUserMapper;
import com.example.pipayshopapi.service.FirstZoneUserService;
import org.springframework.stereotype.Service;

@Service
public class FirstZoneUserServiceImpl extends ServiceImpl<FirstZoneUserMapper, FirstZoneUser> implements FirstZoneUserService {
}
