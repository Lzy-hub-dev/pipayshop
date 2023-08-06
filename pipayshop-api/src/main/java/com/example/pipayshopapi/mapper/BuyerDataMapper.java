package com.example.pipayshopapi.mapper;

import com.example.pipayshopapi.entity.BuyerData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 网店商品收货地址 Mapper 接口
 * </p>
 *
 * @author zxb
 * @since 2023-07-31
 */
@Mapper
public interface BuyerDataMapper extends BaseMapper<BuyerData> {


    @Select("select user_id from buyer_data where buyer_data_id = #{buyerDataId}")
    String getUserId(@Param("buyerDataId") String buyerDataId);
}
