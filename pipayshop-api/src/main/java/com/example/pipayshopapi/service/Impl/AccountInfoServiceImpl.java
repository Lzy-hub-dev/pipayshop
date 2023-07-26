package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.pipayshopapi.entity.AccountInfo;
import com.example.pipayshopapi.entity.vo.AccountInfoVO;
import com.example.pipayshopapi.entity.vo.FrontAccountInfoVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.mapper.AccountInfoMapper;
import com.example.pipayshopapi.service.AccountInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * <p>
 * 用户账户表 服务实现类
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@Service
public class AccountInfoServiceImpl extends ServiceImpl<AccountInfoMapper, AccountInfo> implements AccountInfoService {

    @Resource
    private AccountInfoMapper accountInfoMapper;

    /**
     * 根据用户Id查找用户账户表的积分余额和pi币余额
     * */
    @Override
    public AccountInfoVO selectAccountById(String uid) {
        return accountInfoMapper.selectAccountInfo(uid);
    }

    /**
     * 根据用户Id增加用户账户表的积分余额和pi币余额
     * */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addAccountById(FrontAccountInfoVO frontAccountInfoVO, String uid) {
        AccountInfoVO accountInfoVO1 = selectAccountById(uid);
        BigDecimal piBalance = accountInfoVO1.getPiBalance().add(frontAccountInfoVO.getPiBalance());
        BigDecimal pointBalance = accountInfoVO1.getPointBalance().add(frontAccountInfoVO.getPointBalance());

        int result = accountInfoMapper.update(null, new UpdateWrapper<AccountInfo>()
                .eq("uid", uid)
                .set("point_balance", pointBalance)
                .set("pi_balance", piBalance));
        return result>0;
    }

    /**
     * 根据用户Id减少用户账户表的积分余额
     * */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean subAccountById(FrontAccountInfoVO frontAccountInfoVO, String uid) {
        AccountInfoVO accountInfoVO = selectAccountById(uid);

        if (frontAccountInfoVO.getPointBalance().compareTo(accountInfoVO.getPointBalance())==1){
            throw new BusinessException("没有足够的积分");
        }

        BigDecimal subtract = accountInfoVO.getPointBalance().subtract(frontAccountInfoVO.getPointBalance());

        int result = accountInfoMapper.update(null, new UpdateWrapper<AccountInfo>()
                .eq("uid", uid)
                .set("point_balance", subtract));
        return result>0;
    }

}
