package com.example.pipayshopapi.mapper;

import com.example.pipayshopapi.entity.ItemOrderInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pipayshopapi.entity.vo.ItemOrderInfoVO;
import org.apache.ibatis.annotations.Mapper;

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
}