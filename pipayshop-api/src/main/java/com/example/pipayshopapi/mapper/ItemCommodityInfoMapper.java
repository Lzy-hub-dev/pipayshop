package com.example.pipayshopapi.mapper;

import com.example.pipayshopapi.entity.ItemCommodityInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pipayshopapi.entity.vo.CommodityDetailVO;
import com.example.pipayshopapi.entity.vo.commodityVO;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.omg.CORBA.INTERNAL;

import java.util.List;

/**
 * <p>
 * 网店的商品表 Mapper 接口
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@Mapper
public interface ItemCommodityInfoMapper extends BaseMapper<ItemCommodityInfo> {

    List<commodityVO> commodityOfCateList(@Param("categoryId") Integer categoryId, @Param("startIndex")Integer startIndex, @Param("number")Integer number);

    Integer  listCount(@Param("categoryId") Integer categoryId);

    List<commodityVO> itemCommodityChoose(String itemId,String brandId);

    CommodityDetailVO itemCommodityDetail(String commodityId);

}
