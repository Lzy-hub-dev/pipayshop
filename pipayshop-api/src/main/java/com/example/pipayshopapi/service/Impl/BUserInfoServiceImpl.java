package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.entity.BUserInfo;
import com.example.pipayshopapi.entity.vo.BUserLoginVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.mapper.BUserInfoMapper;
import com.example.pipayshopapi.service.BUserInfoService;
import com.example.pipayshopapi.util.TokenUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zxb
 * @since 2023-08-09
 */
@Service
public class BUserInfoServiceImpl extends ServiceImpl<BUserInfoMapper, BUserInfo> implements BUserInfoService {

    @Resource
    BUserInfoMapper bUserInfoMapper;

    @Override
    public String login(BUserLoginVO bUserLoginVO) {
        String piName = bUserLoginVO.getPiName();
        String passWord = bUserLoginVO.getPassWord();
        // 校验操作
        BUserInfo bUserInfo = bUserInfoMapper.selectOne(new QueryWrapper<BUserInfo>()
                .eq("pi_name", piName)
                .eq("passWord", passWord)
                .eq("status", 0));
        if (bUserInfo == null) {
            throw new BusinessException("登录失败");
        }
        // 登录成功
        // 记录登录时间
        bUserInfoMapper.update(null, new UpdateWrapper<BUserInfo>()
                .eq("pi_name", piName)
                .set("last_login_time", new Date()));
        // 封装token
        return TokenUtil.getToken(piName);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updatePassWord(BUserLoginVO bUserLoginVO) {
        int update = bUserInfoMapper.update(null, new UpdateWrapper<BUserInfo>()
                .eq("pi_name", bUserLoginVO.getPiName())
                .eq("status", 0)
                .set("password", bUserLoginVO.getPassWord())
                .set("update_time", new Date()));
        return update > 0;
    }
}
