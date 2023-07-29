package com.example.pipayshopapi.mapper;

import com.example.pipayshopapi.entity.ShopOrderInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pipayshopapi.entity.vo.ItemOrderInfoVO;
import org.apache.ibatis.annotations.Mapper;

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
     * 根据用户id查询 订单列表
     *
     * @param userId
     * @return
     */
    List<ItemOrderInfoVO> selectOrderByUerId(String userId);
}
