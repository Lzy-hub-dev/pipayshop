package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.entity.AccountInfo;
import com.example.pipayshopapi.entity.TransactionRecord;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.mapper.AccountInfoMapper;
import com.example.pipayshopapi.mapper.TransactionRecordMapper;
import com.example.pipayshopapi.service.TransactionRecordService;
import com.example.pipayshopapi.util.StringUtil;
import com.example.pipayshopapi.util.TokenUtil;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;


/**
 * @author wzx
 */
@Service
public class TransactionRecordServiceImpl extends ServiceImpl<TransactionRecordMapper, TransactionRecord> implements TransactionRecordService {

    @Resource
    TransactionRecordMapper transactionRecordMapper;

    @Resource
    AccountInfoMapper accountInfoMapper;



    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean recordTransaction(String token) {
        // 解密JWT获取数据，记录交易日志
        Claims claims = TokenUtil.getUserIdFromToken(token);
        String shopId = claims.get("shopId", String.class);
        String userId = claims.get("user_id", String.class);
        BigDecimal transactionAmount = claims.get("transactionAmount", BigDecimal.class);
        int insert = transactionRecordMapper.insert(new TransactionRecord(null, StringUtil.generateShortId()
                , shopId, userId, transactionAmount, null, null));
        if (insert < 1) {
            throw new BusinessException("记录交易日志失败");
        }
        // 扣减自身的积分余额
        int update = accountInfoMapper.update(null, new UpdateWrapper<AccountInfo>()
                .eq("uid", userId)
                .setSql("point_balance = point_balance -" + transactionAmount)
                .set("update_time", new Date()));
        if (update < 1) {
            throw new BusinessException("扣减自身的积分余额失败");
        }
        // 增加店铺对应的店主的账户数据
        int update1 = accountInfoMapper.updatePointBalanceByShopId(shopId, transactionAmount);
        if (update1 < 1) {
            throw new BusinessException("增加店铺对应的店主的账户数据失败");
        }
        return true;
    }
}
