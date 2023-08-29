package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.pipayshopapi.entity.ItemCommodityCategory;
import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.entity.vo.ItemCommoditiesVO;
import com.example.pipayshopapi.mapper.ItemCommodityCategoryMapper;
import com.example.pipayshopapi.service.ItemCommodityCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 网店商品的二级分类表 服务实现类
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@Service
public class ItemCommodityCategoryServiceImpl extends ServiceImpl<ItemCommodityCategoryMapper, ItemCommodityCategory> implements ItemCommodityCategoryService {

    @Resource
    private ItemCommodityCategoryMapper categoryMapper;


    /**
     * 展示某一级分类下的二级分类列表
     */
    @Override
    public List<ItemCommodityCategory> cateSecondList(Integer categoryPid) {

        List<ItemCommodityCategory> cateSecondList = categoryMapper.selectList(new QueryWrapper<ItemCommodityCategory>()
                                                                                    .eq("category_pid",categoryPid)
                                                                                    .eq("del_flag",0));
        return cateSecondList;

    }

    @Override
    public List<ItemCommodityCategory> cateSecondList() {
        return null;
    }

    /**
     * 根据一级分类-获取所有网店商品列表
     *
     * @param limit
     * @param pages
     * @param categoryId
     * @return
     */
    @Override
    public PageDataVO getSecShopInfoListByCondition(Integer limit, Integer pages, String categoryId) {
        List<ItemCommoditiesVO> list  = categoryMapper.getSecShopInfoListByTopCateId(limit, pages, categoryId);
        return new PageDataVO(list.size(), list);
    }
}
