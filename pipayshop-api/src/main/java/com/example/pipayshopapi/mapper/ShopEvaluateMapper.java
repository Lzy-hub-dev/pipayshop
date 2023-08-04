package com.example.pipayshopapi.mapper;

import com.example.pipayshopapi.entity.ShopEvaluate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 网店评价 Mapper 接口
 * </p>
 *
 * @author zxb
 * @since 2023-07-29
 */
@Mapper
public interface ShopEvaluateMapper extends BaseMapper<ShopEvaluate> {
    /**
     * 获取实体店评价数
     * @param shopId
     * @return
     */
    Integer selectShopEvaluateCount(String shopId);
}
