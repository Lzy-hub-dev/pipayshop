package com.example.pipayshopapi.mapper;

import com.example.pipayshopapi.entity.ItemOrderDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pipayshopapi.entity.dto.ItemOrderDetailDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wzx
 * @since 2023-09-05
 */
public interface ItemOrderDetailMapper extends BaseMapper<ItemOrderDetail> {


    List<ItemOrderDetailDTO> selectCommodityList(@Param("orderId") String orderId);
}
