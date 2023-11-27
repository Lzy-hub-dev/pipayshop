package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.entity.ZoneLeaderConfiguration;
import com.example.pipayshopapi.mapper.ZoneLeaderConfigurationMapper;
import com.example.pipayshopapi.service.ZoneLeaderConfigurationService;
import org.springframework.stereotype.Service;

@Service
public class ZoneLeaderConfigurationServiceImpl extends ServiceImpl<ZoneLeaderConfigurationMapper, ZoneLeaderConfiguration> implements ZoneLeaderConfigurationService {
}
