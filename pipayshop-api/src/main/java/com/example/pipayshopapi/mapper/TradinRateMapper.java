package com.example.pipayshopapi.mapper;

import com.example.pipayshopapi.entity.TradinRate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.example.pipayshopapi.entity.vo.TradinRateVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wzx
 * @since 2023-09-14
 */
@Mapper
public interface TradinRateMapper extends BaseMapper<TradinRate> {

    List<TradinRateVO> selectAllTradinRate();

}
