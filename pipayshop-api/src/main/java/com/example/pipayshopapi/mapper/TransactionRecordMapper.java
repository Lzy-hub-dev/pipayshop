package com.example.pipayshopapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pipayshopapi.entity.TransactionRecord;
import com.example.pipayshopapi.entity.vo.RecordTransactionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zxb
 * @since 2023-08-08
 */
@Mapper
public interface TransactionRecordMapper extends BaseMapper<TransactionRecord> {

    List<RecordTransactionVO> getRecordTransaction(@Param("shopId") String shopId,
                                                   @Param("page") int page,
                                                   @Param("limit") int limit);

    int getRecordTransactionCount(@Param("shopId") String shopId);
}
