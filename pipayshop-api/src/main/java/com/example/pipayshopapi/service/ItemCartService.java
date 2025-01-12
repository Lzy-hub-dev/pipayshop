package com.example.pipayshopapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.ItemCart;
import com.example.pipayshopapi.entity.vo.ItemCartVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zxb
 * @since 2023-07-28
 */
public interface ItemCartService extends IService<ItemCart> {

    /**
     * 根据用户id查找购物车
     */
    List<ItemCartVO> selectItemCartByIds(String userId);

    /**
     * 放进购物车
     */
    boolean putItemCartById(String userId,String commodityId,Integer sumCount,String commoditySpec);

    /**
     * 批量放出购物车
     */
    boolean outItemCartById(List<String> commodityId,String cartId);



}
