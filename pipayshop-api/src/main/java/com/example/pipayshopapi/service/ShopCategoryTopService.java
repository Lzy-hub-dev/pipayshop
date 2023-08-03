package com.example.pipayshopapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.ShopCategoryTop;
import com.example.pipayshopapi.entity.vo.PageDataVO;

import java.util.List;

/**
 * <p>
 * 实体店一级分类表 服务类
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
public interface ShopCategoryTopService extends IService<ShopCategoryTop> {

    /**
     *查询一级分类列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageDataVO getShopCategoryTopList(Integer pageNum, Integer pageSize);

    /**
     * 根据分类id查询分类
     * @param categoryTopId
     * @return
     */
    ShopCategoryTop getShopCategoryTopById(String categoryTopId);

    /**
     * 根据分类id删除分类
     * @param categoryTopId
     * @return
     */
    Boolean deleteShopCategoryTopById(String categoryTopId);

    /**
     * 根据分类id修改分类
     * @param shopCategoryTop
     * @return
     */
    Boolean updateShopCategoryTopById(ShopCategoryTop shopCategoryTop);



    /**
     * 实体店申请商品获取所有一级分类
     * @return  List<ShopCategoryTop>
     */
    List<ShopCategoryTop> getAllShopCategoryTopList();


}
