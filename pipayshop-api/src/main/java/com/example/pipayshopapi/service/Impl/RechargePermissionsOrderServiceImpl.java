package com.example.pipayshopapi.service.Impl;

import com.example.pipayshopapi.entity.RechargePermissionsOrder;
import com.example.pipayshopapi.mapper.RechargePermissionsOrderMapper;
import com.example.pipayshopapi.service.RechargePermissionsOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
    @Resource
    private RechargePermissionsOrderMapper rechargePermissionsOrderMapper;

    @Override
    @Transactional
    public Boolean updateUploadBalanceInfo(RechargePermissionsOrderVO rechargePermissionsOrderVO) {
        String orderId = StringUtil.generateShortId();
        RechargePermissionsOrder order = new RechargePermissionsOrder();
        order.setOrderId(orderId);
        order.setCreateTime(new Date());
        order.setUid(rechargePermissionsOrderVO.getUid());
        order.setPermissionsCount(rechargePermissionsOrderVO.getPermissionsCount());
        order.setTransactionAmount(rechargePermissionsOrderVO.getTransactionAmount());
        Integer result = rechargePermissionsOrderMapper.insert(order);
        if(result < 1){
            throw new RuntimeException();
        }
        result = accountInfoMapper.update(null,new UpdateWrapper<AccountInfo>()
                .eq("uid",rechargePermissionsOrderVO.getUid())
                .eq("del_flag",0)
                .setSql("point_balance = point_balance -" + rechargePermissionsOrderVO.getTransactionAmount())
                .set("update_time",new Date())
        );
        if(result < 1){
            throw new RuntimeException();
        }
        result = itemInfoMapper.update(null,new UpdateWrapper<ItemInfo>()
                .eq("uid",rechargePermissionsOrderVO.getUid())
                .setSql("upload_balance = upload_balance +" + rechargePermissionsOrderVO.getPermissionsCount()));
        if(result < 1){
            throw new RuntimeException();
        }
        return true;
    }
}
