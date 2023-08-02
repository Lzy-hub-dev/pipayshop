package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.pipayshopapi.entity.ItemCommodityHistory;
import com.example.pipayshopapi.mapper.ItemCommodityHistoryMapper;
import com.example.pipayshopapi.service.ItemCommodityHistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 网店用户商品历史记录表 服务实现类
 * </p>
 *
 * @author zxb
 * @since 2023-07-29
 */
@Service
public class ItemCommodityHistoryServiceImpl extends ServiceImpl<ItemCommodityHistoryMapper, ItemCommodityHistory> implements ItemCommodityHistoryService {
    @Resource
    private ItemCommodityHistoryMapper itemCommodityHistoryMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteHistory(String userId, String commodityId) {
        if (commodityId != null) {
            return itemCommodityHistoryMapper.delete(new LambdaQueryWrapper<ItemCommodityHistory>()
                    .eq(ItemCommodityHistory::getCommodityId, commodityId)) > 0;
        }
        if (userId != null) {
            return itemCommodityHistoryMapper.delete(new LambdaQueryWrapper<ItemCommodityHistory>()
                    .eq(ItemCommodityHistory::getUserId, userId)) > 0;
        }
        return false;
    }
}
