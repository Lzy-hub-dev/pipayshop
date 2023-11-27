package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.pipayshopapi.entity.*;
import com.example.pipayshopapi.entity.dto.RebateDTO;
import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.mapper.*;
import com.example.pipayshopapi.service.RebateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class RebateServiceImpl implements RebateService {

    @Autowired
    ZoneLeaderConfigurationMapper zoneLeaderConfigurationMapper;
    @Autowired
    FirstZoneMapper firstZoneMapper;
    @Autowired
    TwoZoneSuperiorMapper twoZoneSuperiorMapper;
    @Autowired
    UserByZoneMapper userByZoneMapper;
    @Autowired
    AccountInfoMapper accountInfoMapper;
    @Autowired
    LogLeaderCommissionMapper logLeaderCommissionMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseVO<String> firstRebate(RebateDTO rebateDTO) {
        String userId = rebateDTO.getUserId();
        Long zoneId = rebateDTO.getZoneId();
        ZoneLeaderConfiguration zoneLeaderConfiguration = zoneLeaderConfigurationMapper.selectById(1);

        //查看返佣资格
        FirstZone firstZone = firstZoneMapper.selectOne(new QueryWrapper<FirstZone>()
                .eq("zone_id", zoneId));
        if (firstZone.getInvalid() == 1){
            return ResponseVO.getFalseResponseMsg("该团超过任务时长已失效，无法获取返佣");
        }
        if(firstZone.getLevelRebate() == 1){
            return ResponseVO.getFalseResponseMsg("该团已经返利，无法再次获取返利");
        }
        if (firstZone.getRebateQualification() == 1){
            return ResponseVO.getFalseResponseMsg("当前不具有返利资格，请稍后重试");
        }
        if(firstZone.getOpeningZoneTime().compareTo(firstZone.getEndTime()) > 0 || firstZone.getOpeningZoneTime().compareTo(firstZone.getEndTime()) == 0){
            firstZoneMapper.update(null, new UpdateWrapper<FirstZone>()
                    .eq("zone_id", zoneId)
                    .set("invalid", 1));
            return ResponseVO.getFalseResponseMsg("该团超过任务时长已失效，无法获取返佣");
        }
        //修改一级团状态为成功返利
        firstZoneMapper.update(null, new UpdateWrapper<FirstZone>()
                .eq("zone_id", zoneId)
                .set("level_rebate", 1));
        //为用户账户添加金额
        BigDecimal interestRate = zoneLeaderConfiguration.getThresholdSum()
                .multiply(BigDecimal.valueOf(zoneLeaderConfiguration.getFirstInterestRate()));
        AccountInfo accountInfo = accountInfoMapper.selectOne(new QueryWrapper<AccountInfo>()
                .eq("uid", userId)
                .eq("del_flag", 0));
        if (accountInfo == null) {
            throw new BusinessException("无该用户或者该用户已注销");
        }
        accountInfoMapper.update(null, new UpdateWrapper<AccountInfo>()
                .eq("uid", userId)
                .set("pi_balance", accountInfo.getPiBalance()
                        .add(zoneLeaderConfiguration.getThresholdSum()
                                .multiply(BigDecimal.valueOf(zoneLeaderConfiguration.getFirstInterestRate())))));
        //修改用户专区信息表
        Integer firstRebateNum = userByZoneMapper.selectOne(new QueryWrapper<UserByZone>()
                .eq("user_id", userId)).getFirstRebateNum();
        userByZoneMapper.update(null ,new UpdateWrapper<UserByZone>()
                .eq("user_id", userId)
                .set("first_rebate_num", firstRebateNum+1));
        //添加用户返佣记录表
        LogLeaderCommission logLeaderCommission = new LogLeaderCommission();
        logLeaderCommission.setZoneLeaderId(userId);
        logLeaderCommission.setZoneId(zoneId);
        logLeaderCommission.setCommissionAmount(interestRate);
        logLeaderCommissionMapper.insert(logLeaderCommission);
        return ResponseVO.getSuccessResponseVo("一级返利成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseVO<String> twoRebate(RebateDTO rebateDTO) {
        String userId = rebateDTO.getUserId();
        Long zoneId = rebateDTO.getZoneId();
        UserByZone userByZone = userByZoneMapper.selectOne(new QueryWrapper<UserByZone>()
                .eq("user_id", userId));
        ZoneLeaderConfiguration zoneLeaderConfiguration = zoneLeaderConfigurationMapper.selectById(1);

        //查看返佣资格
        TwoZoneSuperior twoZoneSuperior = twoZoneSuperiorMapper.selectOne(new QueryWrapper<TwoZoneSuperior>()
                .eq("zone_id", zoneId));
        if (twoZoneSuperior.getInvalid() == 1) {
            return ResponseVO.getFalseResponseMsg("该团超过任务时长已失效，无法获取返佣");
        }
        if (twoZoneSuperior.getLevelRebate() == 1) {
            return ResponseVO.getFalseResponseMsg("该团已经返佣，无法再次获取返佣");
        }
        if (twoZoneSuperior.getRebateQualification() == 0) {
            return ResponseVO.getFalseResponseMsg("当前不具有返佣资格，请10分钟后重试");
        }

        if (twoZoneSuperior.getIsTwoRebateTime() == 1) {
            //开启
            //判断团是否失效
            if (twoZoneSuperior.getOpeningZoneTime().compareTo(twoZoneSuperior.getEndTimeTwo()) > 0 ||
                    twoZoneSuperior.getOpeningZoneTime().compareTo(twoZoneSuperior.getEndTimeTwo()) == 0) {
                twoZoneSuperiorMapper.update(null, new UpdateWrapper<TwoZoneSuperior>()
                        .eq("zone_id", zoneId)
                        .set("invalid", 1));
                return ResponseVO.getFalseResponseMsg("该团超过任务时长已失效，无法获取返佣");
            }
            //修改二级团状态为成功返利
            twoZoneSuperiorMapper.update(null, new UpdateWrapper<TwoZoneSuperior>()
                    .eq("zone_id", zoneId)
                    .set("level_rebate", 1));
            //开团时间
            Date openingZoneTime = twoZoneSuperior.getOpeningZoneTime();
            //一阶段结束时间
            Date endTimeOne = twoZoneSuperior.getEndTimeOne();
            //二阶段结束时间
            Date endTimeTwo = twoZoneSuperior.getEndTimeTwo();

            if (openingZoneTime.compareTo(endTimeOne) < 0 || openingZoneTime.compareTo(endTimeOne) == 0) {
                //在一阶段时间内完成
                //修改二级团状态为成功返利
                twoZoneSuperiorMapper.update(null, new UpdateWrapper<TwoZoneSuperior>()
                        .eq("zone_id", zoneId)
                        .set("level_rebate", 1));
                //为用户账户添加金额
                BigDecimal interestRate = userByZone.getUserThreshold()
                        .multiply(BigDecimal.valueOf(zoneLeaderConfiguration.getFirstInterestRate()));
                AccountInfo accountInfo = accountInfoMapper.selectOne(new QueryWrapper<AccountInfo>()
                        .eq("uid", userId)
                        .eq("del_flag", 0));
                if (accountInfo == null) {
                    throw new BusinessException("无该用户或者该用户已注销");
                }
                accountInfoMapper.update(null, new UpdateWrapper<AccountInfo>()
                        .eq("uid", userId)
                        .set("pi_balance", accountInfo.getPiBalance()
                                .add(zoneLeaderConfiguration.getThresholdSum()
                                        .multiply(BigDecimal.valueOf(zoneLeaderConfiguration.getFirstInterestRate())))));
                //修改用户专区信息表
                Integer twoRebateNum = userByZoneMapper.selectOne(new QueryWrapper<UserByZone>()
                        .eq("user_id", userId)).getTwoRebateNum();
                userByZoneMapper.update(null ,new UpdateWrapper<UserByZone>()
                        .eq("user_id", userId)
                        .set("two_rebate_num", twoRebateNum+1));
                //添加用户返佣记录表
                LogLeaderCommission logLeaderCommission = new LogLeaderCommission();
                logLeaderCommission.setZoneLeaderId(userId);
                logLeaderCommission.setZoneId(zoneId);
                logLeaderCommission.setCommissionAmount(interestRate);
                logLeaderCommissionMapper.insert(logLeaderCommission);
                return ResponseVO.getSuccessResponseMsg(null, "二级返佣成功");
            } else{
                //在二阶段时间完成
                //修改二级团状态为成功返利
                twoZoneSuperiorMapper.update(null, new UpdateWrapper<TwoZoneSuperior>()
                        .eq("zone_id", zoneId)
                        .set("level_rebate", 1));
                //为用户账户添加金额
                BigDecimal interestRate = userByZone.getUserThreshold()
                        .multiply(BigDecimal.valueOf(zoneLeaderConfiguration.getTwoInterestRate()));
                AccountInfo accountInfo = accountInfoMapper.selectOne(new QueryWrapper<AccountInfo>()
                        .eq("uid", userId)
                        .eq("del_flag", 0));
                if (accountInfo == null) {
                    throw new BusinessException("无该用户或者该用户已注销");
                }
                accountInfoMapper.update(null, new UpdateWrapper<AccountInfo>()
                        .eq("uid", userId)
                        .set("pi_balance", accountInfo.getPiBalance()
                                .add(zoneLeaderConfiguration.getThresholdSum()
                                        .multiply(BigDecimal.valueOf(zoneLeaderConfiguration.getFirstInterestRate())))));
                //修改用户专区信息表
                Integer twoRebateNum = userByZoneMapper.selectOne(new QueryWrapper<UserByZone>()
                        .eq("user_id", userId)).getTwoRebateNum();
                userByZoneMapper.update(null ,new UpdateWrapper<UserByZone>()
                        .eq("user_id", userId)
                        .set("two_rebate_num", twoRebateNum+1));
                //添加用户返佣记录表
                LogLeaderCommission logLeaderCommission = new LogLeaderCommission();
                logLeaderCommission.setZoneLeaderId(userId);
                logLeaderCommission.setZoneId(zoneId);
                logLeaderCommission.setCommissionAmount(interestRate);
                logLeaderCommissionMapper.insert(logLeaderCommission);
                return ResponseVO.getSuccessResponseMsg(null, "二级返佣成功");
            }

        }
        else{
            //不开启
            //判断团是否失效
            if(twoZoneSuperior.getOpeningZoneTime().compareTo(twoZoneSuperior.getEndTimeOne()) > 0 ||
                    twoZoneSuperior.getOpeningZoneTime().compareTo(twoZoneSuperior.getEndTimeOne()) == 0){
                twoZoneSuperiorMapper.update(null, new UpdateWrapper<TwoZoneSuperior>()
                        .eq("zone_id", zoneId)
                        .set("invalid", 1));
                return ResponseVO.getFalseResponseMsg("该团超过任务时长已失效，无法获取返佣");
            }
            //修改二级团状态为成功返利
            twoZoneSuperiorMapper.update(null, new UpdateWrapper<TwoZoneSuperior>()
                    .eq("zone_id", zoneId)
                    .set("level_rebate", 1));

            //开团时间
            Date openingZoneTime = twoZoneSuperior.getOpeningZoneTime();
            //结束时间
            Date endTimeOne = twoZoneSuperior.getEndTimeOne();
            if(openingZoneTime.compareTo(endTimeOne) < 0 || openingZoneTime.compareTo(endTimeOne) == 0){
                //完成开始返佣
                //修改二级团状态为成功返利
                twoZoneSuperiorMapper.update(null, new UpdateWrapper<TwoZoneSuperior>()
                        .eq("zone_id", zoneId)
                        .set("level_rebate", 1));
                //为用户账户添加金额
                BigDecimal interestRate = userByZone.getUserThreshold()
                        .multiply(BigDecimal.valueOf(zoneLeaderConfiguration.getFirstInterestRate()));
                AccountInfo accountInfo = accountInfoMapper.selectOne(new QueryWrapper<AccountInfo>()
                        .eq("uid", userId)
                        .eq("del_flag", 0));
                if (accountInfo == null) {
                    throw new BusinessException("无该用户或者该用户已注销");
                }
                accountInfoMapper.update(null, new UpdateWrapper<AccountInfo>()
                        .eq("uid", userId)
                        .set("pi_balance", accountInfo.getPiBalance()
                                .add(zoneLeaderConfiguration.getThresholdSum()
                                        .multiply(BigDecimal.valueOf(zoneLeaderConfiguration.getFirstInterestRate())))));
                //修改用户专区信息表
                Integer twoRebateNum = userByZoneMapper.selectOne(new QueryWrapper<UserByZone>()
                        .eq("user_id", userId)).getTwoRebateNum();
                userByZoneMapper.update(null ,new UpdateWrapper<UserByZone>()
                        .eq("user_id", userId)
                        .set("two_rebate_num", twoRebateNum+1));
                //添加用户返佣记录表
                LogLeaderCommission logLeaderCommission = new LogLeaderCommission();
                logLeaderCommission.setZoneLeaderId(userId);
                logLeaderCommission.setZoneId(zoneId);
                logLeaderCommission.setCommissionAmount(interestRate);
                logLeaderCommissionMapper.insert(logLeaderCommission);
            }
        }
        return ResponseVO.getSuccessResponseMsg(null, "二级返佣成功");
    }
}
