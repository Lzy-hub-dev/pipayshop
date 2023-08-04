package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.pipayshopapi.entity.ShopCommodityLiveInfo;
import com.example.pipayshopapi.entity.ShopHotelRecord;
import com.example.pipayshopapi.entity.ShopOrderInfo;
import com.example.pipayshopapi.entity.dto.ShopHotelRecordDTO;
import com.example.pipayshopapi.entity.vo.ShopCommodityLiveInfoListVO;
import com.example.pipayshopapi.mapper.ShopCommodityLiveInfoMapper;
import com.example.pipayshopapi.mapper.ShopEvaluateMapper;
import com.example.pipayshopapi.mapper.ShopHotelRecordMapper;
import com.example.pipayshopapi.mapper.ShopOrderInfoMapper;
import com.example.pipayshopapi.service.ShopCommodityLiveInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.service.ShopHotelRecordService;
import com.example.pipayshopapi.util.StringUtil;
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
    private ShopEvaluateMapper shopEvaluateMapper;

    @Resource
    private ShopHotelRecordMapper shopHotelRecordMapper;

    @Resource
    private ShopOrderInfoMapper shopOrderInfoMapper;


    /**
     * 根据房型id查找房型的详细信息
     */
    // TODO
    @Override
    public ShopCommodityLiveInfo selectShopLiveByRoomId(String roomId) {
        return shopCommodityLiveInfoMapper.selectOne(new QueryWrapper<ShopCommodityLiveInfo>()
                .eq("room_id", roomId)
                .eq("del_flag", 0)
                .eq("status", 1));
    }



    /**
     * 增加房型的详细信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertShopLiveInfo(ShopCommodityLiveInfo shopCommodityLiveInfo) {
        shopCommodityLiveInfo.setRoomId(StringUtil.generateShortId());
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
}
