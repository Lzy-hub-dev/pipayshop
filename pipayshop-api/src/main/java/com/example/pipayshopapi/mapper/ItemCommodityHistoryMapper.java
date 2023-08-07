package com.example.pipayshopapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pipayshopapi.entity.ItemCommodityHistory;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 网店用户商品历史记录表 Mapper 接口
 * </p>
 *
 * @author zxb
 * @since 2023-07-29
 */
@Mapper

public interface ItemCommodityHistoryMapper extends BaseMapper<ItemCommodityHistory> {

    void orderDeleteHistory();
}
