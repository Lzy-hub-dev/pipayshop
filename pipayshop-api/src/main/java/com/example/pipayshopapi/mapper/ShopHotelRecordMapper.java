package com.example.pipayshopapi.mapper;

import com.example.pipayshopapi.entity.ShopHotelRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * <p>
 * 记录酒店入住订单信息表 Mapper 接口
 * </p>
 *
 * @author zxb
 * @since 2023-08-02
 */
@Mapper
public interface ShopHotelRecordMapper extends BaseMapper<ShopHotelRecord> {

    /**
     * 获取房型剩余数量
     * @param roomId
     * @param startTime
     * @param endTime
     * @return
     */
    Integer getRentByTime(@Param("roomId") Integer roomId,@Param("startTime") Date startTime,@Param("endTime") Date endTime);
}
