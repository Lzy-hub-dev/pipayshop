package com.example.pipayshopapi.service;

import com.example.pipayshopapi.entity.BuyerData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.vo.BuyerDataVO;

import java.util.List;

/**
 * <p>
 * 买家的基本数据（多选） 服务类
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
public interface BuyerDataService extends IService<BuyerData> {

    /**
     * 根据用户Id查找用户的所有收货地址
     * @param userId
     * @return
     * */
    List<BuyerData> selectAllAddress(String userId);


    /**
     * 根据收货Id更改买家的收货地址
     * @param buyerData
     * @return
     * */
    boolean updateBuyerDataById(BuyerData buyerData);

    /**
     * 插入买家的收货地址
     * @param buyerData
     * @return
     * */
    boolean insectBuyerData(BuyerData buyerData);

    /**
     * 根据id删除买家的收货地址
     * @param buyerId
     * @return
     * */
    boolean deleteBuyerDataById(String buyerId);

}
