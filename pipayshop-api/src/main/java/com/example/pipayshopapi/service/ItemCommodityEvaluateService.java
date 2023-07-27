package com.example.pipayshopapi.service;

import com.example.pipayshopapi.entity.ItemCommodityEvaluate;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.dto.ShopDTO;
import com.example.pipayshopapi.entity.vo.ItemCommodityEvaluateVO;
import com.example.pipayshopapi.entity.vo.PageDataVO;

import java.util.List;

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
}
