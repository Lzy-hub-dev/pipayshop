package com.example.pipayshopapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.ItemCommodityEvaluate;
import com.example.pipayshopapi.entity.vo.PageDataVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zxb
 * @since 2023-07-27
 */
public interface ItemCommodityEvaluateService extends IService<ItemCommodityEvaluate> {

    /**
     * 获取网店商品的评价
     * @return
     */
    PageDataVO getItemCommodityEvaluates(String commodityId,Integer page,Integer limit);

    /**
     * 新增网店商品的评价
     * @return
     */
    boolean addItemEvaluates(ItemCommodityEvaluate itemCommodityEvaluate);

    /**
     * 根据评价Id删除网店商品的评价
     * @return
     */
    boolean deleteItemEvaluates(String evaluateId, String userId);

}
