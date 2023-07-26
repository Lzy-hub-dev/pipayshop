package com.example.pipayshopapi.service;

import com.example.pipayshopapi.entity.AccountInfo;
import com.example.pipayshopapi.entity.BuyerData;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.vo.BuyerDataVO;
import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.entity.vo.PageVO;

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
     * 根据Id查找买家的基本信息
     * @param id
     * @return
     * */
    BuyerDataVO selectBuyerDataById(long id);


    /**
     * 根据Id更改买家的基本信息
     * @param buyerDataVO
     * @param id
     * @return
     * */
    boolean updateBuyerDataById(BuyerDataVO buyerDataVO,long id);

    /**
     * 插入买家的基本信息
     * @param buyerData
     * @return
     * */
    boolean insectBuyerData(BuyerData buyerData);

    /**
     * 根据id删除买家的基本信息
     * @param id
     * @return
     * */
    boolean deleteBuyerDataById(long id);

}
