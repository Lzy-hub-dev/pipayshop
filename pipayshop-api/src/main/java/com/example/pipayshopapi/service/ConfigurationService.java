package com.example.pipayshopapi.service;

import com.example.pipayshopapi.entity.Configuration;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zxb
 * @since 2023-08-08
 */
public interface ConfigurationService extends IService<Configuration> {

    /**
     * 获取所有配置
     * @return
     */
    List<Configuration> getAll();

    /**
     * 根据key修改value
     * @return
     */
    Boolean setValueByKey(String key,String value);
}
