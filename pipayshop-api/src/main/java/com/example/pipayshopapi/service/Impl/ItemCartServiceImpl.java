package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.entity.ItemCart;
import com.example.pipayshopapi.entity.vo.ItemCartVO;
import com.example.pipayshopapi.mapper.ItemCartMapper;
import com.example.pipayshopapi.service.ItemCartService;
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
    public List<ItemCartVO> selectItemCartByIds(String userId) {
        return itemCartMapper.selectItemCartByIds(userId);
    }

    /**
     * 放进购物车
     */
    @Override
    public boolean putItemCartById(String userId, String commodityId,Integer sumCount,String commoditySpec) {
        ItemCart itemCart = new ItemCart();
        itemCart.setCartId(StringUtil.generateShortId());
        itemCart.setUserId(userId);
        itemCart.setCommodityId(commodityId);
        itemCart.setSumCount(sumCount);
        itemCart.setCommoditySpec(commoditySpec);
        int result = itemCartMapper.insert(itemCart);
        return result > 0;
    }


    /**
     * 批量放出购物车
     */
    @Override
    public boolean outItemCartById(List<String> commodityId,String cartId) {
        if (commodityId == null || commodityId.size()==0){
            return false;
        }else {
            QueryWrapper<ItemCart> wrapper = new QueryWrapper<>();
            wrapper.eq("cart_id",cartId).in("commodity_id",commodityId);
            int result = itemCartMapper.delete(wrapper);
            return result > 0;
        }
    }


}
