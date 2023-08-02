package com.example.pipayshopapi.mapper;

import com.example.pipayshopapi.entity.ShopFollowFocus;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 粉丝关注表 Mapper 接口
 * </p>
 *
 * @author jjf
 * @since 2023-07-29
 */
@Mapper
public interface ShopFollowFocusMapper extends BaseMapper<ShopFollowFocus> {

    List<ShopFollowFocus> selectFollowIdListByShopId(@Param("shopId")String shopId);
}
