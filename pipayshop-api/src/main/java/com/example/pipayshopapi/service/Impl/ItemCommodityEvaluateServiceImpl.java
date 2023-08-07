package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.entity.ItemCommodityEvaluate;
import com.example.pipayshopapi.entity.vo.ItemCommodityEvaluateAddVO;
import com.example.pipayshopapi.entity.vo.ItemCommodityEvaluateVO;
import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.mapper.ItemCommodityEvaluateMapper;
import com.example.pipayshopapi.service.ItemCommodityEvaluateService;
import com.example.pipayshopapi.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 新增网店商品的评价
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addItemEvaluates(ItemCommodityEvaluateAddVO itemCommodityEvaluateAddVO) {
        String evaluateId = StringUtil.generateShortId();
        int result = itemCommodityEvaluateMapper.insertItemCommodityEvaluateAddVO(
                evaluateId,
                itemCommodityEvaluateAddVO.getUserId(),
                itemCommodityEvaluateAddVO.getCommodityId(),
                itemCommodityEvaluateAddVO.getEvaluate(),
                itemCommodityEvaluateAddVO.getScore());
        // TODO 评价后将订单的状态改为4
        return result>0;
    }

    /**
     * 根据评价Id删除网店商品自己评价
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteItemEvaluates(String evaluateId, String userId) {
        int result = itemCommodityEvaluateMapper.update(null, new UpdateWrapper<ItemCommodityEvaluate>()
                                                                    .eq("evaluate_id", evaluateId)
                                                                    .eq("user_id", userId)
                                                                    .set("status", 1));
        return result > 0;
    }
}
