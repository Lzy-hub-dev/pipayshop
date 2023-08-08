package com.example.pipayshopapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.TransactionRecord;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zxb
 * @since 2023-08-08
 */
public interface TransactionRecordService extends IService<TransactionRecord> {

    /**
     * 记录交易，数据采用JWT 加密传输
     */
    boolean recordTransaction(String token);
}
