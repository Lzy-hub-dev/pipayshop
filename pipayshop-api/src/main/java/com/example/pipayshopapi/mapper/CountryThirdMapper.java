package com.example.pipayshopapi.mapper;

import com.example.pipayshopapi.entity.CountryThird;
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
public interface CountryThirdMapper extends BaseMapper<CountryThird> {

    @Select("select country_third_id as countryId, name from country_third where pid_id = #{countrySecondId} and del_flag = 0")
    List<CountryMinVO> getThirdDistrictList(@Param("countrySecondId") String countrySecondId);
}
