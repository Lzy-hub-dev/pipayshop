package com.example.pipayshopapi.mapper;

import com.example.pipayshopapi.entity.ItemCommodityEvaluate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pipayshopapi.entity.vo.EvaluateVO;
import com.example.pipayshopapi.entity.vo.ItemCommodityEvaluateVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zxb
 * @since 2023-07-27
 */
@Mapper
public interface ItemCommodityEvaluateMapper extends BaseMapper<ItemCommodityEvaluate> {

    List<ItemCommodityEvaluateVO> getItemCommodityEvaluates(@Param("commodityId")String commodityId,
                                                            @Param("page")Integer page,
                                                            @Param("limit")Integer limit);

    Integer getItemCommodityEvaluatesSum(@Param("commodityId")String commodityId);

    List<EvaluateVO> getItemCommodityEvaluate(@Param("itemId")String itemId,
                                              @Param("page")Integer page,
                                              @Param("limit")Integer limit);

    Integer getInfoSize(@Param("limit")String itemId);
}
