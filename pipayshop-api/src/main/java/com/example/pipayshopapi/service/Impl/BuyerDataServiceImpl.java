package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.entity.BuyerData;
import com.example.pipayshopapi.mapper.BuyerDataMapper;
import com.example.pipayshopapi.service.BuyerDataService;
import com.example.pipayshopapi.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

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
     * 根据用户Id查找用户的所有收货地址
     * */
    @Override
    public List<BuyerData> selectAllAddress(String userId) {
        return buyerDataMapper.selectList(new QueryWrapper<BuyerData>()
                .eq("user_id", userId)
                .eq("del_flag", 0));
    }

    /**
     * 根据收货Id更改买家的收货地址
     * */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateBuyerDataById(BuyerData buyerData) {
        int result = buyerDataMapper.update(null, new UpdateWrapper<BuyerData>()
                                                                .eq("buyer_data_id", buyerData.getBuyerDataId())
                                                                .set("user_name", buyerData.getUserName())
                                                                .set("address", buyerData.getAddress())
                                                                .set("phone", buyerData.getPhone()));
        return result > 0;
    }

    /**
     * 插入买家的收货地址
     * */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insectBuyerData(BuyerData buyerData) {
        buyerData.setBuyerDataId(StringUtil.generateShortId());
        int result = buyerDataMapper.insert(buyerData);
        return result > 0;
    }

    /**
     * 根据id删除买家的收货地址
     * */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBuyerDataById(String buyerId) {
        int result = buyerDataMapper.update(null, new UpdateWrapper<BuyerData>()
                                                                .eq("buyer_data_id", buyerId)
                                                                .set("del_flag", 1));
        return result > 0;
    }

    @Override
    public boolean updateDefaultAddress(String buyerDataId) {
        String userId = buyerDataMapper.getUserId(buyerDataId);
        //先把全部值为0
        buyerDataMapper.update(null,new UpdateWrapper<BuyerData>()
                .eq("user_id",userId)
                .set("is_default","0"));

        return buyerDataMapper.update(null,new UpdateWrapper<BuyerData>()
                .eq("buyer_data_id",buyerDataId)
                .set("is_default","1")) > 0;
    }

}
