package com.example.pipayshopapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pipayshopapi.entity.ItemCommodityInfo;
import com.example.pipayshopapi.entity.vo.CommodityVO;
import com.example.pipayshopapi.entity.vo.ItemCommodityInfoVO;
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

    List<CommodityVO> itemCommodityChoose(String itemId, String brandId);


    List<ItemCommodityInfoVO> selectCollectProductByUserId(String userId);




    List<ItemCommodityInfoVO> selectHistoryProductByUserId(String userId);

    List<ItemCommodityInfoVO> examineCommodityList(@Param("userId") String userId, @Param("examineStatus")Integer examineStatus);

    List<ItemCommodityInfoVO> commodityList(String userId);


}
