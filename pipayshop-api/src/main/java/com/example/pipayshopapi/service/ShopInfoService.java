package com.example.pipayshopapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.ShopInfo;
import com.example.pipayshopapi.entity.dto.ShopDTO;
import com.example.pipayshopapi.entity.vo.PageDataVO;

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
    ShopInfo getShopInfoById(String shopId);


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
     * 新增实体店
     * @param shopInfo
     * @return
     */
    Boolean addShopInfo(ShopInfo shopInfo);
}
