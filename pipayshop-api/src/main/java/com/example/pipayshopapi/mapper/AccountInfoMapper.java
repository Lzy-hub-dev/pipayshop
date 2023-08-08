package com.example.pipayshopapi.mapper;

import com.example.pipayshopapi.entity.AccountInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pipayshopapi.entity.vo.AccountInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户账户表 Mapper 接口
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@Mapper
public interface AccountInfoMapper extends BaseMapper<AccountInfo> {


    /**
     * 根据用户Id查找用户账户表的积分余额和pi币余额
     * @param uid
     * @return
     */
    AccountInfoVO selectAccountInfo(@Param("uid") String uid);

    int createAccount(String userId);
}
