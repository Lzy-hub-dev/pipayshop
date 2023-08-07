package com.example.pipayshopapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pipayshopapi.entity.ItemCommodityEvaluate;
import com.example.pipayshopapi.entity.vo.EvaluateVO;
import com.example.pipayshopapi.entity.vo.ItemCommodityEvaluateAddVO;
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

    List<EvaluateVO> getItemCommodityEvaluate(@Param("itemId") String itemId,
                                              @Param("page") Integer page,
                                              @Param("limit") Integer limit);

    Integer getInfoSize(@Param("itemId")String itemId);

    int insertItemCommodityEvaluateAddVO(@Param("evaluateId")String evaluateId,
                                         @Param("userId") String userId,
                                         @Param("itemId") String itemId,
                                         @Param("commodityId")String commodityId,
                                         @Param("evaluate") String evaluate,
                                         @Param("score") Double score);
}
