package com.example.pipayshopapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pipayshopapi.entity.ShopCommodityHistory;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 实体店用户商品历史记录表 Mapper 接口
 * </p>
 *
 * @author zxb
 * @since 2023-07-29
 */
@Mapper
public interface ShopCommodityHistoryMapper extends BaseMapper<ShopCommodityHistory> {

    void orderDeleteHistory();
}
