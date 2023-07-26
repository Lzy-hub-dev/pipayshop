package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.pipayshopapi.entity.BuyerData;
import com.example.pipayshopapi.entity.vo.BuyerDataVO;
import com.example.pipayshopapi.mapper.BuyerDataMapper;
import com.example.pipayshopapi.service.BuyerDataService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 买家的基本数据（多选） 服务实现类
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@Service
public class BuyerDataServiceImpl extends ServiceImpl<BuyerDataMapper, BuyerData> implements BuyerDataService {

    @Resource
    private BuyerDataMapper buyerDataMapper;

    /**
     * 根据Id查找买家的基本信息
     * */
    @Override
    public BuyerDataVO selectBuyerDataById(long id) {
        BuyerDataVO buyerDataVO = buyerDataMapper.selectBuyerDataById(id);
        return buyerDataVO;
    }

    /**
     * 根据Id更改买家的基本信息
     * */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateBuyerDataById(BuyerDataVO buyerDataVO, long id) {
        int result = buyerDataMapper.update(null, new UpdateWrapper<BuyerData>()
                                                                .eq("id", id)
                                                                .set("user_name", buyerDataVO.getUserName())
                                                                .set("address", buyerDataVO.getAddress())
                                                                .set("phone", buyerDataVO.getPhone()));
        return result > 0;
    }

    /**
     * 插入买家的基本信息
     * */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insectBuyerData(BuyerData buyerData) {
        int result = buyerDataMapper.insert(buyerData);
        return result > 0;
    }

    /**
     * 根据Id删除买家的基本信息
     * */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBuyerDataById(long id) {
        int result = buyerDataMapper.update(null, new UpdateWrapper<BuyerData>()
                                                                .eq("id", id)
                                                                .set("del_flag", 1));
        return result > 0;
    }

}
