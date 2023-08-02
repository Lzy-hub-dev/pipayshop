package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.entity.ItemCollection;
import com.example.pipayshopapi.entity.ShopCollection;
import com.example.pipayshopapi.mapper.ItemCollectionMapper;
import com.example.pipayshopapi.service.ItemCollectionService;
import com.example.pipayshopapi.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 网店商品收藏表	 服务实现类
 * </p>
 *
 * @author jjf
 * @since 2023-07-29
 */
@Service
public class ItemCollectionServiceImpl extends ServiceImpl<ItemCollectionMapper, ItemCollection> implements ItemCollectionService {

    @Resource
    ItemCollectionMapper collectionMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int AddItemCommodityToCollection(String userId, String commodityId) {
        ItemCollection itemCollection = collectionMapper.selectOne(new QueryWrapper<ItemCollection>()
                .eq("user_id", userId)
                .eq("commodity_id", commodityId));
        if(itemCollection != null){
            //收藏状态修改为0
            itemCollection.setStatus(0);
            return collectionMapper.updateById(itemCollection);
        }else{
            ItemCollection itemCollection1 = new ItemCollection(null, StringUtil.generateShortId(), commodityId, userId, null, null, null);
            return collectionMapper.insert(itemCollection1);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int closeItemCommodityToCollection(String userId, String commodityId) {
        return collectionMapper.update(null, new UpdateWrapper<ItemCollection>()
                .eq("user_id", userId)
                .eq("commodity_id", commodityId)
                .set("status", 1));
    }

    @Override
    public boolean isItemCommodityToCollection(String userId, String commodityId) {
        ItemCollection itemCollection = collectionMapper.selectOne(new QueryWrapper<ItemCollection>()
                .eq("user_id", userId)
                .eq("commodity_id", commodityId)
                .eq("status", 0));
        return itemCollection != null;
    }
}
