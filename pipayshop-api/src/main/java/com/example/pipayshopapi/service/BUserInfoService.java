package com.example.pipayshopapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.BUserInfo;
import com.example.pipayshopapi.entity.vo.BUserLoginVO;
import com.example.pipayshopapi.entity.vo.PageDataVO;

import java.math.BigDecimal;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zxb
 * @since 2023-08-09
 */
public interface BUserInfoService extends IService<BUserInfo> {

    /**
     * 校验登录
     */
    String login(BUserLoginVO bUserLoginVO);

    /**
     * 修改密码
     */
    boolean updatePassWord(BUserLoginVO bUserLoginVO);

    /**
     * 获取用户账号积分
     */
    BigDecimal selectAccountBalance(String uid);

    /**
     * 商户提现
     */
    boolean userWithDraw(String uid,BigDecimal balance);

    /**
     * 商户提现记录列表
     */
    PageDataVO selectUserWithdrawalRecord(String uid, Integer page, Integer limit);
}
