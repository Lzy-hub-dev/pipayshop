package com.example.pipayshopapi.service.Impl;

import com.example.pipayshopapi.entity.ItemOrderInfo;
import com.example.pipayshopapi.entity.vo.ItemOrderInfoVO;
import com.example.pipayshopapi.mapper.ItemOrderInfoMapper;
import com.example.pipayshopapi.service.ItemOrderInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 商户订单数据表 服务实现类
 * </p>
 *
 * @author zxb
 * @since 2023-07-28
 */
@Service
public class ItemOrderInfoServiceImpl extends ServiceImpl<ItemOrderInfoMapper, ItemOrderInfo> implements ItemOrderInfoService {

    @Resource
    private ItemOrderInfoMapper itemOrderInfoMapper;
    @Override
    public List<ItemOrderInfoVO> selectItemOrders(String userId) {
        List<ItemOrderInfoVO> itemOrderInfoVOS = itemOrderInfoMapper.selectItemOrders(userId);
        return  itemOrderInfoVOS;
    }
}
