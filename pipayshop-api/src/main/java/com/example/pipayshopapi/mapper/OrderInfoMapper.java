package com.example.pipayshopapi.mapper;

import com.example.pipayshopapi.entity.OrderInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 订单数据表 Mapper 接口
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@Mapper
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {

}
