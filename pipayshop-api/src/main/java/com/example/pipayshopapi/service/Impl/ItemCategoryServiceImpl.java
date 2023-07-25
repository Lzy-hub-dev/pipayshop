package com.example.pipayshopapi.service.Impl;

import com.example.pipayshopapi.entity.ItemCategory;
import com.example.pipayshopapi.mapper.ItemCategoryMapper;
import com.example.pipayshopapi.service.IItemCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 网店的二级分类表 服务实现类
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@Service
public class ItemCategoryServiceImpl extends ServiceImpl<ItemCategoryMapper, ItemCategory> implements IItemCategoryService {

}
