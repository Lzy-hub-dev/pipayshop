package com.example.pipayshopapi;

import com.example.pipayshopapi.entity.vo.ShopCommodityLiveInfoListVO;
import com.example.pipayshopapi.mapper.*;
import com.example.pipayshopapi.service.ItemOrderInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
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
    @Test
    void contextLoads() {
        List<ShopCommodityLiveInfoListVO> shopCommodityLiveInfoListVOS = shopCommodityLiveInfoMapper.selectShopCommodityLiveInfoList("1002");
        for (ShopCommodityLiveInfoListVO shopCommodityLiveInfoListVO : shopCommodityLiveInfoListVOS) {
            System.out.println(shopCommodityLiveInfoListVO);
        }

    }

}
