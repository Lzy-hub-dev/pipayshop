package com.example.pipayshopapi.mapper;

import com.example.pipayshopapi.entity.ShopCollection;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pipayshopapi.entity.ShopCommodityInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 实体店服务收藏表		 Mapper 接口
 * </p>
 *
 * @author jjf
 * @since 2023-07-29
 */
@Mapper
public interface ShopCollectionMapper extends BaseMapper<ShopCollection> {
}
