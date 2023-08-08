package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.pipayshopapi.entity.Configuration;
import com.example.pipayshopapi.mapper.ConfigurationMapper;
import com.example.pipayshopapi.service.ConfigurationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zxb
 * @since 2023-08-08
 */
@Service
public class ConfigurationServiceImpl extends ServiceImpl<ConfigurationMapper, Configuration> implements ConfigurationService {
    @Autowired
    private ConfigurationMapper configurationMapper;
    /**
     * 获取所有配置
     *
     * @return
     */
    @Override
    public List<Configuration> getAll() {
        return configurationMapper.selectList(null);
    }

    /**
     * 根据key修改value
     *
     * @return
     */
    @Override
    public Boolean setValueByKey(String key,String value) {
        return configurationMapper.update(null,new LambdaUpdateWrapper<Configuration>()
                .eq(Configuration::getContent,key)
                .set(Configuration::getConfigValue,value))>0;
    }
}
