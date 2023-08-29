package com.example.pipayshopapi.mapper;

import com.example.pipayshopapi.entity.ItemCommodityCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pipayshopapi.entity.vo.ItemCommoditiesVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 网店商品的二级分类表 Mapper 接口
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@Mapper
public interface ItemCommodityCategoryMapper extends BaseMapper<ItemCommodityCategory> {

    List<ItemCommoditiesVO> getSecShopInfoListByTopCateId(@Param("limit") Integer limit,
                                                          @Param("page")Integer page,
                                                          @Param("categoryId")String categoryId);
}
