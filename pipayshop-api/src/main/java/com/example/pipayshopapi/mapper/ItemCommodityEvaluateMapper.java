package com.example.pipayshopapi.mapper;

import com.example.pipayshopapi.entity.ItemCommodityEvaluate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pipayshopapi.entity.vo.EvaluateVO;
import com.example.pipayshopapi.entity.vo.ItemCommodityEvaluateVO;
import org.apache.ibatis.annotations.Mapper;

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

    List<ItemCommodityEvaluateVO> getItemCommodityEvaluates(String commodityId,Integer page,Integer limit);

    Integer getItemCommodityEvaluatesSum(String commodityId);

    List<EvaluateVO> getItemCommodityEvaluate(String itemId, Integer page, Integer limit);

    Integer getInfoSize(String itemId);
}
