package com.example.pipayshopapi.mapper;

import com.example.pipayshopapi.entity.CountryFourth;
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
 * @since 2023-09-13
 */
public interface CountryFourthMapper extends BaseMapper<CountryFourth> {

    @Select("select country_fourth_id as countryId, name from country_fourth where pid_id = #{countryThirdId} and del_flag = 0")
    List<CountryMinVO> getFourthDistrictList(String countryThirdId);
}
