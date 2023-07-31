package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.entity.ShopCategoryTop;
import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.mapper.ShopCategoryTopMapper;
import com.example.pipayshopapi.service.ShopCategoryTopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    /**
     * 查询一级分类列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageDataVO getShopCategoryTopList(Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<ShopCategoryTop> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShopCategoryTop::getDelFlag, 0);
        Page<ShopCategoryTop> page = new Page<>(pageNum, pageSize);
        Page<ShopCategoryTop> selectPage = shopCategoryTopMapper.selectPage(page, wrapper);
        return new PageDataVO(Integer.valueOf(selectPage.getTotal()+""),selectPage.getRecords());
    }

    /**
     * 根据分类id查询分类
     *
     * @param categoryTopId
     * @return
     */
    @Override
    public ShopCategoryTop getShopCategoryTopById(String categoryTopId) {
        ShopCategoryTop categoryTop = shopCategoryTopMapper.selectOne(new LambdaQueryWrapper<ShopCategoryTop>()
                .eq(ShopCategoryTop::getCategoryId, categoryTopId)
                .eq(ShopCategoryTop::getDelFlag, 0));
        return categoryTop;
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
