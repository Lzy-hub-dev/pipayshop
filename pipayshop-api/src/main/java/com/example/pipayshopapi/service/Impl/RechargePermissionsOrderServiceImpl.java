package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.entity.AccountInfo;
import com.example.pipayshopapi.entity.RechargePermissionsOrder;
import com.example.pipayshopapi.entity.ShopInfo;
import com.example.pipayshopapi.entity.vo.RechargeVO;
import com.example.pipayshopapi.mapper.AccountInfoMapper;
import com.example.pipayshopapi.mapper.RechargePermissionsOrderMapper;
import com.example.pipayshopapi.mapper.ShopInfoMapper;
import com.example.pipayshopapi.service.RechargePermissionsOrderService;
import com.example.pipayshopapi.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zxb
 * @since 2023-08-06
 */
@Service
public class RechargePermissionsOrderServiceImpl extends ServiceImpl<RechargePermissionsOrderMapper, RechargePermissionsOrder> implements RechargePermissionsOrderService {

    @Resource
    RechargePermissionsOrderMapper rechargePermissionsOrderMapper;

    @Resource
    AccountInfoMapper accountInfoMapper;

    @Resource
    ShopInfoMapper shopInfoMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addShopSum(RechargeVO rechargeVO) {
        // 扣减余额积分
        int update = accountInfoMapper.update(null, new UpdateWrapper<AccountInfo>()
                .eq("uid", rechargeVO.getUid())
                .eq("del_flag", 0)
                .setSql("point_balance = point_balance -" + rechargeVO.getTransactionAmount()));
        if (update < 0){
            throw new RuntimeException();
        }
        // 增加实体店绑定数
        int update1 = shopInfoMapper.update(null, new UpdateWrapper<ShopInfo>()
                .eq("shop_id", rechargeVO.getShopId())
                .eq("status", 0)
                .setSql("upload_commodity_balance = upload_commodity_balance +" + rechargeVO.getPermissionsCount()));
        if (update1 < 0){
            throw new RuntimeException();
        }
        // 记录订单
        RechargePermissionsOrder rechargePermissionsOrder = new RechargePermissionsOrder(StringUtil.generateShortId()
                ,rechargeVO.getUid(), rechargeVO.getPermissionsCount(),rechargeVO.getTransactionAmount());

        int insert = rechargePermissionsOrderMapper.insert(rechargePermissionsOrder);
        if (insert < 0){
            throw new RuntimeException();
        }
        return true;
    }
}
