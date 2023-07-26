package com.example.pipayshopapi.service;

import com.example.pipayshopapi.entity.ShopCategoryTop;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.vo.ShopCategoryVO;

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
     * 查找分类
     * @return
     */
    List<ShopCategoryVO> selectAllContentList();

}
