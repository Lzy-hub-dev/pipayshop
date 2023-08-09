package com.example.pipayshopapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.TransactionRecord;
import com.example.pipayshopapi.entity.vo.PageDataVO;

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

    PageDataVO getRecordTransaction(String shopId, int page, int limit          );
}
