package com.example.pipayshopapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pipayshopapi.entity.ShopInfo;
import com.example.pipayshopapi.entity.vo.IndexShopInfoVO;
import com.example.pipayshopapi.entity.vo.ShopInfoVO1;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
    Integer getIndexShopInfoVOCount(@Param("categoryId") String categoryId);

    /**
     * 首页获取商家信息列表
     */
    List<IndexShopInfoVO> getIndexShopInfoVO(@Param("categoryId") String categoryId,@Param("page") Integer page,@Param("limit") Integer limit,@Param("score") Boolean score);

    Integer getAllIndexShopInfoVO(@Param("categoryId") String categoryId);


    List<String> getShopIdListByUid(@Param("uid") String uid);

    List<IndexShopInfoVO> getIndexShopInfoVOById(@Param("categoryId") String categoryId,@Param("page") Integer page,@Param("limit") Integer limit);

    /**
     * 实体店已上传的商品数量
     */
    Integer updateNumber(@Param("shopId") String shopId);

    /**
     * 实体店可以上传的总数
     */
    Integer updateAllNumber(@Param("shopId") String shopId);

}
