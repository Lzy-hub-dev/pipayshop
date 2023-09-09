package com.example.pipayshopapi.mapper;

import com.example.pipayshopapi.entity.CountrySecond;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pipayshopapi.entity.vo.CountryMinVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wzx
 * @since 2023-09-07
 */
public interface CountrySecondMapper extends BaseMapper<CountrySecond> {


    @Select("select country_second_id, name from country_second where pid_id = #{countryCode} and del_flag = 0")
    List<CountryMinVO> getSecondDistrictList(@Param("countryCode") String countryCode);
}
