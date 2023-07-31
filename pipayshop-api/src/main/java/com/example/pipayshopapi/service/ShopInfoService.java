package com.example.pipayshopapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.ShopInfo;
import com.example.pipayshopapi.entity.dto.ApplyShopDTO;
import com.example.pipayshopapi.entity.dto.ShopDTO;
import com.example.pipayshopapi.entity.vo.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

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
     * 获取实体店列表
     * @return
     */
    PageDataVO getShopInfoList(ShopDTO shopDTO);

    /**
     * 根据条件筛选后获取实体店列表
     * @param limit
     * @param pages
     * @param categoryId
     * @param state
     * @return
     */
    PageDataVO getShopInfoListByCondition(Integer limit,Integer pages,String categoryId,Integer state);

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
     * @param file
     * @return
     */
    boolean applyShop(ApplyShopDTO applyShopDTO , MultipartFile[] file);

}
