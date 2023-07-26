package com.example.pipayshopapi.service;

import com.example.pipayshopapi.entity.AccountInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.vo.AccountInfoVO;
import com.example.pipayshopapi.entity.vo.FrontAccountInfoVO;
import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.entity.vo.PageVO;

import java.util.List;

/**
 * <p>
 * 用户账户表 服务类
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
public interface AccountInfoService extends IService<AccountInfo> {

    /**
     * 根据用户Id查找用户账户表的积分余额和pi币余额
     * @param uid
     * @return
     * */
    AccountInfoVO selectAccountById(String uid);

    /**
     * 根据用户Id增加用户账户表的积分余额和pi币余额
     * @param frontAccountInfoVO
     * @param uid
     * @return
     * */
    boolean addAccountById(FrontAccountInfoVO frontAccountInfoVO, String uid);

    /**
     * 根据用户Id减少用户账户表的积分余额
     * @param frontAccountInfoVO
     * @param uid
     * @return
     * */
    boolean subAccountById(FrontAccountInfoVO frontAccountInfoVO, String uid);

}
