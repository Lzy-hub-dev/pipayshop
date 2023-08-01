package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.entity.AccountInfo;
import com.example.pipayshopapi.entity.vo.AccountInfoVO;
import com.example.pipayshopapi.mapper.AccountInfoMapper;
import com.example.pipayshopapi.service.AccountInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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

}
