package com.example.pipayshopapi.mapper;

import com.example.pipayshopapi.entity.ShopOrderInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pipayshopapi.entity.vo.ItemOrderInfoVO;
import com.example.pipayshopapi.entity.vo.ShopOrderInfoVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 订单数据表 Mapper 接口
 * </p>
 *
 * @author zxb
 * @since 2023-07-29
 */
@Mapper
public interface ShopOrderInfoMapper extends BaseMapper<ShopOrderInfo> {

    /**
     * 通过用户id去查找实体店订单
     * @param userId
     * @return
     */
    List<ShopOrderInfoVO> selectShopOrders(String userId);
}
