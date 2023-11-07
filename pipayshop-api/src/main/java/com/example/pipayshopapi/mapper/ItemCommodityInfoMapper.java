package com.example.pipayshopapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pipayshopapi.entity.ItemCommodityInfo;
import com.example.pipayshopapi.entity.dto.ApplyItemCommodityDTO;
import com.example.pipayshopapi.entity.dto.ItemSearchConditionDTO;
import com.example.pipayshopapi.entity.vo.*;
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

    List<CommodityVO> commodityOfCateList(@Param("secondCategoryId") Integer secondCategoryId,@Param("countryCode") String countryCode, @Param("startIndex")Integer startIndex, @Param("number")Integer number);

    Integer  listCount(@Param("secondCategoryId") Integer secondCategoryId);

    List<CommodityVO> itemCommodityChoose(@Param("itemId") String itemId,@Param("brandId") String brandId);


    List<ItemCollectionVO> selectCollectProductByUserId(@Param("page") Integer page, @Param("limit") Integer limit,@Param("userId") String userId);
    Integer selectAllCollectProductByUserId(@Param("userId") String userId);

    List<ItemCollectionVO> selectHistoryProductByUserId(@Param("page") Integer page, @Param("limit") Integer limit,@Param("userId") String userId);
    Integer selectAllHistoryProductByUserId(@Param("userId") String userId);

    List<AuditItemVO> examineCommodityList(@Param("itemId")String itemId);

    List<ItemCommodityVO> commodityList(@Param("itemId")String itemId);

    List<ItemCommodityMinVO> getInfoByItemId(@Param("itemId")String itemId,
                                             @Param("page")Integer page,
                                             @Param("limit")Integer limit,
                                             @Param("price")Boolean price);


    List<ItemCommoditiesVO> selectMembershipByCommodityIdList(@Param("list") List<String> commodityIdList, @Param("priceOrder")Integer priceOrder);


    Integer getInfoSize(String itemId);

    String getItemIdByOrderId(@Param("orderId") String orderId);
    /**
     * 根据购买数量、商品id
     */
    int reduceStock(@Param("num")Integer num,@Param("commodityId")String commodityId);

    /**
     * 根据购买数量、商品id =》商品库存复原
     */
    int addStock(@Param("num")Integer num,@Param("commodityId")String commodityId);

    List<ItemCommoditiesVO> itemSearchCommodity(@Param("dto") ItemSearchConditionDTO dto);

    int itemSearchCommodityCount(@Param("dto") ItemSearchConditionDTO dto);


    Integer selectPiShoper(String itemId);
}
