package com.example.pipayshopapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pipayshopapi.entity.ShopCommodityInfo;
import com.example.pipayshopapi.entity.ShopDetailInfoVO;
import com.example.pipayshopapi.entity.ShopInfo;
import com.example.pipayshopapi.entity.vo.ShopCommodityInfo1VO;
import com.example.pipayshopapi.entity.vo.ShopCommodityInfoVO;
import com.example.pipayshopapi.entity.vo.ShopCommodityVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 实体店的商品表 Mapper 接口
 * </p>
 *
 * @author zxb
 * @since 2023-07-27
 */
@Mapper

public interface ShopCommodityInfoMapper extends BaseMapper<ShopCommodityInfo> {
    /**
     * 根据用户id查询 用户收藏的商品列表
     *
     * @param userId
     * @return
     */
    List<ShopCommodityInfoVO> selectCollectProductByUserId(String userId);


    /**
     * 根据用户id查询用户商品浏览历史
     */
    List<ShopCommodityVO> selectHistoryProductByUserId(String userId);

    /**
     * 根据用户id查询，商品状态查询审核通过和未审核列表
     *
     * @param page
     * @return
     */
    List<ShopCommodityInfoVO> selectCommodityByUidAndStatus (@Param("page") Integer page, @Param("limit") Integer limit,
                                                             @Param("uid") String uid, @Param("status") Integer status);

    /**
     * 根据用户id查询，商品状态查询审核通过和未审核列表的总数
     *
     * @param uid
     * @return
     */
    Integer selectAllCommodity(@Param("uid") String uid, @Param("status") Integer status);

    /**
     * 根据店铺id查询，实体店的商品列表
     *
     * @param page
     * @return
     */
    List<ShopCommodityInfo1VO>selectCommodityByShopId(@Param("page") Integer page, @Param("limit") Integer limit,@Param("shopId") String shopId);

    /**
     * 根据店铺id查询，实体店的商品列表总数
     *
     * @param shopId
     * @return
     */
    Integer selectAllCommodityByShopId(@Param("shopId")String shopId);

    ShopDetailInfoVO selectShopInfoByCommodityId(@Param("commodityId")String commodityId);
}
