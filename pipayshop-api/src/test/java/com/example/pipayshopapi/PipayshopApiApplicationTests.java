package com.example.pipayshopapi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.pipayshopapi.entity.ItemCommodityInfo;
import com.example.pipayshopapi.entity.vo.ItemCommodityEvaluateVO;
import com.example.pipayshopapi.entity.vo.ItemInfoVO;
import com.example.pipayshopapi.entity.vo.commodityVO;
import com.example.pipayshopapi.mapper.ItemCommodityEvaluateMapper;
import com.example.pipayshopapi.mapper.ItemCommodityInfoMapper;
import com.example.pipayshopapi.mapper.ItemInfoMapper;
import com.example.pipayshopapi.service.ItemCommodityEvaluateService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
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
    @Test
    void contextLoads() {
        ItemInfoVO commodity1 = itemInfoMapper.getItemInfo("commodity1");
        System.out.println(commodity1);


    }

}
