package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.entity.AccountInfo;
import com.example.pipayshopapi.entity.ItemInfo;
import com.example.pipayshopapi.entity.RechargePermissionsOrder;
import com.example.pipayshopapi.entity.UserInfo;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.mapper.AccountInfoMapper;
import com.example.pipayshopapi.mapper.ItemInfoMapper;
import com.example.pipayshopapi.mapper.RechargePermissionsOrderMapper;
import com.example.pipayshopapi.mapper.UserInfoMapper;
import com.example.pipayshopapi.service.RechargePermissionsOrderService;
import com.example.pipayshopapi.util.StringUtil;
import com.example.pipayshopapi.util.TokenUtil;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

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
    RechargePermissionsOrderService rechargePermissionsOrderService;

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
    public boolean addShopSum(String orderId) {
        RechargePermissionsOrder data = rechargePermissionsOrderService.rechargeComplete(orderId);
        // 增加实体店绑定数
        int update1 = userInfoMapper.update(null, new UpdateWrapper<UserInfo>()
                .eq("uid", data.getUid())
                .eq("status", 0)
                .setSql("shop_balance = shop_balance +" + data.getPermissionsCount()));
        if (update1 < 1){throw new RuntimeException();}
        return true;
    }

    @Override
    public boolean updateUploadBalanceInfo(String orderId) {
        RechargePermissionsOrder data = rechargePermissionsOrderService.rechargeComplete(orderId);
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
    public String getUploadBalanceNoPayOrder(String token) {
        String orderId = StringUtil.generateShortId();
        Claims dataFromToken = TokenUtil.getDataFromToken(token);
        String uid = dataFromToken.get("uid", String.class);
        Integer permissionsCount = dataFromToken.get("permissionsCount", Integer.class);
        BigDecimal transactionAmount = BigDecimal.valueOf(Double.parseDouble(dataFromToken.get("transactionAmount", String.class)));
        Integer chargeType = dataFromToken.get("chargeType", Integer.class);
        RechargePermissionsOrder order = new RechargePermissionsOrder(null, orderId, uid, permissionsCount,
                transactionAmount, null, null, null, chargeType);
        // 生成订单
        int insert = rechargePermissionsOrderMapper.insert(order);
        if (insert < 1){throw new RuntimeException();}
        return orderId;
    }

    /**
     * 两个下单操作的共用类
     */
    @Transactional(rollbackFor = Exception.class)
    public RechargePermissionsOrder rechargeComplete(String orderId){
        // 校验订单id是否已经存在，保证接口的幂等性，避免重复下单
        RechargePermissionsOrder data = rechargePermissionsOrderMapper.selectOne(new QueryWrapper<RechargePermissionsOrder>()
                .eq("order_id", orderId));
        if (data == null || data.getStatus() == 1){throw new BusinessException(MESSAGE);}
        // 扣减余额积分
        int update = accountInfoMapper.updatePointBalanceByUid(data.getTransactionAmount(),data.getUid());
        if (update < 1){throw new RuntimeException();}
        // 修改未支付订单的状态
        int update2 = rechargePermissionsOrderMapper.update(null, new UpdateWrapper<RechargePermissionsOrder>()
                .eq("order_id", data.getOrderId())
                .set("status", 1));
        if (update2 < 1){throw new RuntimeException();}
        return data;
    }
}
