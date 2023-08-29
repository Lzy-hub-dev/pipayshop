package com.example.pipayshopapi.service.Impl;

import com.example.pipayshopapi.entity.Country;
import com.example.pipayshopapi.entity.vo.CountryVO;
import com.example.pipayshopapi.mapper.CountryMapper;
import com.example.pipayshopapi.service.CountryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zxb
 * @since 2023-08-29
 */
@Service
public class CountryServiceImpl extends ServiceImpl<CountryMapper, Country> implements CountryService {

    @Resource
    private CountryMapper countryMapper;

    @Override
    public List<CountryVO> getCountryList() {
        return countryMapper.getCountryList();
    }
}
