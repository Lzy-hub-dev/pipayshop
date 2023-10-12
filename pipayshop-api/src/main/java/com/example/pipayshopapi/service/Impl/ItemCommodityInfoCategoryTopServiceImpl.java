package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.pipayshopapi.entity.ItemCommodityInfoCategoryTop;
import com.example.pipayshopapi.mapper.ImageMapper;
import com.example.pipayshopapi.mapper.ItemCommodityInfoCategoryTopMapper;
import com.example.pipayshopapi.service.ItemCommodityInfoCategoryTopService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.util.FileUploadUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
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


    @Resource
    private ImageMapper imageMapper;

    /**
     * 展示一级分类列表
     */
    @Override
    public List<ItemCommodityInfoCategoryTop> cateTopList() {

        return categoryTopMapper.selectCateTopList();

    }
    /**
     * 网店分类图片上传
     */
    @Override
    public String itemTopCategoryImags(MultipartFile multipartFile) {
        List<String> imageSize = new ArrayList<>();
        imageSize.add("45,45");
        return FileUploadUtil.allUploadImageData(multipartFile, imageMapper, FileUploadUtil.ITEM_TOP_CATEGORY_IMG,imageSize);
    }
}
