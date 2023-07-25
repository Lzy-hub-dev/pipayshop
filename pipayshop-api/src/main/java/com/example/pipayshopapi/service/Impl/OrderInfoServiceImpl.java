package com.example.pipayshopapi.service.Impl;

import com.example.pipayshopapi.entity.OrderInfo;
import com.example.pipayshopapi.mapper.OrderInfoMapper;
import com.example.pipayshopapi.service.IOrderInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单数据表 服务实现类
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements IOrderInfoService {

}
