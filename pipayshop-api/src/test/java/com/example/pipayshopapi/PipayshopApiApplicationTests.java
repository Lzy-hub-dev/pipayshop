package com.example.pipayshopapi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.pipayshopapi.entity.ItemCommodityInfo;
import com.example.pipayshopapi.entity.ShopCategoryTop;
import com.example.pipayshopapi.entity.dto.ApplyItemCommodityDTO;
import com.example.pipayshopapi.entity.vo.*;
import com.example.pipayshopapi.mapper.*;
import com.example.pipayshopapi.service.ItemCommodityEvaluateService;
import com.example.pipayshopapi.service.ItemCommodityInfoService;
import com.example.pipayshopapi.service.ItemOrderInfoService;
import com.example.pipayshopapi.service.ShopCategoryTopService;
import com.example.pipayshopapi.util.StringUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarOutputStream;
import java.util.stream.Collectors;

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
    private ShopCategoryTopService shopCategoryTopService;
    @Resource
    private ItemCommodityInfoService itemCommodityInfoService;

    @Test
    void contextLoads() {
//        List<ShopCategoryTop> allShopCategoryTopList = shopCategoryTopService.getAllShopCategoryTopList();
//        for (ShopCategoryTop shopCategoryTop : allShopCategoryTopList) {
//            System.out.println(shopCategoryTop);
//        }
        ApplyItemCommodityDTO applyItemCommodityDTO = new ApplyItemCommodityDTO();
        applyItemCommodityDTO.setItemCommodityName("2221");
        applyItemCommodityDTO.setPrice(new BigDecimal(123));
        applyItemCommodityDTO.setShopId("21");
        applyItemCommodityDTO.setColorList("[]");
        ItemCommodityInfo itemCommodityInfo = new ItemCommodityInfo();
        itemCommodityInfo.setCommodityId(StringUtil.generateShortId());
        itemCommodityInfo.setBrandId(applyItemCommodityDTO.getBrandId());
        itemCommodityInfo.setOriginPrice(applyItemCommodityDTO.getOriginPrice());
        itemCommodityInfo.setPrice(applyItemCommodityDTO.getPrice());
        itemCommodityInfo.setDegreeLoss(applyItemCommodityDTO.getDegreeLoss());
        itemCommodityInfo.setItemCommodityName(applyItemCommodityDTO.getItemCommodityName());
        itemCommodityInfo.setOriginAddress(applyItemCommodityDTO.getOriginAddress());
        itemCommodityInfo.setDetails(applyItemCommodityDTO.getDetails());
        itemCommodityInfo.setFreeShippingNum(applyItemCommodityDTO.getFreeShippingNum());
        itemCommodityInfo.setCategoryId(applyItemCommodityDTO.getCategoryId());
        itemCommodityInfo.setInventory(applyItemCommodityDTO.getInventory());
        itemCommodityInfo.setColorList(applyItemCommodityDTO.getColorList());
//        itemCommodityInfo.se
        int result = itemCommodityInfoMapper.insert(itemCommodityInfo);
        System.out.println(result);
    }

}
