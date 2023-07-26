package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.pipayshopapi.entity.ShopCategory;
import com.example.pipayshopapi.entity.ShopCategoryTop;
import com.example.pipayshopapi.entity.vo.ShopCategoryVO;
import com.example.pipayshopapi.mapper.ShopCategoryMapper;
import com.example.pipayshopapi.mapper.ShopCategoryTopMapper;
import com.example.pipayshopapi.service.ShopCategoryTopService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Resource
    private ShopCategoryTopMapper shopCategoryTopMapper;

    @Resource
    private ShopCategoryMapper shopCategoryMapper;

    /**
     * 查找分类列表
     * @return
     */
    @Override
    public List<ShopCategoryVO> selectAllContentList() {
        List<ShopCategoryVO> shopCategoryVOS = shopCategoryTopMapper.selectAllTopContentList();
        List<ShopCategoryVO> temList = shopCategoryVOS;

        for (ShopCategoryVO shopCategoryVO : shopCategoryVOS) {
            String categoryId = shopCategoryVO.getCategoryId();
            List<ShopCategoryVO> list = shopCategoryMapper.selectAllContentList(categoryId);
            shopCategoryVO.setCategoryVOList(list);
        }
        return temList;
    }

}
