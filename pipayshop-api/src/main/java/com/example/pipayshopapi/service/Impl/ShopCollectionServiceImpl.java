package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.pipayshopapi.entity.ItemCollection;
import com.example.pipayshopapi.entity.ShopCollection;
import com.example.pipayshopapi.mapper.ShopCollectionMapper;
import com.example.pipayshopapi.service.ShopCollectionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 实体店服务收藏表		 服务实现类
 * </p>
 *
 * @author jjf
 * @since 2023-07-29
 */
@Service
public class ShopCollectionServiceImpl extends ServiceImpl<ShopCollectionMapper, ShopCollection> implements ShopCollectionService {

    @Resource
    ShopCollectionMapper shopCollectionMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addShopCollection(String userId, String commodityId) {
        ShopCollection shopCollection = shopCollectionMapper.selectOne(new QueryWrapper<ShopCollection>()
                        .eq("user_id", userId)
                        .eq("commodity_id", commodityId));
        if(shopCollection != null){
            //收藏状态修改为0
            shopCollection.setStatus(0);
            return shopCollectionMapper.updateById(shopCollection);
        }else{
            ShopCollection shopCollection1 = new ShopCollection(null, StringUtil.generateShortId(), commodityId, userId, null, null, null);
            return shopCollectionMapper.insert(shopCollection1);
        }
    }

    @Override
    public int removeShopCollection(String userId, String commodityId) {
        return shopCollectionMapper.update(null, new UpdateWrapper<ShopCollection>()
                .eq("user_id", userId)
                .eq("commodity_id", commodityId)
                .set("status", 1));
    }

    @Override
    public boolean isCommodityToCollection(String userId, String commodityId) {
        ShopCollection shopCollection = shopCollectionMapper.selectOne(new QueryWrapper<ShopCollection>()
                .eq("user_id", userId)
                .eq("commodity_id", commodityId)
                .eq("status", 0));
        return shopCollection != null;
    }
}
