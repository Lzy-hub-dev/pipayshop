package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.entity.ItemCommodityEvaluate;
import com.example.pipayshopapi.entity.vo.ItemCommodityEvaluateVO;
import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.mapper.ItemCommodityEvaluateMapper;
import com.example.pipayshopapi.service.ItemCommodityEvaluateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zxb
 * @since 2023-07-27
 */
@Service
public class ItemCommodityEvaluateServiceImpl extends ServiceImpl<ItemCommodityEvaluateMapper, ItemCommodityEvaluate> implements ItemCommodityEvaluateService {

    @Resource
    private ItemCommodityEvaluateMapper itemCommodityEvaluateMapper;

    /**
     * 获取网店商品评价
     */
    @Override
    public PageDataVO getItemCommodityEvaluates(String commodityId, Integer page, Integer limit) {
        List<ItemCommodityEvaluateVO> itemCommodityEvaluates = itemCommodityEvaluateMapper.getItemCommodityEvaluates(commodityId,(page-1)*limit,limit);
        Integer itemCommodityEvaluatesSum = itemCommodityEvaluateMapper.getItemCommodityEvaluatesSum(commodityId);

        return new PageDataVO(itemCommodityEvaluatesSum,itemCommodityEvaluates);
    }

    @Override
    public boolean addItemEvaluates(ItemCommodityEvaluate itemCommodityEvaluate) {
        return false;
    }

    @Override
    public boolean deleteItemEvaluates(String userId) {
        return false;
    }
}
