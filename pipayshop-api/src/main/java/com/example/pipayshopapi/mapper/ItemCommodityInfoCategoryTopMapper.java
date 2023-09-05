package com.example.pipayshopapi.mapper;

import com.example.pipayshopapi.entity.ItemCommodityInfoCategoryTop;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 网店商品的一级分类表 Mapper 接口
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@Mapper
public interface ItemCommodityInfoCategoryTopMapper extends BaseMapper<ItemCommodityInfoCategoryTop> {

    List<ItemCommodityInfoCategoryTop> selectCateTopList();
}
