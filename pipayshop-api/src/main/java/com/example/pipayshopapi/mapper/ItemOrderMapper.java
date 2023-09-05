package com.example.pipayshopapi.mapper;

import com.example.pipayshopapi.entity.ItemOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pipayshopapi.entity.vo.MyItemOrderInfoVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wzx
 * @since 2023-09-05
 */
public interface ItemOrderMapper extends BaseMapper<ItemOrder> {

    /**
     * 根据用户id查询网店的所有订单
     */
    List<MyItemOrderInfoVO> getMyOrderByUid(@Param("page") Integer page, @Param("limit") Integer limit, @Param("uid") String uid
            , @Param("status") Integer status);

    Integer getAllMyOrderByUid(@Param("uid") String uid,@Param("status") Integer status);
}
