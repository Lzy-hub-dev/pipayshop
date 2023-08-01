package com.example.pipayshopapi.service;

import com.example.pipayshopapi.entity.ItemInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.vo.ItemInfoVO;
import com.example.pipayshopapi.entity.vo.UserInfoVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zxb
 * @since 2023-07-27
 */
public interface ItemInfoService extends IService<ItemInfo> {
    /**
     * 根据商品id获取网店信息
     * @param commodityId
     * @return
     * */
    ItemInfoVO getItemInfo(String commodityId);

    /**
     * 根据用户id获取网店信息
     * @param userId
     * @return
     */
    List<ItemInfoVO> getItemInfoByUid(String userId);

    /**
     * 根据用户id获取网店数量
     * @param userId
     * @return
     */
    Integer getItemCountByUserId(String userId);

    /**
     * 根据网店id获取网店地址
     * @param itemId
     * @return
     */

    String getItemAddressById(String itemId);
}
