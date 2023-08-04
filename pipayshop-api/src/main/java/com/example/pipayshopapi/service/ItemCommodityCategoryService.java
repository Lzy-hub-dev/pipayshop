package com.example.pipayshopapi.service;

import com.example.pipayshopapi.entity.ItemCommodityCategory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.vo.PageDataVO;

import java.util.List;

/**
 * <p>
 * 网店商品的二级分类表 服务类
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
public interface ItemCommodityCategoryService extends IService<ItemCommodityCategory> {

    /**
     * 展示某一级分类下的二级分类列表
     */
     List<ItemCommodityCategory> cateSecondList(Integer categoryPid);


    /**
     * 展示一级分类列表
     */
    List<ItemCommodityCategory> cateSecondList( );


    /**
     * 根据一级分类-获取所有网店商品列表
     * @param limit
     * @param pages
     * @param categoryId
     * @return
     */
    PageDataVO getSecShopInfoListByCondition(Integer limit, Integer pages, String categoryId);
}
