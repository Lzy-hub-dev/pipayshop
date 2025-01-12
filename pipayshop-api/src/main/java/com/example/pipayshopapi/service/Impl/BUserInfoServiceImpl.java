package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.entity.AccountInfo;
import com.example.pipayshopapi.entity.BUserInfo;
import com.example.pipayshopapi.entity.BUserWithdrawalRecordInfo;
import com.example.pipayshopapi.entity.vo.AccountInfoVO;
import com.example.pipayshopapi.entity.vo.BUserLoginVO;
import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.mapper.AccountInfoMapper;
import com.example.pipayshopapi.mapper.BUserInfoMapper;
import com.example.pipayshopapi.mapper.BUserWithdrawalRecordInfoMapper;
import com.example.pipayshopapi.service.BUserInfoService;
import com.example.pipayshopapi.util.TokenUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
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

    @Resource
    AccountInfoMapper accountInfoMapper;

    @Resource
    BUserWithdrawalRecordInfoMapper bUserWithdrawalRecordInfoMapper;

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
    public boolean updatePassWord(BUserLoginVO bUserLoginVO) {
        int update = bUserInfoMapper.update(null, new UpdateWrapper<BUserInfo>()
                .eq("pi_name", bUserLoginVO.getPiName())
                .eq("status", 0)
                .set("password", bUserLoginVO.getPassWord())
                .set("update_time", new Date()));
        return update > 0;
    }

    /**
     * 获取账户积分
     */
    @Override
    public BigDecimal selectAccountBalance(String uid) {

        return accountInfoMapper.selectAccountBalance(uid);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean userWithDraw(String uid, BigDecimal balance) {
        // 获取商户积分余额
        AccountInfoVO accountInfoVO = accountInfoMapper.selectAccountInfo(uid);
        BigDecimal pointBalance = accountInfoVO.getPointBalance();
        int result = pointBalance.compareTo(balance);
        // 余额充足
        if ( result >= 0){
            // 提现
            int update = accountInfoMapper.update(null, new UpdateWrapper<AccountInfo>()
                    .eq("uid", uid)
                    .set("point_balance", pointBalance.subtract(balance)));
            // 提现记录
            update  += bUserWithdrawalRecordInfoMapper.insert(new BUserWithdrawalRecordInfo(uid, balance));
            return update > 1;
        }
        // 余额不足
        return false;
    }

    @Override
    public PageDataVO selectUserWithdrawalRecord(String uid, Integer page, Integer limit) {

        // 设置分页参数
        Page<BUserWithdrawalRecordInfo> pageRecord = new Page<>(page,limit);

        // 查询分页数据封装到page中
        bUserWithdrawalRecordInfoMapper.selectPage(pageRecord, new QueryWrapper<BUserWithdrawalRecordInfo>()
                                                                    .eq("uid",uid)
                                                                    .orderByDesc("create_time")
        );
        // 封装数据
        return new PageDataVO((int)pageRecord.getTotal(), pageRecord.getRecords());

    }
}
