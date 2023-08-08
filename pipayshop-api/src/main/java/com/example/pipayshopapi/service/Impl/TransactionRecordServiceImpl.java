package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.entity.TransactionRecord;
import com.example.pipayshopapi.mapper.TransactionRecordMapper;
import com.example.pipayshopapi.service.TransactionRecordService;
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
 * @since 2023-08-08
 */
@Service
public class TransactionRecordServiceImpl extends ServiceImpl<TransactionRecordMapper, TransactionRecord> implements TransactionRecordService {

    @Resource
    TransactionRecordMapper transactionRecordMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean recordTransaction(String token) {
        // 解密JWT获取数据
        Claims claims = TokenUtil.getUserIdFromToken(token);
        String shopId = claims.get("shopId", String.class);
        String userId = claims.get("user_id", String.class);
        BigDecimal transactionAmount = claims.get("transactionAmount", BigDecimal.class);
        int insert = transactionRecordMapper.insert(new TransactionRecord(null, StringUtil.generateShortId()
                , shopId, userId, transactionAmount, null, null));
        return insert > 0;
    }
}
