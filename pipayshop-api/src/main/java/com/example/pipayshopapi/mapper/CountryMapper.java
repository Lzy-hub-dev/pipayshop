package com.example.pipayshopapi.mapper;

import com.example.pipayshopapi.entity.Country;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pipayshopapi.entity.vo.CountryVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zxb
 * @since 2023-08-29
 */
public interface CountryMapper extends BaseMapper<Country> {

    @Select("select country_code, country_name from country where del_flag = 0")
    List<CountryVO> getCountryList();
}
