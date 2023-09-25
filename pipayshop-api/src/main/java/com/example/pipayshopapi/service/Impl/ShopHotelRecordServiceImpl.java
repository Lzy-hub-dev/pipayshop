package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.pipayshopapi.entity.ItemCommodityInfo;
import com.example.pipayshopapi.entity.ShopCommodityLiveInfo;
import com.example.pipayshopapi.entity.ShopHotelRecord;
import com.example.pipayshopapi.mapper.ItemCommodityInfoMapper;
import com.example.pipayshopapi.mapper.ShopCommodityLiveInfoMapper;
import com.example.pipayshopapi.mapper.ShopHotelRecordMapper;
import com.example.pipayshopapi.service.ShopHotelRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Wrapper;
import java.util.Date;

/**
 * <p>
 * 记录酒店入住订单信息表 服务实现类
 * </p>
 *
 * @author zxb
 * @since 2023-08-02
 */
@Service
public class ShopHotelRecordServiceImpl extends ServiceImpl<ShopHotelRecordMapper, ShopHotelRecord> implements ShopHotelRecordService {

    @Resource
    private ShopHotelRecordMapper shopHotelRecordMapper;

    @Resource
    private ShopCommodityLiveInfoMapper shopCommodityLiveInfoMapper;

    @Override
    public Integer getInventory(String roomId, Date startTime, Date endTime) {
        //根据roomId 找库存
        Integer Inventory = shopCommodityLiveInfoMapper.selectOne(new QueryWrapper<ShopCommodityLiveInfo>()
                .eq("room_id",roomId))
                .getInventory();
        Integer rent = shopHotelRecordMapper.getRentByTime(roomId,startTime,endTime);
        return Inventory - rent;
    }

    @Override
    public Boolean createShopHotelRecord(ShopHotelRecord shopHotelRecord) {
        String recordId = StringUtil.generateShortId();
        shopHotelRecord.setRecordId(recordId);
        return shopHotelRecordMapper.insert(shopHotelRecord) > 0;
    }

}
