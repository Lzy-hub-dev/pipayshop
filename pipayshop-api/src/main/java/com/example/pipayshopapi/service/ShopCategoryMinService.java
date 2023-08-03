package com.example.pipayshopapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.ShopCategoryMin;
import com.example.pipayshopapi.entity.vo.IndexShopInfoVO;
import com.example.pipayshopapi.entity.vo.PageDataVO;

import java.util.List;

/**
 * <p>
 * 实体店三级分类表 服务类
 * </p>
 *
 * @author wzx
 */
public interface ShopCategoryMinService extends IService<ShopCategoryMin> {

    /**
     * 查询三级分类标签列表

     */
    List<ShopCategoryMin> getShopCategoryMinList(String categoryPid);


    /**
     * 实体店三级分类标签列表对应的店铺列表条件分页展示
     */
    PageDataVO getShopInfoMinListByCondition(Integer limit, Integer pages, String categoryId, Integer state);
}
