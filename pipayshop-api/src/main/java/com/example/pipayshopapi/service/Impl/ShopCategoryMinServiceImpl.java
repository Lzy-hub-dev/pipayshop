package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.entity.ShopCategoryMin;
import com.example.pipayshopapi.entity.vo.IndexShopInfoVO;
import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.mapper.ShopCategoryMinMapper;
import com.example.pipayshopapi.mapper.ShopInfoMapper;
import com.example.pipayshopapi.service.ShopCategoryMinService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wzx
 */
@Service
public class ShopCategoryMinServiceImpl extends ServiceImpl<ShopCategoryMinMapper, ShopCategoryMin> implements ShopCategoryMinService {

    @Resource
    private ShopCategoryMinMapper shopCategoryMinMapper;

    @Resource
    private ShopInfoMapper shopInfoMapper;


    @Override
    public List<ShopCategoryMin> getShopCategoryMinList(String categoryPid) {
        return shopCategoryMinMapper.selectList(new QueryWrapper<ShopCategoryMin>().
                eq("del_flag", 0).eq("category_pid", categoryPid));
    }

    /**
     * 实体店二级分类标签列表对应的店铺列表条件分页展示
     */
    @Override
    public PageDataVO getShopInfoMinListByCondition(Integer limit, Integer pages, String categoryId) {
        // 获取总条数
        Integer indexShopInfoVOCount = shopInfoMapper.getIndexShopInfoVOCount(categoryId);
        List<IndexShopInfoVO> indexShopInfoVO = shopInfoMapper.getIndexShopInfoVOById(categoryId, (pages - 1) * limit, limit);
        return new PageDataVO(indexShopInfoVOCount,indexShopInfoVO);
    }

}
