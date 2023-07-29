package com.example.pipayshopapi.service.Impl;

import com.example.pipayshopapi.entity.ShopOrderInfo;
import com.example.pipayshopapi.entity.vo.ItemOrderInfoVO;
import com.example.pipayshopapi.mapper.ShopOrderInfoMapper;
import com.example.pipayshopapi.service.ShopOrderInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 实体店订单数据表 服务实现类
 * </p>
 *
 * @author jjf
 * @since 2023-07-29
 */
@Service
public class ShopOrderInfoServiceImpl extends ServiceImpl<ShopOrderInfoMapper, ShopOrderInfo> implements ShopOrderInfoService {
    @Autowired
    private ShopOrderInfoMapper shopOrderInfoMapper;
    /**
     * 根据用户id查询 订单列表
     *
     * @param userId
     * @return
     */
    @Override
    public List<ItemOrderInfoVO> selectOrderByUerId(String userId) {
        return shopOrderInfoMapper.selectOrderByUerId(userId);
    }
}
