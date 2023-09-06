package com.example.pipayshopapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pipayshopapi.entity.ItemOrderInfo;
import com.example.pipayshopapi.entity.vo.ItemOrderDetailVO;
import com.example.pipayshopapi.entity.vo.ItemOrderInfoVO;
import com.example.pipayshopapi.entity.vo.MyItemOrderInfoVO;
import com.example.pipayshopapi.entity.vo.OrderListVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商户订单数据表 Mapper 接口
 * </p>
 *
 * @author zxb
 * @since 2023-07-28
 */
@Mapper
public interface ItemOrderInfoMapper extends BaseMapper<ItemOrderInfo> {


}
