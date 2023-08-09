package com.example.pipayshopapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.BUserInfo;
import com.example.pipayshopapi.entity.vo.BUserLoginVO;

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
}
