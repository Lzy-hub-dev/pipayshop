package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.pipayshopapi.entity.ItemCart;
import com.example.pipayshopapi.entity.vo.ItemCartVO;
import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.mapper.ItemCartMapper;
import com.example.pipayshopapi.service.ItemCartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.awt.*;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zxb
 * @since 2023-07-28
 */
@Service
public class ItemCartServiceImpl extends ServiceImpl<ItemCartMapper, ItemCart> implements ItemCartService {

    @Resource
    private ItemCartMapper itemCartMapper;

    /**
     * 根据用户id查找购物车
     */
    @Override
    public PageDataVO selectItemCartByIds(Integer limit, Integer pages, String userId) {
        Integer integer = itemCartMapper.selectItemCartTotal(userId);
        int p=(pages-1)*limit;
        List<ItemCartVO> itemCartVOS = itemCartMapper.selectItemCartByIds(limit, p, userId);
        return new PageDataVO(integer,itemCartVOS);
    }

    /**
     * 放进购物车
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean putItemCartById(String userId, String commodityId) {
        ItemCart itemCart = new ItemCart();
        itemCart.setCartId(StringUtil.generateShortId());
        itemCart.setUserId(userId);
        itemCart.setCommodityId(commodityId);
        int result = itemCartMapper.insert(itemCart);
        return result>0;
    }


    /**
     * 批量放出购物车
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean outItemCartById(List<String> cartIds) {
        System.out.println(cartIds);
        if (!cartIds.isEmpty()&&cartIds.size()==0){
            return false;
        }else {
            QueryWrapper<ItemCart> wrapper = new QueryWrapper<>();
            wrapper.in("cart_id",cartIds);
            int result = itemCartMapper.delete(wrapper);
            return result>0;
        }
    }


}
