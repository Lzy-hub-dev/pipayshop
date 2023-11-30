package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.entity.*;
import com.example.pipayshopapi.entity.dto.UserByZoneInvitationCodeDTO;
import com.example.pipayshopapi.entity.vo.*;
import com.example.pipayshopapi.mapper.*;
import com.example.pipayshopapi.service.UserInfoService;
import com.example.pipayshopapi.service.ZoneService;
import com.example.pipayshopapi.util.InviteCodeGenerator;
import com.example.pipayshopapi.util.SnowflakeIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ZoneServiceImpl  extends ServiceImpl<FirstZoneMapper, FirstZone> implements ZoneService {

    @Autowired
    FirstZoneMapper firstZoneMapper;
    @Autowired
    UserByZoneMapper userByZoneMapper;
    @Autowired
    TwoZoneSuperiorMapper twoZoneSuperiorMapper;
    @Autowired
    FirstZoneUserMapper firstZoneUserMapper;
    @Autowired
    TwoZoneMapper twoZoneMapper;
    @Autowired
    TwoZoneUserMapper twoZoneUserMapper;

    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    UserInfoService userInfoService;

    @Autowired
    ZoneLeaderConfigurationMapper zoneLeaderConfigurationMapper;
    //雪花算法
    SnowflakeIdGenerator snowflakeIdGenerator = new SnowflakeIdGenerator(1,1);

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseVO<String> createZone(String userId) {
        ZoneLeaderConfiguration zoneLeaderConfiguration = zoneLeaderConfigurationMapper.selectById(1);

        UserByZone userByZone = userByZoneMapper.selectOne(new QueryWrapper<UserByZone>()
                .eq("user_id", userId));
        //检验是否具有开团资格
        if (userByZone == null) {
            return ResponseVO.getFalseResponseMsg("团创建失败，无该用户的专区信息");
        }
        if(userByZone.getOpeningGroupQualification() == 1) {
            //添加一级团
            FirstZone firstZone = new FirstZone();
            firstZone.setUserId(userId);
            firstZone.setZoneId(snowflakeIdGenerator.nextId());
            firstZone.setInvitationCode(InviteCodeGenerator.generateInviteCode());

            Calendar calendar = Calendar.getInstance();
            Date currentDate = new Date();
            calendar.setTime(currentDate);
            calendar.add(Calendar.DAY_OF_MONTH, zoneLeaderConfiguration.getTaskTime());
            Date modifiedDate = calendar.getTime();
            firstZone.setEndTime(modifiedDate);

            firstZoneMapper.insert(firstZone);
            //判断该一级团是否要被纳入上级总级团
            //判断是否有邀请人
            String superiorUserId = userByZone.getSuperiorUserId();
                    if (superiorUserId != null && !superiorUserId.isEmpty()) {
                //判断是否为第一次开团
                if (userByZone.getZoneNum() == 0) {
                    //判断二级团是否失效
                    FirstZone firstZone1 = firstZoneMapper.selectOne(new QueryWrapper<FirstZone>()
                            .eq("user_id", superiorUserId)
                            .lt("zone_user_num", zoneLeaderConfiguration.getSubordinateMaxNum()));
                    TwoZoneSuperior twoZoneSuperior = twoZoneSuperiorMapper.selectOne(new QueryWrapper<TwoZoneSuperior>()
                            .eq("user_id", superiorUserId)
                            .eq("first_zone_id ", firstZone1.getZoneId()));
                    if (twoZoneSuperior != null) {
                        Integer invalid = twoZoneSuperior.getInvalid();
                        //纳入上级的二级团
                        if (invalid == 0) {
                            TwoZone twoZone = new TwoZone(twoZoneSuperior.getZoneId(), firstZone.getZoneId());
                            twoZoneMapper.insert(twoZone);}}}}
            //添加二级总团
            TwoZoneSuperior twoZoneSuperior2 = new TwoZoneSuperior();
            twoZoneSuperior2.setUserId(userId);
            twoZoneSuperior2.setZoneId(snowflakeIdGenerator.nextId());
            twoZoneSuperior2.setFirstZoneId(firstZone.getZoneId());
            calendar.setTime(currentDate);
            if (zoneLeaderConfiguration.getTwoRebateTime() == 1){
                calendar.add(Calendar.DAY_OF_MONTH, zoneLeaderConfiguration.getTwoRebateTimeOne());
                twoZoneSuperior2.setEndTimeOne(calendar.getTime());
                calendar.add(Calendar.DAY_OF_MONTH, zoneLeaderConfiguration.getTwoRebateTimeTwo());
                twoZoneSuperior2.setEndTimeTwo(calendar.getTime());
                twoZoneSuperior2.setIsTwoRebateTime(1);
            }else{
                calendar.add(Calendar.DAY_OF_MONTH, zoneLeaderConfiguration.getTwoRebateTimeOne());
                Date modifiedDate2 = calendar.getTime();
                twoZoneSuperior2.setEndTimeOne(modifiedDate2);
                twoZoneSuperior2.setIsTwoRebateTime(0);
            }
            twoZoneSuperiorMapper.insert(twoZoneSuperior2);
            //修改用户拥有团数量
            userByZoneMapper.update(null, new UpdateWrapper<UserByZone>()
                    .eq("user_id", userId)
                    .set("zone_num", userByZone.getZoneNum()+1)
                    .set("opening_group_qualification", 0)//取消再开团资格
                    .set("new_zone_id", firstZone.getZoneId())
                    .set("new_two_zone_id", twoZoneSuperiorMapper.selectOne(new QueryWrapper<TwoZoneSuperior>()
                            .eq("user_id", userId)
                            .eq("first_zone_id", firstZone.getZoneId())).getZoneId()));
            return ResponseVO.getSuccessResponseVo(firstZone.getZoneId().toString());
        }else
            return ResponseVO.getFalseResponseMsg("团创建失败，不具有创建资格");
    }

    @Override
    public List<FirstZone> selectZones(String userId) {
        return firstZoneMapper.selectList(new QueryWrapper<FirstZone>()
                .eq("user_id", userId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseVO<String> joinZone(UserByZoneInvitationCodeDTO userByZoneInvitationCodeDTO) {
        String userId = userByZoneInvitationCodeDTO.getUserId();
        String invitationCode = userByZoneInvitationCodeDTO.getInvitationCode();

        UserByZone userByZone = userByZoneMapper.selectOne(new QueryWrapper<UserByZone>()
                .eq("user_id", userId));
        //判断用户是否具备入团资格（团长没有入团资格）
        if(userByZone != null) {
            if (userByZone.getIsGroupLeader() == 1) {
                return ResponseVO.getFalseResponseMsg("团长身份无法入团");
            }
            //判断是否被人邀请过
            if (userByZone.getSuperiorUserId() != null) {
                if (!userByZone.getSuperiorUserId().isEmpty())
                    return ResponseVO.getFalseResponseMsg("请求失败，无法加入该团，已加入过其他团");
            }
            //判断团是否失效
            FirstZone firstZone = firstZoneMapper.selectOne(new QueryWrapper<FirstZone>()
                    .eq("invitation_code", invitationCode));
            Integer invalid = firstZone.getInvalid();
            if (invalid == 1) {
                return ResponseVO.getFalseResponseMsg("请求失败，该团已经失效哦，无法加入");
            }
            firstZoneUserMapper.selectOne(new QueryWrapper<FirstZoneUser>()
                    .eq("zone_id", firstZone.getZoneId())
                    .eq("user_id", userId));
            if (firstZoneUserMapper.selectOne(new QueryWrapper<FirstZoneUser>()
                    .eq("zone_id", firstZone.getZoneId())
                    .eq("user_id", userId)) != null){
                return ResponseVO.getFalseResponseMsg("请求失败，已经在该团中，无法加入");
            }
            //判断团是否满员
            Integer maxNum = zoneLeaderConfigurationMapper.selectById(1).getSubordinateMaxNum();
            if (firstZone.getZoneUserNum() > maxNum) {
                return ResponseVO.getFalseResponseMsg("请求失败，该团已经满人，无法加入");
            }
            //通过邀请码获取到对应的团
            Long zoneId = firstZone.getZoneId();
            //将用户绑定到当前团下
            firstZoneUserMapper.insert(new FirstZoneUser(zoneId, userId));
            //查询是否关联了二级团
            //判断邀请码的所属用户
            TwoZoneSuperior twoZoneSuperior = twoZoneSuperiorMapper.selectOne(new QueryWrapper<TwoZoneSuperior>()
                    .eq("first_zone_id", firstZone.getZoneId()));
            TwoZone twoZone = twoZoneMapper.selectOne(new QueryWrapper<TwoZone>()
                    .eq("superior_zone_id", twoZoneSuperior.getZoneId()));
            if (twoZone != null){
                //存在
                TwoZoneUser twoZoneUser = new TwoZoneUser();
                twoZoneUser.setZoneId(twoZone.getZoneId());
                twoZoneUser.setUserId(userId);
                twoZoneUserMapper.insert(twoZoneUser);
            }
            //修改用户信息
            userByZoneMapper.update(null, new UpdateWrapper<UserByZone>()
                    .eq("user_id", userId)
                    .set("join_zone", 1)
                    .set("superior_user_id", firstZone.getUserId()));
            return ResponseVO.getSuccessResponseMsg(null,"入团成功");
        }
        return ResponseVO.getFalseResponseMsg("请求失败，无该用户专区信息");
    }

    @Override
    public List<TwoZoneVO> selectTwoZones(String userId, Long firstZoneId) {
        List<TwoZoneVO> twoZoneVOS = new ArrayList<>();
        TwoZoneVO twoZoneVO = new TwoZoneVO();
        List<ZoneUserInfoVO> zoneUserInfoVOList = new ArrayList<>();
        ZoneUserInfoVO zoneUserInfoVO = new ZoneUserInfoVO();
        TwoZoneSuperior twoZoneSuperior = twoZoneSuperiorMapper.selectOne(new QueryWrapper<TwoZoneSuperior>()
                .eq("user_id", userId)
                .eq("first_zone_id", firstZoneId));
        List<TwoZone> twoZones = twoZoneMapper.selectList(new QueryWrapper<TwoZone>()
                .eq("superior_zone_id", twoZoneSuperior.getZoneId()));
        for (TwoZone twoZone : twoZones) {
            FirstZone firstZone = firstZoneMapper.selectOne(new QueryWrapper<FirstZone>()
                    .eq("zone_id", twoZone.getZoneId()));

            twoZoneVO.setUserId(firstZone.getUserId());
            List<FirstZoneUser> users = firstZoneUserMapper.selectList(new QueryWrapper<FirstZoneUser>()
                    .eq("zone_id", firstZone.getZoneId()));
            for (FirstZoneUser user : users) {
                UserInfoVO userInfoVO = userInfoService.selectUserInfoByUid(user.getUserId());
                zoneUserInfoVO.setUserId(userInfoVO.getUid());
                zoneUserInfoVO.setUserImage(userInfoVO.getUserImage());
                zoneUserInfoVO.setUserName(userInfoVO.getUserName());
                zoneUserInfoVO.setZoneConsumptionSum(userByZoneMapper.selectOne(new QueryWrapper<UserByZone>()
                        .eq("user_id", user.getUserId())).getZoneConsumptionSum());
                zoneUserInfoVOList.add(zoneUserInfoVO);
            }
            twoZoneVO.setZoneUserInfoVOList(zoneUserInfoVOList);
            twoZoneVOS.add(twoZoneVO);
        }
        return twoZoneVOS;
    }

    @Override
    public FirstZoneVO selectZone(String userId, Long firstZoneId) {
        List<ZoneUserInfoVO> ZoneUserInfoVOList = new ArrayList<>();
        FirstZoneVO firstZoneVO = new FirstZoneVO();

        FirstZone firstZone = firstZoneMapper.selectOne(new QueryWrapper<FirstZone>()
                .eq("user_id", userId)
                .eq("zone_id", firstZoneId));
        if (firstZone != null) {
            List<FirstZoneUser> firstZoneUsers = firstZoneUserMapper.selectList(new QueryWrapper<FirstZoneUser>()
                    .eq("zone_id", firstZoneId));
            for (FirstZoneUser firstZoneUser : firstZoneUsers) {
                ZoneUserInfoVO zoneUserInfoVO = new ZoneUserInfoVO();
                UserInfoVO userInfo = userInfoService.selectUserInfoByUid(firstZoneUser.getUserId());
                zoneUserInfoVO.setUserId(userInfo.getUid());
                zoneUserInfoVO.setUserImage(userInfo.getUserImage());
                zoneUserInfoVO.setUserName(userInfo.getUserName());
                UserByZone userByZone = userByZoneMapper.selectOne(new QueryWrapper<UserByZone>()
                        .eq("user_id", firstZoneUser.getUserId()));
                zoneUserInfoVO.setZoneConsumptionSum(userByZone.getZoneConsumptionSum());
                zoneUserInfoVO.setZoneId(userByZone.getNewZoneId());
                ZoneUserInfoVOList.add(zoneUserInfoVO);
            }
            firstZoneVO.setZoneUserNum(firstZone.getZoneUserNum());
            firstZoneVO.setInvitationCode(firstZone.getInvitationCode());
            firstZoneVO.setRebateQualification(firstZone.getRebateQualification());
            firstZoneVO.setInvalid(firstZone.getInvalid());
            firstZoneVO.setLevelRebate(firstZone.getLevelRebate());
            firstZoneVO.setZoneUserInfoVOList(ZoneUserInfoVOList);
        }
        return firstZoneVO;
    }

    @Override
    public ZoneStatusVO selectTwoZone(Long zoneId) {
        TwoZoneSuperior twoZoneSuperior = twoZoneSuperiorMapper.selectOne(new QueryWrapper<TwoZoneSuperior>()
                .eq("zone_id", zoneId));
        return new ZoneStatusVO(twoZoneSuperior.getRebateQualification(), twoZoneSuperior.getInvalid());
    }
}
