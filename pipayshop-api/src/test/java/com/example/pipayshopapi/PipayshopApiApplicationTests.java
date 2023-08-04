package com.example.pipayshopapi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.pipayshopapi.entity.ShopCommodityLiveInfo;
import com.example.pipayshopapi.entity.ShopOrderInfo;
import com.example.pipayshopapi.entity.vo.ShopCommodityLiveInfoListVO;
import com.example.pipayshopapi.mapper.*;
import com.example.pipayshopapi.service.ItemOrderInfoService;
import com.example.pipayshopapi.service.ShopCommodityLiveInfoService;
import com.example.pipayshopapi.util.StringUtil;
import freemarker.template.SimpleDate;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;
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

    @Resource
    private ShopEvaluateMapper shopEvaluateMapper;


    @Test
    void contextLoads() {
//        shopCommodityLiveInfoService.selectShopCommodityLiveInfoList("1002",new SimpleDate(new Date()))
//        Integer integer = shopEvaluateMapper.selectShopEvaluateCount("1002");
//        System.out.println(integer);
/*        ShopCommodityLiveInfo shopCommodityLiveInfo = new ShopCommodityLiveInfo();


        List list = new ArrayList<>();

        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("name","冰箱");
        jsonObject1.put("flag",true);
        list.add(jsonObject1);
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("name","wifi");
        jsonObject2.put("flag",true);
        list.add(jsonObject2);
        JSONObject jsonObject3 = new JSONObject();
        jsonObject3.put("name","停车位");
        jsonObject3.put("flag",false);
        list.add(jsonObject3);
        String jsonString = JSONObject.toJSONString(list);
        shopCommodityLiveInfo.setBasics(jsonString);
        int update = shopCommodityLiveInfoMapper.update(shopCommodityLiveInfo,new UpdateWrapper<ShopCommodityLiveInfo>().eq("room_id","102"));
        System.out.println(update);*/
    }

    @Test
    void test1(){
        List list = new ArrayList<>();
        list.add("大床");
        list.add("无窗");
        list.add("1.8");
        String jsonString = JSON.toJSONString(list);
        ShopCommodityLiveInfo shopCommodityLiveInfo = new ShopCommodityLiveInfo();
        shopCommodityLiveInfo.setTagList(jsonString);
        int update = shopCommodityLiveInfoMapper.update(shopCommodityLiveInfo, new UpdateWrapper<ShopCommodityLiveInfo>().eq("room_id", "101"));
        System.out.println(update);
    }
}
