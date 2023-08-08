package com.example.pipayshopapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.ShopInfo;
import com.example.pipayshopapi.entity.dto.ApplyShopDTO;
import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.entity.vo.ShopInfoVO;
import com.example.pipayshopapi.entity.vo.ShopInfoVO1;
import com.example.pipayshopapi.entity.vo.UidPageVO;

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


    /**
     * 根据二级分类-获取所有实体店列表
     *
     * @param limit
     * @param pages
     * @param categoryId
     * @return
     */
    PageDataVO getShopInfoListByCondition(Integer limit, Integer pages, String categoryId,Boolean score);

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
     * @param applyShopDTO
     * @return
     */
    boolean applyShop(ApplyShopDTO applyShopDTO );

    /**
     * 根据一级分类-获取所有实体店列表
     * @param limit
     * @param pages
     * @param categoryId
     * @return
     */
    PageDataVO getSecShopInfoListByCondition(Integer limit, Integer pages, String categoryId);

    boolean isVipShop(String shopId);

    List<String> getShopIdListByUid(String uid);

    Boolean upVipByShopIdList(String shopIds);

    /**
     * 校验实体店是否可以上传的商品
     */
    Integer updateShopCommodity(String shopId);
}
