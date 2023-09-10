package com.example.pipayshopapi.service.Impl;

import com.example.pipayshopapi.entity.Country;
import com.example.pipayshopapi.entity.vo.CountryMinVO;
import com.example.pipayshopapi.entity.vo.CountryVO;
import com.example.pipayshopapi.mapper.CountryMapper;
import com.example.pipayshopapi.service.CountryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.util.Constants;
import com.example.pipayshopapi.util.RedisUtil;
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

    private final RedisUtil<CountryVO> redisUtil = new RedisUtil<>();


    @Override
    public List<CountryVO> getCountryList() {
        // 直接走缓存拿
        List<CountryVO> countryVOList = redisUtil.getDataListFromRedisList(Constants.COUNTRY_REGION);
        // 校验缓存结果
        if (countryVOList == null || countryVOList.size() == 0) {
            // 刷新缓存
            countryVOList = countryMapper.getCountryList();
            if (countryVOList == null){return null;}
            redisUtil.savaDataListToRedisList(Constants.COUNTRY_REGION, countryVOList);
        }
        return countryVOList;
    }
}
