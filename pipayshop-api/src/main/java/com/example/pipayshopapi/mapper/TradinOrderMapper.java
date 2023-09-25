package com.example.pipayshopapi.mapper;

import com.example.pipayshopapi.entity.TradinOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pipayshopapi.entity.vo.TradinOrderDetailVO;
import com.example.pipayshopapi.entity.vo.TradinOrderListVO;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wzx
 * @since 2023-09-19
 */
public interface TradinOrderMapper extends BaseMapper<TradinOrder> {

    List<TradinOrderListVO> selectTradinyOrderByUid(String userId);

    TradinOrderDetailVO selectTradinOrderDetail(String orderId);
}
