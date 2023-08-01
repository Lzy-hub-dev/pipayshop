package com.example.pipayshopapi;

import com.example.pipayshopapi.mapper.*;
import com.example.pipayshopapi.service.ItemOrderInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class PipayshopApiApplicationTests {

    @Resource
    private ItemCommodityInfoMapper itemCommodityInfoMapper;

    @Resource
    private ItemCommodityEvaluateMapper itemCommodityEvaluateMapper;

    @Resource
    private ItemInfoMapper itemInfoMapper;

    @Resource
    private ItemOrderInfoMapper itemOrderInfoMapper;

    @Resource
    private ItemOrderInfoService orderInfoService;

    @Resource
    private ShopOrderInfoMapper shopOrderInfoMapper;
    @Test
    void contextLoads() {


    }

}
