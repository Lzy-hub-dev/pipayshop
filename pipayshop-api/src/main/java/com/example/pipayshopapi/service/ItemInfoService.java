package com.example.pipayshopapi.service;

import com.example.pipayshopapi.entity.ItemInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.vo.ItemInfoVO;
import com.example.pipayshopapi.entity.vo.UserInfoVO;

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
}
