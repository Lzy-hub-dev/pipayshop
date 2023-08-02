package com.example.pipayshopapi.mapper;

import com.example.pipayshopapi.entity.ShopCommodityEvaluate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pipayshopapi.entity.vo.ShopCommodityEvaluateVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zxb
 * @since 2023-07-27
 */
@Mapper

public interface ShopCommodityEvaluateMapper extends BaseMapper<ShopCommodityEvaluate> {
    List<ShopCommodityEvaluateVO> commodityEvaluateList(@Param("commodityId") String commodityId,
                                                        @Param("pageNum") Integer pageNum,
                                                        @Param("pageSize") Integer pageSize);
}
