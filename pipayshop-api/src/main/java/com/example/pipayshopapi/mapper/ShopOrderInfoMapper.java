package com.example.pipayshopapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pipayshopapi.entity.ShopOrderInfo;
import com.example.pipayshopapi.entity.vo.GetOrderDataVO;
import com.example.pipayshopapi.entity.vo.OrderListVO;
import com.example.pipayshopapi.entity.vo.ShopOrderDetailVO;
import com.example.pipayshopapi.entity.vo.ShopOrderInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 实体店订单数据表 Mapper 接口
 * </p>
 *
 * @author jjf
 * @since 2023-07-29
 */
@Mapper
public interface ShopOrderInfoMapper extends BaseMapper<ShopOrderInfo> {

    /**
     * 根据用户id查询，订单状态查询订单列表
     *
     * @param page
     * @return
     */
    List<ShopOrderInfoVO> selectOrderByUidAndStatus (@Param("page") Integer page,@Param("limit") Integer limit,
                                                     @Param("uid") String uid,@Param("status") Integer status);

    /**
     * 根据用户id查询，订单状态查询订单列表总条数
     *
     * @param uid
     * @return
     */
    Integer selectAllOrderByUidAndStatus(@Param("uid") String uid,@Param("status") Integer status);


    List<OrderListVO> getOrderList(@Param("getOrderDataVO") GetOrderDataVO getOrderDataVO);

    ShopOrderDetailVO getShopOrderDetailVO(@Param("orderId") String orderId);
    List<OrderListVO> getOrderListByShopId(@Param("getOrderDataVO") GetOrderDataVO getOrderDataVO);
}
