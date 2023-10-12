package com.example.pipayshopapi.service;

import com.example.pipayshopapi.entity.ItemCommodityInfoCategoryTop;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

import java.util.List;

/**
 * <p>
 * 网店商品的一级分类表 服务类
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
public interface ItemCommodityInfoCategoryTopService extends IService<ItemCommodityInfoCategoryTop> {


    /**
     * 展示一级分类列表
     */
    List<ItemCommodityInfoCategoryTop> cateTopList();


    String itemTopCategoryImags(MultipartFile multipartFile);
}
