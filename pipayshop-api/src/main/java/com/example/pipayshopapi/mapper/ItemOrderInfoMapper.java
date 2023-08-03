package com.example.pipayshopapi.mapper;

import com.example.pipayshopapi.entity.ItemOrderInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pipayshopapi.entity.vo.*;
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

    /**
     * 通过用户id去查找网店订单
     * @param userId
     * @return
     */
    List<ItemOrderInfoVO> selectItemOrders(String userId);

    /**
     * 通过用户id去查找网店订单详情（关联网店+订单+商品）
     * @param userId
     * @return
     */
    List<ItemOrderInfoVO> selectOrderByUerId(String userId);

    /**
     * 根据卖家id查询网店关联的订单
     *
     * @param userId      卖家id
     * @param orderStatus 0:待支付;2:已完成;3:查询所有
     * @return
     */
    List<ItemOrderInfoVO> selectItemOrdersBySellerId(@Param("userId") String userId, @Param("orderStatus") Integer orderStatus);

    /**
     * 订单详情数据
     */
    OrderDetailVO getOrderDetail(@Param("orderId") String orderId);


    List<OrderListVO> getOrderList(@Param("getOrderDataVO") GetOrderDataVO getOrderDataVO);

    /**
     * 根据用户id查询网店的所有订单
     */
    List<MyItemOrderInfoVO> getMyOrderByUid(@Param("page") Integer page, @Param("limit") Integer limit,@Param("Uid") String uid);

    Integer getAllMyOrderByUid(@Param("Uid") String uid);
}
