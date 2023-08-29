package com.example.pipayshopapi.service;

import com.example.pipayshopapi.entity.Country;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.vo.CountryVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zxb
 * @since 2023-08-29
 */
public interface CountryService extends IService<Country> {

    /**
     * 获取国家列表数据
     */
    List<CountryVO> getCountryList();
}
