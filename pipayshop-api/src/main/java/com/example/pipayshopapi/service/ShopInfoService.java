package com.example.pipayshopapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.ShopInfo;
import com.example.pipayshopapi.entity.dto.ApplyShopDTO;
import com.example.pipayshopapi.entity.vo.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 实体店的信息 服务类
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
public interface ShopInfoService extends IService<ShopInfo> {


    String getShopCodeByShopId(String shopId);

    /**
     * 根据二级分类-获取所有实体店列表
     */
    PageDataVO getShopInfoListByCondition(Integer limit, Integer pages, String categoryId,Boolean score, String regionId);

    /**
     * 根据实体店id查询实体店信息
     */
    ShopInfoVO getShopInfoById(String shopId);

    /**
     * 根据用户id查询用户名下多少间实体店
     */
    Integer getShopNumber(String uid);

    /**
     * 根据用户id查询实体店列表(我的)
     */
    PageDataVO getShopList(UidPageVO uidPageVO);

    /**
     * 根据实体店id查询实体店信息(我的)
     */
    ShopInfoVO1 getShopInfoVO(String shopId);

    /**
     * 根据实体店id删除实体店
     * @param shopId
     * @return
     */
    Boolean deleteShopInfoById(String shopId);

    /**
     * 根据实体店id修改实体店
     * @param shopInfo
     * @return
     */
    Boolean updateShopInfoById(ShopInfo shopInfo);



    /**
     * 申请实体店
     */
    boolean applyShop(ApplyShopDTO applyShopDTO );

    /**
     * 根据一级分类-获取所有实体店列表
     */
    PageDataVO getSecShopInfoListByCondition(Integer limit, Integer pages, String categoryId, String areaDivide);

    boolean isVipShop(String shopId);

    List<String> getShopIdListByUid(String uid);

    Boolean upVipByShopIdList(String shopIds);

    /**
     * 校验实体店是否可以上传的商品
     */
    Integer updateShopCommodity(String shopId);

    CheckVO checkId(String qrcode);

    Boolean setShopScore();

    /**
     * 根据条件查询酒店信息
     * @param livePageVO
     * @return
     */
    PageDataVO getHotelInfoByCondition(LivePageVO livePageVO);

    /**
     * 实体店展示图上传
     */
    String shopTopImageUp(MultipartFile multipartFile);

    /**
     * 实体店轮播图上传
     */
    String shopImageUp(MultipartFile multipartFile);

    List<CountryMinVO> getSecondDistrictList(String countryCode);

    List<CountryMinVO> getThirdDistrictList(String countrySecondId);
}
