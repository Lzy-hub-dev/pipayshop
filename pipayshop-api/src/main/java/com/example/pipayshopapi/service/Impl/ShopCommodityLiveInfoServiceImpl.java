package com.example.pipayshopapi.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.entity.ShopCommodityLiveInfo;
import com.example.pipayshopapi.entity.ShopHotelRecord;
import com.example.pipayshopapi.entity.ShopInfo;
import com.example.pipayshopapi.entity.ShopOrderInfo;
import com.example.pipayshopapi.entity.dto.ShopCommodityLiveInfoListDTO1;
import com.example.pipayshopapi.entity.dto.ShopHotelRecordDTO;
import com.example.pipayshopapi.entity.vo.*;
import com.example.pipayshopapi.mapper.*;
import com.example.pipayshopapi.service.ShopCommodityLiveInfoService;
import com.example.pipayshopapi.service.ShopHotelRecordService;
import com.example.pipayshopapi.util.FileUploadUtil;
import com.example.pipayshopapi.util.StringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    @Resource
    ShopInfoMapper shopInfoMapper;

    @Resource
    ImageMapper imageMapper;
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
        List<HotelFacilityVO> bathList;
        List<HotelFacilityVO> applianceList;

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
       /*  int count = shopInfoMapper.selectCount(new QueryWrapper<ShopInfo>()
                .eq("shop_id", shopCommodityLiveInfoVO1.getShopId())
                .eq("status", 0)
                .eq("membership", 1)).intValue(); */
        //上面要状态干嘛？已经打烊的酒店不能发房间？
        int count = shopInfoMapper.selectCount(new QueryWrapper<ShopInfo>()
                .eq("shop_id", shopCommodityLiveInfoVO1.getShopId())
                .eq("membership", 1)).intValue();

        //shop-1剩余数量
        int shopId = shopInfoMapper.update(null, new UpdateWrapper<ShopInfo>()
                .eq("shop_id", shopCommodityLiveInfoVO1.getShopId())
                .setSql("upload_commodity_balance= upload_commodity_balance -1"));
        if (shopId < 1){throw new RuntimeException();}
        // 属性转移
        ShopCommodityLiveInfo shopCommodityLiveInfo = new ShopCommodityLiveInfo();
        BeanUtils.copyProperties(shopCommodityLiveInfoVO1, shopCommodityLiveInfo);
        String roomId = StringUtil.generateShortId();
        shopCommodityLiveInfo.setRoomId(roomId);
        shopCommodityLiveInfo.setTagList(JSON.toJSONString(shopCommodityLiveInfoVO1.getTagList()));
        List<String> imageList = shopCommodityLiveInfoVO1.getImageList();
        List<String> images = imageList.stream()
                .parallel()
                .map(imageId -> imageMapper.selectPath(imageId))
                .collect(Collectors.toList());
        shopCommodityLiveInfo.setImageList(JSON.toJSONString(images));
        shopCommodityLiveInfo.setBasics(JSON.toJSONString(shopCommodityLiveInfoVO1.getBasics()));
        shopCommodityLiveInfo.setBath(JSON.toJSONString(shopCommodityLiveInfoVO1.getBath()));
        shopCommodityLiveInfo.setAppliance(JSON.toJSONString(shopCommodityLiveInfoVO1.getAppliance()));
        shopCommodityLiveInfo.setRecommended((count==1)?1:0);
        shopCommodityLiveInfo.setAvatarImag(images.get(0));
        int result = shopCommodityLiveInfoMapper.insert(shopCommodityLiveInfo);
        return result>0;
    }

    /**
     * 根据房型id更改房型的详细信息
     */
    @Override
    public boolean updateShopLiveInfo(ShopCommodityLiveInfo shopCommodityLiveInfo) {
        int result = shopCommodityLiveInfoMapper.update(shopCommodityLiveInfo, new QueryWrapper<ShopCommodityLiveInfo>()
                  .eq("room_id", shopCommodityLiveInfo.getRoomId()));
        return result > 0;
    }

    /**
     * 根据房型id删除房型的详细信息
     */
    @Override
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

    @Override
    public String roomTopImageUp(MultipartFile multipartFile) {
        return FileUploadUtil.allUploadImageData(multipartFile, imageMapper, FileUploadUtil.ROOM_TOP_IMG,null);
    }

    @Override
    public String roomImageUp(MultipartFile multipartFile) {
        return FileUploadUtil.allUploadImageData(multipartFile, imageMapper, FileUploadUtil.ROOM_IMAGE_LIST,null);
    }

    /**
     * 根据价格升降序，价格条件可有可无
     * @param shopInfo
     * @return
     */
    @Override
    public List<ShopCommodityLiveInfoVO2> selectAllByPrice(ShopCommodityLiveInfoListDTO1 shopInfo) throws JsonProcessingException {
        //得到数据
        List<ShopCommodityLiveInfoVO3> shopInfoLists = shopCommodityLiveInfoMapper.selectALlByPrice(shopInfo);
        List<ShopCommodityLiveInfoVO2> shopInfoLists2 = new ArrayList<>();
        for (ShopCommodityLiveInfoVO3 shopInfoVO : shopInfoLists) {
            if (shopInfoVO != null) {
                // System.out.println(shopInfoVO);
                ModelMapper modelMapper = new ModelMapper();
                //接收转换后的值
                ShopCommodityLiveInfoVO2 s2 = new ShopCommodityLiveInfoVO2();
                s2  = modelMapper.map(shopInfoVO, ShopCommodityLiveInfoVO2.class);
              /*   s2.setAddress(shopInfoVO.getAddress());
                s2.setAdult(shopInfoVO.getAdult());
                s2.setBed(shopInfoVO.getBed());
                s2.setBedType(shopInfoVO.getBedType());
                s2.setChildren(shopInfoVO.getChildren());
                s2.setDetail(shopInfoVO.getDetail());
                s2.setFloor(shopInfoVO.getFloor());
                s2.setInventory(shopInfoVO.getInventory());
                s2.setIsAdd(shopInfoVO.getIsAdd());
                s2.setLand(shopInfoVO.getLand());
                s2.setLatitude(shopInfoVO.getLatitude());
                s2.setLongitude(shopInfoVO.getLongitude());
                s2.setMonthlySales(shopInfoVO.getMonthlySales());
                s2.setPhone(shopInfoVO.getPhone());
                s2.setPrice(shopInfoVO.getPrice());
                s2.setRestricted(shopInfoVO.getRestricted());
                s2.setRestRoom(shopInfoVO.getRestRoom());
                s2.setRoom(shopInfoVO.getRoom());
                s2.setRoomId(shopInfoVO.getRoomId());
                s2.setRoomTypeName(shopInfoVO.getRoomTypeName());
 */
                //tagList
                JSONArray TagArray = JSON.parseArray(shopInfoVO.getTagList());
                List<String> tagList = JSON.parseArray(TagArray.toJSONString(), String.class);
                s2.setTagList(tagList);
                //imageList
                JSONArray imageArray = JSON.parseArray(shopInfoVO.getImageList());
                List<String> imageList = JSON.parseArray(imageArray.toJSONString(), String.class);
                s2.setImageList(imageList);
              //  解析基础设施
                JSONArray basicArray = JSON.parseArray(shopInfoVO.getBasics());
                List<HotelFacilityVO>  basicList = JSON.parseArray(basicArray.toJSONString(),HotelFacilityVO.class);
                s2.setBasics(basicList);
              //  解析淋浴
                JSONArray bathArray = JSON.parseArray(shopInfoVO.getBath());
                List<HotelFacilityVO>  bathList = JSON.parseArray(bathArray.toJSONString(),HotelFacilityVO.class);
                s2.setBath(bathList);

                // 解析电器设施
                JSONArray applianceArray = JSON.parseArray(shopInfoVO.getAppliance());
                List<HotelFacilityVO>  applianceList = JSON.parseArray(applianceArray.toJSONString(),HotelFacilityVO.class);
                s2.setAppliance(applianceList);

                shopInfoLists2.add(s2);
                // System.out.println(s2);
                // System.out.println("----------------");
            }
        }
        return shopInfoLists2;
    }
}
