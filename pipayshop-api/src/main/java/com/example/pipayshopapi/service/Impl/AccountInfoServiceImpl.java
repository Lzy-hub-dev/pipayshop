package com.example.pipayshopapi.service.Impl;

import com.example.pipayshopapi.entity.AccountInfo;
import com.example.pipayshopapi.mapper.AccountInfoMapper;
import com.example.pipayshopapi.service.AccountInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
