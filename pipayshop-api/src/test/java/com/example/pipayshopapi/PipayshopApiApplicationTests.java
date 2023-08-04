package com.example.pipayshopapi;

import com.example.pipayshopapi.entity.vo.HotelFacilityVO;
import com.example.pipayshopapi.entity.vo.ShopCommodityLiveInfoListVO;
import com.example.pipayshopapi.mapper.*;
import com.example.pipayshopapi.service.ItemOrderInfoService;
import com.example.pipayshopapi.service.ShopCommodityLiveInfoService;
import freemarker.template.SimpleDate;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Resource
    private ShopCommodityLiveInfoMapper shopCommodityLiveInfoMapper;
    @Resource
    private ShopCommodityLiveInfoService shopCommodityLiveInfoService;

    @Test
    void contextLoads() {

    }


}
