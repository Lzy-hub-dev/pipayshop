package com.example.pipayshopapi.service;

import com.example.pipayshopapi.entity.ShopEvaluate;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.vo.PageDataVO;

/**
 * <p>
 * 网店评价 服务类
 * </p>
 *
 * @author zxb
 * @since 2023-07-29
 */
public interface ShopEvaluateService extends IService<ShopEvaluate> {

    /**
     * 根据实体店id获取实体店评价列表
     */
    PageDataVO getShopEvaluateListByItemId(Integer page,Integer limit,String itemId);

    /**
     * 增加实体店评价
     */
    boolean addShopEvaluate(ShopEvaluate shopEvaluate);

    /**
     * 删除实体店评价列表
     */
    boolean deleteShopEvaluate(String evaluateId,String userId);
}
