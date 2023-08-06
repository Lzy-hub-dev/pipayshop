package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.pipayshopapi.entity.AccountInfo;
import com.example.pipayshopapi.entity.ItemInfo;
import com.example.pipayshopapi.entity.RechargePermissionsOrder;
import com.example.pipayshopapi.entity.vo.RechargePermissionsOrderVO;
import com.example.pipayshopapi.mapper.AccountInfoMapper;
import com.example.pipayshopapi.mapper.ItemInfoMapper;
import com.example.pipayshopapi.mapper.RechargePermissionsOrderMapper;
import com.example.pipayshopapi.service.AccountInfoService;
import com.example.pipayshopapi.service.ItemInfoService;
import com.example.pipayshopapi.service.RechargePermissionsOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.util.StringUtil;
import org.omg.SendingContext.RunTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

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
    private ItemInfoMapper itemInfoMapper;

    @Resource
    private AccountInfoMapper accountInfoMapper;

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
