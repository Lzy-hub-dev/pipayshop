package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.pipayshopapi.entity.ItemCommodityHistory;
import com.example.pipayshopapi.entity.ShopCommodityHistory;
import com.example.pipayshopapi.entity.ShopCommodityInfo;
import com.example.pipayshopapi.entity.ShopInfo;
import com.example.pipayshopapi.mapper.ItemCommodityHistoryMapper;
import com.example.pipayshopapi.mapper.ShopCommodityHistoryMapper;
import com.example.pipayshopapi.service.ShopCommodityHistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 实体店用户商品历史记录表 服务实现类
 * </p>
 *
 * @author zxb
 * @since 2023-07-29
 */
@Service
public class ShopCommodityHistoryServiceImpl extends ServiceImpl<ShopCommodityHistoryMapper, ShopCommodityHistory> implements ShopCommodityHistoryService {

    @Resource
    private ShopCommodityHistoryMapper shopCommodityHistoryMapper;
    /**
     * 删除用户浏览网店商品的历史记录
     *
     * @param userId
     * @param commodityId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteHistory(String userId, String commodityId) {
        if (commodityId != null) {
            return shopCommodityHistoryMapper.delete(new LambdaQueryWrapper<ShopCommodityHistory>()
                    .eq(ShopCommodityHistory::getCommodityId, commodityId))>0;
        }
        if (userId != null) {
            return shopCommodityHistoryMapper.delete(new LambdaQueryWrapper<ShopCommodityHistory>()
                    .eq(ShopCommodityHistory::getUserId, userId)) > 0;
        }
        return false;
    }

}
