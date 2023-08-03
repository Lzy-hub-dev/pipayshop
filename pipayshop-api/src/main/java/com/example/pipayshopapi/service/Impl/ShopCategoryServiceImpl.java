package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.entity.ShopCategory;
import com.example.pipayshopapi.entity.ShopCategoryTop;
import com.example.pipayshopapi.entity.vo.IndexShopInfoVO;
import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.mapper.ShopCategoryMapper;
import com.example.pipayshopapi.mapper.ShopInfoMapper;
import com.example.pipayshopapi.service.ShopCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 实体店二级分类表 服务实现类
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@Service
public class ShopCategoryServiceImpl extends ServiceImpl<ShopCategoryMapper, ShopCategory> implements ShopCategoryService {
    @Resource
    private ShopCategoryMapper shopCategoryMapper;
    @Resource
    private ShopInfoMapper shopInfoMapper;

    /**
     * 查询一级分类对应的二级分列表
     */
    @Override
    public PageDataVO getShopCategoryList(Integer pageNum, Integer pageSize, Integer topId) {
        LambdaQueryWrapper<ShopCategory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShopCategory::getDelFlag, 0)
                .eq(ShopCategory::getCategoryPid, topId);
        Page<ShopCategory> page = new Page<>(pageNum, pageSize);
        Page<ShopCategory> selectPage = shopCategoryMapper.selectPage(page, wrapper);
        return new PageDataVO(Integer.valueOf(selectPage.getTotal() + ""), selectPage.getRecords());
    }

    /**
     * 根据分类id查询分类
     */
    @Override
    public ShopCategory getShopCategoryById(String categoryId) {
        return shopCategoryMapper.selectOne(new LambdaQueryWrapper<ShopCategory>()
                .eq(ShopCategory::getCategoryId, categoryId)
                .eq(ShopCategory::getDelFlag, 0));
    }

    /**
     * 根据分类id删除分类
     */
    @Override
    public Boolean deleteShopCategoryById(String categoryId) {
        return shopCategoryMapper.update(null, new LambdaUpdateWrapper<ShopCategory>()
                .eq(ShopCategory::getDelFlag, 0)
                .eq(ShopCategory::getCategoryId, categoryId)
                .set(ShopCategory::getDelFlag, 1)) > 0;
    }

    /**
     * 查询二级分类列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageDataVO getShopCategorySecList(String categoryPid, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<ShopCategory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShopCategory::getDelFlag, 0);
        wrapper.eq(ShopCategory::getCategoryPid, categoryPid);
        Page<ShopCategory> page = new Page<>(pageNum, pageSize);
        Page<ShopCategory> selectPage = shopCategoryMapper.selectPage(page, wrapper);
        return new PageDataVO(Integer.valueOf(selectPage.getTotal() + ""), selectPage.getRecords());
    }

}
