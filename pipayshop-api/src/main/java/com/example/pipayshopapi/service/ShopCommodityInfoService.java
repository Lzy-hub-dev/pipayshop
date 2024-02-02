package com.example.pipayshopapi.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.ShopCommodityInfo;
import com.example.pipayshopapi.entity.ShopDetailInfoVO;
import com.example.pipayshopapi.entity.dto.ApplyShopCommodityDTO;
import com.example.pipayshopapi.entity.dto.ShopCommodityInfoDTO;
import com.example.pipayshopapi.entity.vo.ApplicationRecordVO;
import com.example.pipayshopapi.entity.vo.CommodityStatusPageVO;
import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.entity.vo.ShopCommodityInfo1VO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 实体店的商品表 服务类
 * </p>
 *
 * @author zxb
 * @since 2023-07-27
 */
public interface ShopCommodityInfoService extends IService<ShopCommodityInfo> {

    /**
     * 发布实体店商品
     * @param applyShopCommodityDTO
     * @return
     */
    boolean issueShopCommodity(ApplyShopCommodityDTO applyShopCommodityDTO);
    /**
     * 根据用户id查询 用户收藏的商品列表
     * @param userId
     * @return
     */
    PageDataVO getCollectList(Integer page,Integer limit,String userId);


    /**
     * 根据店铺id查找实体店商品的详情信息列表
     *
     * @param shopId
     * @return
     */
    List<ShopCommodityInfo1VO> selectShopInfoListByShopId(String shopId);

    /**
     * 根据店铺id查找实体店商品的上架和下架列表
     * @param commodityStatusPageVO
     * @return
     */
    PageDataVO selectStatusListByShopId(CommodityStatusPageVO commodityStatusPageVO);

    /**
     * 根据商品的id查找实体店商品的详情信息
     */
    ShopDetailInfoVO selectShopInfoByCommodityId(String commodityId);



    /**
     * 根据用户id查询用户浏览商品历史-实体店
     * @param userId
     * @return
     */
    PageDataVO historyList(Integer page,Integer limit,String userId);

    /**
     * 根据实体店id查询商品列表
     */
    List<ApplicationRecordVO> selectCommodityByUidAndStatus(String shopId);

    /**
     * 根据商品id，更改商品的状态
     *
     * @param commodityId
     * @return
     */
    boolean updateCommodityStatus(String commodityId,Integer status);

    /**
     * 根据商品id，上架变为下架
     *
     * @param commodityId
     * @return
     */
    boolean updateCommodityUp(String commodityId);


    /**
     * 根据商品id，下架变为审核中
     *
     * @param commodityId
     * @return
     */
    boolean updateCommodityCheck(String commodityId);

    Integer getResidueByCommodityId(String commodityId);

    String shopCommodityTopImageUp(MultipartFile multipartFile);

    List<String> shopCommodityImageUp(MultipartFile[] multipartFile);

    boolean updateCommodity(ShopCommodityInfoDTO shopCommodityInfoDTO);
}
