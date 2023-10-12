package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.entity.AccountInfo;
import com.example.pipayshopapi.entity.TransactionRecord;
import com.example.pipayshopapi.entity.vo.AccountInfoVO;
import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.entity.vo.RecordTransactionVO;
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
import java.util.List;


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
        Claims claims = TokenUtil.getDataFromToken(token);
        String shopId = claims.get("shopId", String.class);
        String userId = claims.get("userId", String.class);
        BigDecimal transactionAmount = BigDecimal.valueOf(Double.parseDouble(claims.get("transactionAmount", String.class)));
        // 获取用户账户
        AccountInfoVO userAccountInfo = accountInfoMapper.selectAccountInfo(userId);
        // 判断可用积分是否足够支付积分
        if (userAccountInfo.getAvailableBalance().compareTo(transactionAmount) < 0) {
            throw new BusinessException("积分不足");
        }
        // 扣减自身的积分余额
        int update =accountInfoMapper.updatePointBalanceByUid(transactionAmount,userId);
        if (update < 1) {
            throw new BusinessException("扣减自身的积分余额失败");
        }
        // 增加店铺对应的店主的账户数据
        int update1 = accountInfoMapper.updatePointBalanceByShopId(shopId, transactionAmount);
        if (update1 < 1) {
            throw new BusinessException("增加店铺对应的店主的账户数据失败");
        }
        int insert = transactionRecordMapper.insert(new TransactionRecord(null, StringUtil.generateShortId()
                , shopId, userId, transactionAmount, null, null));
        if (insert < 1) {
            throw new BusinessException("记录交易日志失败");
        }
        return true;
    }


    @Override
    public PageDataVO getRecordTransaction(String shopId, int page, int limit) {
        List<RecordTransactionVO> list = transactionRecordMapper
                .getRecordTransaction(shopId,(page - 1) * limit,limit);
        int count = transactionRecordMapper.getRecordTransactionCount(shopId);
        return new PageDataVO(count, list);
    }
}
