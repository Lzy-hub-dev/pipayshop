package com.example.pipayshopapi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.pipayshopapi.entity.ItemCommodityInfo;
import com.example.pipayshopapi.entity.vo.commodityVO;
import com.example.pipayshopapi.mapper.ItemCommodityInfoMapper;
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
    @Test
    void contextLoads() {
        List<commodityVO> commodityVOS = itemCommodityInfoMapper.itemCommodityChoose("1001", "5");
        System.out.println(commodityVOS);

    }

}
