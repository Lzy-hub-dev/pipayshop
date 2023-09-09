package com.example.pipayshopapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pipayshopapi.entity.ShopInfo;
import com.example.pipayshopapi.entity.vo.IndexShopInfoVO;
import com.example.pipayshopapi.entity.vo.ShopInfoVO1;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 实体店的信息 Mapper 接口
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@Mapper
public interface ShopInfoMapper extends BaseMapper<ShopInfo> {

    /**
     * 根据用户id查询实体店列表(我的）
     */
    List<ShopInfoVO1> getShopList(@Param("page") Integer page, @Param("limit") Integer limit,
                                  @Param("uid") String uid);

    /**
     * 根据用户id查询实体店数量
     */
    Integer getShopNumber(@Param("uid") String uid);
    /**
     * 根据实体店id查询实体店信息条数
     */
    Integer getShopInfoVOCount(@Param("shopId")String shopId);

    /**
     * 根据实体店id查询实体店信息
     */
    ShopInfoVO1 getShopInfoVO(@Param("shopId")String shopId);
    /**
     * 首页获取商家信息列表总条数
     */
    Integer getIndexShopInfoVOCount(@Param("categoryId") String categoryId, @Param("areaDivide") String areaDivide);

    /**
     * 首页获取商家信息列表
     */
    List<IndexShopInfoVO> getIndexShopInfoVO(@Param("categoryId") String categoryId
            ,@Param("page") Integer page
            ,@Param("limit") Integer limit
            ,@Param("score") Boolean score
            ,@Param("areaDivide") String areaDivide);

    Integer getAllIndexShopInfoVO(@Param("categoryId") String categoryId, @Param("areaDivide") String areaDivide);


    List<String> getShopIdListByUid(@Param("uid") String uid);

    List<IndexShopInfoVO> getIndexShopInfoVOById(@Param("categoryId") String categoryId,@Param("page") Integer page,@Param("limit") Integer limit, @Param("areaDivide") String areaDivide);

    /**
     * 实体店可以上传的总数
     */
    Integer updateAllNumber(@Param("shopId") String shopId);


    /**
     * 根据服务id 查询 对应实体店可上传服务余额
     */
    int selectUploadCommodityBalanceByCommodityId(@Param("commodityId")String commodityId);

    /**
     * 根据服务id   -1  商品可上架数量余额
     * @param commodityId
     * @return
     */
    int reduceUploadBalanceByCommodityId(@Param("commodityId")String commodityId);
    /**
     * 根据服务id   +1  商品可上架数量余额
     */
    int addUploadBalanceByCommodityId(@Param("commodityId")String commodityId);

    Integer setItemScore();

    List<IndexShopInfoVO> getHotelInfoByCondition(@Param("shopName")String shopName,
                                              @Param("limit") Integer limit,
                                              @Param("page")Integer page,
                                              @Param("checkInTime")Date checkInTime,
                                              @Param("departureTime")Date departureTime,
                                              @Param("adult")Integer adult,
                                              @Param("children")Integer children,
                                                @Param("areaDivide") String areaDivide);

    Integer getHotelInfoNum(@Param("shopName")String shopName,
                            @Param("checkInTime") Date checkInTime,
                            @Param("departureTime")Date departureTime,
                            @Param("adult")Integer adult,
                            @Param("children")Integer children,
                            @Param("areaDivide") String areaDivide);

    String getShopCodeByShopId(@Param("shopId") String shopId);

    List<IndexShopInfoVO> getShopInfoMinListByCondition(@Param("categoryId") String categoryId, @Param("page") int page, @Param("limit") Integer limit, @Param("areaDivide") String areaDivide);
}
