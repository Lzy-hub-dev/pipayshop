package com.example.pipayshopapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.ShopCategory;
import com.example.pipayshopapi.entity.vo.PageDataVO;

/**
 * <p>
 * 实体店二级分类表 服务类
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
public interface ShopCategoryService extends IService<ShopCategory> {
    /**
     * 查询一级分类对应的二级分列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageDataVO getShopCategoryList(Integer pageNum, Integer pageSize, Integer topId);
    /**
     * 根据分类id查询分类
     *
     * @param categoryId
     * @return
     */
    ShopCategory getShopCategoryById(String categoryId);
    /**
     * 根据分类id删除分类
     *
     * @param categoryId
     * @return
     */
    Boolean deleteShopCategoryById(String categoryId);
}
