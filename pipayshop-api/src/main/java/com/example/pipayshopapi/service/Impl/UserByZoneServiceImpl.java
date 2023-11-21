package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.entity.UserByZone;
import com.example.pipayshopapi.mapper.UserByZoneMapper;
import com.example.pipayshopapi.service.UserByZoneService;
import org.springframework.stereotype.Service;

@Service
public class UserByZoneServiceImpl extends ServiceImpl<UserByZoneMapper, UserByZone> implements UserByZoneService {
}
