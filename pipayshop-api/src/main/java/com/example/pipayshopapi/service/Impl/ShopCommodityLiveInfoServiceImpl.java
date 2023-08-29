package com.example.pipayshopapi.service.Impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.entity.ShopCommodityLiveInfo;
import com.example.pipayshopapi.entity.ShopHotelRecord;
import com.example.pipayshopapi.entity.ShopInfo;
import com.example.pipayshopapi.entity.ShopOrderInfo;
import com.example.pipayshopapi.entity.dto.ShopHotelRecordDTO;
import com.example.pipayshopapi.entity.vo.*;
import com.example.pipayshopapi.mapper.*;
import com.example.pipayshopapi.service.ShopCommodityLiveInfoService;
import com.example.pipayshopapi.service.ShopHotelRecordService;
import com.example.pipayshopapi.util.StringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 酒店的房型表 服务实现类
 * </p>
 *
 * @author zxb
 * @since 2023-08-02
 */
@Service
public class ShopCommodityLiveInfoServiceImpl extends ServiceImpl<ShopCommodityLiveInfoMapper, ShopCommodityLiveInfo> implements ShopCommodityLiveInfoService {

    @Resource
    private ShopCommodityLiveInfoMapper shopCommodityLiveInfoMapper;

    @Resource
    private ShopHotelRecordService shopHotelRecordService;

    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private ShopEvaluateMapper shopEvaluateMapper;

    @Resource
    private ShopOrderInfoMapper shopOrderInfoMapper;

    @Resource
    private ShopHotelRecordMapper shopHotelRecordMapper;

    @Resource ShopInfoMapper shopInfoMapper;

    /**
     * 根据房型id查找房型的详细信息
     */
    @Override
    public ShopCommodityLiveInfoVO selectShopLiveByRoomId(String roomId)  {
        ShopCommodityLiveInfoVO shopCommodityLiveInfoVO = shopCommodityLiveInfoMapper.selectByRoomId(roomId);
        String basic = shopCommodityLiveInfoMapper.getBasic(roomId);
        String bath = shopCommodityLiveInfoMapper.getBath(roomId);
        String appliance = shopCommodityLiveInfoMapper.getAppliance(roomId);
        List<HotelFacilityVO> basicList = null;
        List<HotelFacilityVO> bathList = null;
        List<HotelFacilityVO> applianceList = null;

        try {
            if(basic != null){
                basicList = objectMapper.readValue(basic, new TypeReference<List<HotelFacilityVO>>() {});
                shopCommodityLiveInfoVO.setBasics(basicList);
            }
            if(basicList != null){
                bathList = objectMapper.readValue(bath, new TypeReference<List<HotelFacilityVO>>() {});
                shopCommodityLiveInfoVO.setBath(bathList);
            }
            if(appliance != null){
                applianceList = objectMapper.readValue(appliance, new TypeReference<List<HotelFacilityVO>>() {});
                shopCommodityLiveInfoVO.setAppliance(applianceList);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return shopCommodityLiveInfoVO;
    }



    /**
     * 增加房型的详细信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertShopLiveInfo(ShopCommodityLiveInfoVO1 shopCommodityLiveInfoVO1) {
        //判断用户是否是vip
        int count = shopInfoMapper.selectCount(new QueryWrapper<ShopInfo>()
                .eq("shop_id", shopCommodityLiveInfoVO1.getShopId())
                .eq("status", 0)
                .eq("membership", 1)).intValue();

        //shop-1剩余数量
        int shopId = shopInfoMapper.update(null, new UpdateWrapper<ShopInfo>()
                .eq("shop_id", shopCommodityLiveInfoVO1.getShopId())
                .setSql("upload_commodity_balance= upload_commodity_balance -1"));
        if (shopId < 1){throw new RuntimeException();}
        // 属性转移
        ShopCommodityLiveInfo shopCommodityLiveInfo = new ShopCommodityLiveInfo(null,StringUtil.generateShortId(),
                shopCommodityLiveInfoVO1.getRoomTypeName(),shopCommodityLiveInfoVO1.getShopId(),
                shopCommodityLiveInfoVO1.getInventory(),shopCommodityLiveInfoVO1.getDetail(),
                JSON.toJSONString(shopCommodityLiveInfoVO1.getTagList()), JSON.toJSONString(shopCommodityLiveInfoVO1.getImageList()),
                shopCommodityLiveInfoVO1.getLand(),shopCommodityLiveInfoVO1.getRoom(),
                shopCommodityLiveInfoVO1.getRestRoom(),shopCommodityLiveInfoVO1.getBed(),
                shopCommodityLiveInfoVO1.getAdult(),shopCommodityLiveInfoVO1.getChildren(),
                shopCommodityLiveInfoVO1.getRestricted(),JSON.toJSONString(shopCommodityLiveInfoVO1.getBasics()),
                JSON.toJSONString(shopCommodityLiveInfoVO1.getBath()),JSON.toJSONString(shopCommodityLiveInfoVO1.getAppliance()),
                shopCommodityLiveInfoVO1.getPrice(),0,(count==1)?1:0,shopCommodityLiveInfoVO1.getImageList().get(0),
                2,shopCommodityLiveInfoVO1.getBedType(),shopCommodityLiveInfoVO1.getFloor(),shopCommodityLiveInfoVO1.getIsAdd(),0);
        int result = shopCommodityLiveInfoMapper.insert(shopCommodityLiveInfo);
        return result>0;
    }

    /**
     * 根据房型id更改房型的详细信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateShopLiveInfo(ShopCommodityLiveInfo shopCommodityLiveInfo) {
        int result = shopCommodityLiveInfoMapper.update(shopCommodityLiveInfo, new QueryWrapper<ShopCommodityLiveInfo>()
                  .eq("room_id", shopCommodityLiveInfo.getRoomId()));
        return result > 0;
    }

    /**
     * 根据房型id删除房型的详细信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteShopLiveInfo(String roomId) {
        int result = shopCommodityLiveInfoMapper.update(null, new UpdateWrapper<ShopCommodityLiveInfo>()
                .eq("room_id", roomId)
                .set("del_flag", 1));
        return result>0;
    }

    /**
     * 根据实体店id和入住时间和离店时间来搜索房型
     * @param shopId
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public List<ShopCommodityLiveInfoListVO> selectShopCommodityLiveInfoList(String shopId, Date startTime, Date endTime) {
        List<ShopCommodityLiveInfoListVO> shopCommodityLiveInfoListVOS = shopCommodityLiveInfoMapper.selectShopCommodityLiveInfoList(shopId);
        for (ShopCommodityLiveInfoListVO shopCommodityLiveInfoListVO : shopCommodityLiveInfoListVOS) {
            shopCommodityLiveInfoListVO.setInventory(shopHotelRecordService.getInventory(shopCommodityLiveInfoListVO.getRoomId(),startTime,endTime));
        }
        return shopCommodityLiveInfoListVOS;
    }

    /**
     * 获取实体店评价数
     */
    @Override
    public Integer selectShopEvaluateCount(String shopId) {

        return  shopEvaluateMapper.selectShopEvaluateCount(shopId);
    }

    /**
     * 提交入住酒店
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean applyShopCommodityLive(ShopHotelRecordDTO shopHotelRecordDTO) {

        // 生成订单id
        String orderId=StringUtil.generateShortId();
        // 订单属性转移
        ShopOrderInfo shopOrderInfo = new ShopOrderInfo();
        shopOrderInfo.setOrderId(orderId);
        shopOrderInfo.setCommodityId(shopHotelRecordDTO.getCommodityId());
        shopOrderInfo.setTransactionAmount(shopHotelRecordDTO.getTransactionAmount());
        shopOrderInfo.setUid(shopHotelRecordDTO.getUid());
        shopOrderInfo.setShopId(shopHotelRecordDTO.getShopId());
        shopOrderInfo.setOrderStatus(1); // 下单状态
        int insert = shopOrderInfoMapper.insert(shopOrderInfo);
        // 酒店记录属性转移
        ShopHotelRecord shopHotelRecord = new ShopHotelRecord();
        shopHotelRecord.setRecordId(StringUtil.generateShortId());
        shopHotelRecord.setRoomId(shopHotelRecordDTO.getRoomId());
        shopHotelRecord.setName(shopHotelRecordDTO.getName());
        shopHotelRecord.setPhone(shopHotelRecordDTO.getPhone());
        shopHotelRecord.setStartTime(shopHotelRecordDTO.getStartTime());
        shopHotelRecord.setEndTime(shopHotelRecordDTO.getEndTime());
        shopHotelRecord.setOrderId(orderId);
        insert += shopHotelRecordMapper.insert(shopHotelRecord);
        return insert > 1;
    }

    @Override
    public PageDataVO selectShopCommodityLiveInfoVO(Integer limit, Integer pages) {
        Integer integer = shopCommodityLiveInfoMapper.selectAllShopCommodityLiveVO();
        List<ShopCommodityLiveVO> shopCommodityLiveVOS = shopCommodityLiveInfoMapper.selectShopCommodityLiveVO(limit, (pages-1)*limit);
        return new PageDataVO(integer,shopCommodityLiveVOS);
    }

    @Override
    public boolean insertShopLive(ShopCommodityLiveInfo shopCommodityLiveInfo) {
        shopCommodityLiveInfo.setRoomId(StringUtil.generateShortId());
        int result = shopCommodityLiveInfoMapper.insert(shopCommodityLiveInfo);
        return result>0;
    }

    @Override
    public boolean updateShopLive(ShopCommodityLiveInfoUpVO shopCommodityLiveInfoUpVO) {
        String roomId = StringUtil.generateShortId();
        ShopCommodityLiveInfo shopCommodityLiveInfo = new ShopCommodityLiveInfo(null, roomId, shopCommodityLiveInfoUpVO.getRoomTypeName(),
                null, shopCommodityLiveInfoUpVO.getInventory(),
                shopCommodityLiveInfoUpVO.getDetail(), shopCommodityLiveInfoUpVO.getTagList(),
                shopCommodityLiveInfoUpVO.getImageList(), shopCommodityLiveInfoUpVO.getLand(),
                shopCommodityLiveInfoUpVO.getRoom(), shopCommodityLiveInfoUpVO.getRestRoom(),
                shopCommodityLiveInfoUpVO.getBed(), shopCommodityLiveInfoUpVO.getAdult(),
                shopCommodityLiveInfoUpVO.getChildren(), shopCommodityLiveInfoUpVO.getRestricted(),
                shopCommodityLiveInfoUpVO.getBasics(), shopCommodityLiveInfoUpVO.getBath(),
                shopCommodityLiveInfoUpVO.getAppliance(), shopCommodityLiveInfoUpVO.getPrice(),
                null, null, shopCommodityLiveInfoUpVO.getAvatarImag(), null, shopCommodityLiveInfoUpVO.getBedType(),
                shopCommodityLiveInfoUpVO.getFloor(), shopCommodityLiveInfoUpVO.getIsAdd(),null);
        int result = shopCommodityLiveInfoMapper.update(shopCommodityLiveInfo, new UpdateWrapper<ShopCommodityLiveInfo>()
                .eq("room_id", shopCommodityLiveInfoUpVO.getRoomId()));
        return result > 0;
    }


}
