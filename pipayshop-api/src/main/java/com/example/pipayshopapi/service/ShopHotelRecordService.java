package com.example.pipayshopapi.service;

import com.example.pipayshopapi.entity.ShopHotelRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;

/**
 * <p>
 * 记录酒店入住订单信息表 服务类
 * </p>
 *
 * @author zxb
 * @since 2023-08-02
 */
public interface ShopHotelRecordService extends IService<ShopHotelRecord> {

    /**
     * 获取房型剩余数量
     */
    Integer getInventory(String roomId,Date startTime,Date endTime);

    Boolean createShopHotelRecord(ShopHotelRecord shopHotelRecord);
}
