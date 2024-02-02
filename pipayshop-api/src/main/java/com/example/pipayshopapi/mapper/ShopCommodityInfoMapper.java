package com.example.pipayshopapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pipayshopapi.entity.ShopCommodityInfo;
import com.example.pipayshopapi.entity.ShopDetailInfoVO;
import com.example.pipayshopapi.entity.dto.ShopCommodityInfoDTO;
import com.example.pipayshopapi.entity.vo.ApplicationRecordVO;
import com.example.pipayshopapi.entity.vo.ShopCommodityInfo1VO;
import com.example.pipayshopapi.entity.vo.ShopCommodityInfoVO;
import com.example.pipayshopapi.entity.vo.ShopCommodityListVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
    List<ShopCommodityInfoVO> selectCollectProductByUserId(@Param("page") Integer page, @Param("limit") Integer limit,@Param("userId") String userId);
    Integer selectAllCollectProductByUserId(@Param("userId") String userId);


    /**
     * 根据用户id查询用户商品浏览历史
     */
    List<ShopCommodityListVO> selectHistoryProductByUserId(@Param("page") Integer page, @Param("limit") Integer limit,@Param("userId") String userId);
    Integer selectAllHistoryProductByUserId(@Param("userId") String userId);

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
     * @return
     */
    List<ShopCommodityInfo1VO>selectCommodityByShopId(@Param("shopId") String shopId);

    /**
     * 根据店铺id查询，实体店的商品列表总数
     *
     * @param shopId
     * @return
     */
    Integer selectAllCommodityByShopId(@Param("shopId")String shopId);

    ShopDetailInfoVO selectShopInfoByCommodityId(@Param("commodityId")String commodityId);

    Integer selectCommdityListByShopId(@Param("shopId")String shopId);

    List<ApplicationRecordVO> selectCommdityListByShopIdPage(@Param("shopId")String shopId);

    @Select("select shop_id from shop_commodity_info where commodity_id = #{commodityId}")
    String selectShopIdByCommodityId(@Param("commodityId")String commodityId);

    /**
     * 根据购买数量、商品id
     * @param num
     * @param commodityId
     * @return
     */
    int reduceStock(@Param("num")Integer num,@Param("commodityId")String commodityId);


    /**
     * 根据购买数量、商品id =》商品库存复原
     * @return
     */
    int addStock(@Param("num")Integer num,@Param("commodityId")String commodityId);

    int updateCommodity(ShopCommodityInfoDTO shopCommodityInfoDTO);

}
