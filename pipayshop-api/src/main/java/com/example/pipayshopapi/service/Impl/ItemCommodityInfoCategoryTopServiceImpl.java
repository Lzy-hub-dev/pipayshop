package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.pipayshopapi.entity.ItemCommodityInfoCategoryTop;
import com.example.pipayshopapi.mapper.ItemCommodityInfoCategoryTopMapper;
import com.example.pipayshopapi.service.ItemCommodityInfoCategoryTopService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 网店商品的一级分类表 服务实现类
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@Service
public class ItemCommodityInfoCategoryTopServiceImpl extends ServiceImpl<ItemCommodityInfoCategoryTopMapper, ItemCommodityInfoCategoryTop> implements ItemCommodityInfoCategoryTopService {

    @Resource
    private ItemCommodityInfoCategoryTopMapper categoryTopMapper;



    /**
     * 展示一级分类列表
     */
    @Override
    public List<ItemCommodityInfoCategoryTop> cateTopList() {

        return categoryTopMapper.selectCateTopList();

    }
}
