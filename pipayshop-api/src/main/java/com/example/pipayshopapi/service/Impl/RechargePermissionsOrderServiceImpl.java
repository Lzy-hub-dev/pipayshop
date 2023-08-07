package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.entity.AccountInfo;
import com.example.pipayshopapi.entity.ItemInfo;
import com.example.pipayshopapi.entity.RechargePermissionsOrder;
import com.example.pipayshopapi.entity.UserInfo;
import com.example.pipayshopapi.entity.vo.RechargePermissionsOrderVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.mapper.AccountInfoMapper;
import com.example.pipayshopapi.mapper.ItemInfoMapper;
import com.example.pipayshopapi.mapper.RechargePermissionsOrderMapper;
import com.example.pipayshopapi.mapper.UserInfoMapper;
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
    UserInfoMapper userInfoMapper;

    @Resource
    ItemInfoMapper itemInfoMapper;

    private final String MESSAGE = "该订单已经支付，请勿重复下单！";


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addShopSum(String orderId) {
        RechargePermissionsOrder data = rechargeComplete(orderId);
        // 增加实体店绑定数
        int update1 = userInfoMapper.update(null, new UpdateWrapper<UserInfo>()
                .eq("uid", data.getUid())
                .eq("status", 0)
                .setSql("shop_balance = shop_balance +" + data.getPermissionsCount()));
        if (update1 < 1){throw new RuntimeException();}
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateUploadBalanceInfo(String orderId) {
        RechargePermissionsOrder data = rechargeComplete(orderId);
        // 更改可上架商品数值
        int update1 = itemInfoMapper.update(null,new UpdateWrapper<ItemInfo>()
                .eq("uid",data.getUid())
                .setSql("upload_balance = upload_balance +" + data.getPermissionsCount()));
        if(update1 < 1){
            throw new RuntimeException();
        }
        return true;
    }

    /**
     * 生成未支付订单
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String getUploadBalanceNoPayOrder(RechargePermissionsOrderVO orderVO) {
        String orderId = StringUtil.generateShortId();
        RechargePermissionsOrder order = new RechargePermissionsOrder(null, orderId, orderVO.getUid(), orderVO.getPermissionsCount(),
                orderVO.getTransactionAmount(), null, null, null, orderVO.getChargeType());
        // 生成订单
        int insert = rechargePermissionsOrderMapper.insert(order);
        if (insert < 1){throw new RuntimeException();}
        return orderId;
    }

    /**
     * 两个下单操作的共用类
     */
    private RechargePermissionsOrder rechargeComplete(String orderId){
        // 校验订单id是否已经存在，保证接口的幂等性，避免重复下单
        RechargePermissionsOrder data = rechargePermissionsOrderMapper.selectOne(new QueryWrapper<RechargePermissionsOrder>()
                .eq("order_id", orderId));
        if (data == null || data.getStatus() == 1){throw new BusinessException(MESSAGE);}
        // 扣减余额积分
        int update = accountInfoMapper.update(null, new UpdateWrapper<AccountInfo>()
                .eq("uid", data.getUid())
                .eq("del_flag", 0)
                .setSql("point_balance = point_balance -" + data.getTransactionAmount()));
        if (update < 1){throw new RuntimeException();}
        // 修改未支付订单的状态
        int update2 = rechargePermissionsOrderMapper.update(null, new UpdateWrapper<RechargePermissionsOrder>()
                .eq("order_id", data.getOrderId())
                .set("status", 1));
        if (update2 < 1){throw new RuntimeException();}
        return data;
    }
}
