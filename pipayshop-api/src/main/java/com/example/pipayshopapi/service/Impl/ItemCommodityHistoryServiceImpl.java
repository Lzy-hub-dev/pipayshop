package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.pipayshopapi.entity.ItemCommodityHistory;
import com.example.pipayshopapi.entity.ShopCommodityHistory;
import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.mapper.ItemCommodityHistoryMapper;
import com.example.pipayshopapi.service.ItemCommodityHistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

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

    /**
     * 删除用户浏览网店商品的历史记录
     * @param userId
     * @param commodityId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteHistory(String userId, String commodityId) {
        if (commodityId != null) {
            return itemCommodityHistoryMapper.delete(new LambdaQueryWrapper<ItemCommodityHistory>()
                    .eq(ItemCommodityHistory::getCommodityId, commodityId)
                    .eq(ItemCommodityHistory::getUserId,userId)) > 0;
        }
        if (userId != null) {
            return itemCommodityHistoryMapper.delete(new LambdaQueryWrapper<ItemCommodityHistory>()
                    .eq(ItemCommodityHistory::getUserId, userId)) > 0;
        }
        return false;
    }

    /**
     * 新增用户浏览网店商品的历史记录
     * @param itemCommodityHistory
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addHistory(ItemCommodityHistory itemCommodityHistory) {
        if (itemCommodityHistory == null || itemCommodityHistory.getCommodityId() == null || itemCommodityHistory.getUserId() == null) {
            log.error("参数不能为空");
            return false;
        }
        ItemCommodityHistory histories = itemCommodityHistoryMapper.selectOne(new LambdaQueryWrapper<ItemCommodityHistory>()
                .eq(ItemCommodityHistory::getCommodityId, itemCommodityHistory.getCommodityId())
                .eq(ItemCommodityHistory::getUserId, itemCommodityHistory.getUserId()));
        int result;
        if (histories == null) {
            result = itemCommodityHistoryMapper.insert(itemCommodityHistory);
        } else {
            result = itemCommodityHistoryMapper.update(null, new LambdaUpdateWrapper<ItemCommodityHistory>()
                    .eq(ItemCommodityHistory::getCommodityId, itemCommodityHistory.getCommodityId())
                    .eq(ItemCommodityHistory::getUserId, itemCommodityHistory.getUserId())
                    .set(ItemCommodityHistory::getCreateTime, new Date()));
        }
        return result > 0;
    }
}
