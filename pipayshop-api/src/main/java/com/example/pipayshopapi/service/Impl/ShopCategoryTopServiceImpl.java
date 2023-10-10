package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.entity.ShopCategoryTop;
import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.mapper.ImageMapper;
import com.example.pipayshopapi.mapper.ShopCategoryTopMapper;
import com.example.pipayshopapi.service.ShopCategoryTopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 实体店一级分类表 服务实现类
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@Service
public class ShopCategoryTopServiceImpl extends ServiceImpl<ShopCategoryTopMapper, ShopCategoryTop> implements ShopCategoryTopService {

    @Autowired
    private ShopCategoryTopMapper shopCategoryTopMapper;

    @Resource
    private ImageMapper imageMapper;
    /**
     * 查询一级分类列表
     *
     * @return
     */
    @Override
    public List<ShopCategoryTop> getShopCategoryTopList() {
        LambdaQueryWrapper<ShopCategoryTop> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShopCategoryTop::getDelFlag, 0);
        List<ShopCategoryTop> shopCategoryTops = shopCategoryTopMapper.selectList(wrapper);
        for (ShopCategoryTop shopCategoryTop : shopCategoryTops) {
            shopCategoryTop.setCategoryImg(imageMapper.selectPath(shopCategoryTop.getCategoryImg()));
        }
        return shopCategoryTops;
    }



    /*private static List<ShopCategoryVO> getCategoryVO(List<ShopCategoryTop> categoryTop) {
        List<ShopCategoryVO> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(categoryTop)) {
            categoryTop.stream().forEach(shopCategoryTop ->
                    list.add(new ShopCategoryVO() {{
                setCategoryId(getCategoryId());
                setContent(shopCategoryTop.getContent());
            }}));
            return list;
        }
        return list;
    }*/

    /**
     * 根据分类id删除分类
     *
     * @param categoryTopId
     * @return
     */
    @Override
    public Boolean deleteShopCategoryTopById(String categoryTopId) {
        return shopCategoryTopMapper.update(null,new LambdaUpdateWrapper<ShopCategoryTop>()
                .eq(ShopCategoryTop::getDelFlag,0)
                .eq(ShopCategoryTop::getCategoryId,categoryTopId)
                .set(ShopCategoryTop::getDelFlag,1))>0;
    }

    /**
     * 根据分类id修改分类
     *
     * @param shopCategoryTop
     * @return
     */
    @Override
    public Boolean updateShopCategoryTopById(ShopCategoryTop shopCategoryTop) {
        return shopCategoryTopMapper.update(shopCategoryTop,new LambdaQueryWrapper<ShopCategoryTop>()
                .eq(ShopCategoryTop::getDelFlag,0)
                .eq(ShopCategoryTop::getCategoryId,shopCategoryTop.getCategoryId()))>0;
    }
    @Override
    public List<ShopCategoryTop> getAllShopCategoryTopList() {
        List<ShopCategoryTop> categoryTopList = shopCategoryTopMapper.selectList(new QueryWrapper<ShopCategoryTop>().
                eq("del_flag", 0));
        return categoryTopList;
    }
}
