package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.entity.ShopCategoryMin;
import com.example.pipayshopapi.entity.vo.IndexShopInfoVO;
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
     * 实体店三级分类标签列表对应的店铺列表条件分页展示
     * 根据三级ID来查二级ID
     */
    @Override
    public List<IndexShopInfoVO> getShopInfoMinListByCondition(Integer limit, Integer pages, String categoryId, Integer state) {
        // stata==1,按评分从低到高；stata==2,按评分从高到低
        List<IndexShopInfoVO> indexShopInfoVO = shopInfoMapper.getIndexShopInfoVO(categoryId, (pages - 1) * limit, limit,state);
        return indexShopInfoVO;
    }

}
