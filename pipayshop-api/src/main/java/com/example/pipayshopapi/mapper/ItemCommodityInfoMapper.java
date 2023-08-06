package com.example.pipayshopapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pipayshopapi.entity.ItemCommodityInfo;
import com.example.pipayshopapi.entity.dto.ExamineCommodityDTO;
import com.example.pipayshopapi.entity.vo.*;
import com.example.pipayshopapi.entity.vo.CommodityVO;
import com.example.pipayshopapi.entity.vo.ItemCommodityInfoVO;
import com.example.pipayshopapi.entity.vo.ItemCommodityVO;
import com.example.pipayshopapi.entity.vo.itemCommoditiesVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

    List<CommodityVO> commodityOfCateList(@Param("secondCategoryId") Integer secondCategoryId, @Param("startIndex")Integer startIndex, @Param("number")Integer number);

    Integer  listCount(@Param("secondCategoryId") Integer secondCategoryId);

    List<CommodityVO> itemCommodityChoose(@Param("itemId") String itemId,@Param("brandId") String brandId);


    List<ItemCommodityInfoVO> selectCollectProductByUserId(@Param("page") Integer page, @Param("limit") Integer limit,@Param("userId") String userId);
    Integer selectAllCollectProductByUserId(@Param("userId") String userId);

    List<ItemCommodityInfoVO> selectHistoryProductByUserId(@Param("page") Integer page, @Param("limit") Integer limit,@Param("userId") String userId);
    Integer selectAllHistoryProductByUserId(@Param("userId") String userId);

    List<ItemCommodityVO> examineCommodityList(@Param("dto")ExamineCommodityDTO dto);

    List<ItemCommodityVO> commodityList(@Param("userId")String userId);

    List<ItemCommodityMinVO> getInfoByItemId(@Param("itemId")String itemId,
                                             @Param("page")Integer page,
                                             @Param("limit")Integer limit,
                                             @Param("price")Boolean price);


    List<itemCommoditiesVO> selectMembershipByCommodityIdList(@Param("list") List<String> commodityIdList);


    Integer getInfoSize(String itemId);

    String getItemIdByOrderId(@Param("orderId") String orderId);
}
