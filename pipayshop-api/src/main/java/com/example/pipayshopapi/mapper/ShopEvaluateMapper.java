package com.example.pipayshopapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pipayshopapi.entity.ShopEvaluate;
import com.example.pipayshopapi.entity.vo.ShopEvaluateVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 网店评价 Mapper 接口
 * </p>
 *
 * @author zxb
 * @since 2023-07-29
 */
@Mapper
public interface ShopEvaluateMapper extends BaseMapper<ShopEvaluate> {
    /**
     * 获取实体店评价数
     * @param shopId
     * @return
     */
    Integer selectShopEvaluateCount(@Param("shopId")String shopId);

    List<ShopEvaluateVO> selectPageByShopId(@Param("page") Integer page, @Param("limit")Integer limit, @Param("shopId")String shopId);

    int selectPageByShopIdCount(@Param("shopId")String shopId);
}
