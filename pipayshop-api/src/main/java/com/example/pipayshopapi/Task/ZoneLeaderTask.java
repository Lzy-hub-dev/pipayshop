package com.example.pipayshopapi.Task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.pipayshopapi.entity.FirstZone;
import com.example.pipayshopapi.entity.TwoZoneSuperior;
import com.example.pipayshopapi.entity.UserByZone;
import com.example.pipayshopapi.mapper.FirstZoneMapper;
import com.example.pipayshopapi.mapper.TwoZoneSuperiorMapper;
import com.example.pipayshopapi.mapper.UserByZoneMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Component

public class ZoneLeaderTask {

    @Autowired
    FirstZoneMapper firstZoneMapper;

    @Autowired
    UserByZoneMapper userByZoneMapper;

    @Autowired
    TwoZoneSuperiorMapper twoZoneSuperiorMapper;

    @Transactional
    @Scheduled(cron = "0 0/1 * * * ?")
    public void inspectZoneLeader(){
        System.out.println("开始检查开团资格");
        int sum = 0;
        List<UserByZone> userByZones = userByZoneMapper.selectList(null);
        for (UserByZone userByZone : userByZones) {
            if (userByZone.getIsGroupLeader() == 1) { //团长才需要进行开团资格检查
                List<FirstZone> firstZones = firstZoneMapper.selectList(new QueryWrapper<FirstZone>()
                        .eq("user_id", userByZone.getUserId()));
                if (firstZones.size() != 0) {
                    for (FirstZone firstZone : firstZones) {
                        if (firstZone.getZoneUserNum() < 10) {
                            sum++;
                        }
                    }
                    if (sum == 0) {
                        userByZoneMapper.update(null, new UpdateWrapper<UserByZone>()
                                .eq("user_id", userByZone.getUserId())
                                .set("opening_group_qualification", 1));
                        System.out.println("获得再开团资格");
                    } else
                        System.out.println("名下拥有未完成团，无再开团资格");
                } else {
                    userByZoneMapper.update(null, new UpdateWrapper<UserByZone>()
                            .eq("user_id", userByZone.getUserId())
                            .set("opening_group_qualification", 1));
                    System.out.println("用户" + userByZone.getUserId() + "未创建过团，获得再开团资格");
                }
            }
        }
    }

    @Transactional
    //@Scheduled(cron = "* * * * * ?")
    @Scheduled(cron = "0 0/5 * * * ?")
    public void inspectRebate() {
        System.out.println("开始检查返佣资格");
        List<UserByZone> userByZones = userByZoneMapper.selectList(null);
        for (UserByZone userByZone : userByZones) {
            List<FirstZone> firstZones = firstZoneMapper.selectList(new QueryWrapper<FirstZone>()
                    .eq("user_id", userByZone.getUserId()));
            for (FirstZone firstZone : firstZones) {
                if (firstZone.getZoneUserNum() == 10) {
                    System.out.println("团已满人，获得返佣资格");
                    firstZoneMapper.update(null, new UpdateWrapper<FirstZone>()
                            .eq("zone_id", firstZone.getZoneId()).
                            set("rebate_qualification", 1));
                }
            }
        }
    }

    @Transactional
    //@Scheduled(cron = "* * * * * ?")
    @Scheduled(cron = "0 0/1 * * * ?")
    public void inspectZoneInvalidity(){
        System.out.println("开始检查团是否失效");
        Date date = new Date();
        List<UserByZone> userByZones = userByZoneMapper.selectList(null);
        for (UserByZone userByZone : userByZones) {
            List<FirstZone> firstZones = firstZoneMapper.selectList(new QueryWrapper<FirstZone>()
                    .eq("user_id", userByZone.getUserId()));
            for (FirstZone firstZone : firstZones) {
                if (date.compareTo(firstZone.getEndTime()) >=  0){
                    //一级团团失效
                    firstZoneMapper.update(null, new UpdateWrapper<FirstZone>()
                            .eq("zone_id", firstZone.getZoneId())
                            .set("invalid", 1));
                }
            }
            List<TwoZoneSuperior> twoZoneSuperiors = twoZoneSuperiorMapper.selectList(new QueryWrapper<TwoZoneSuperior>()
                    .eq("user_id", userByZone.getUserId()));
            for (TwoZoneSuperior twoZoneSuperior : twoZoneSuperiors) {
                if (twoZoneSuperior.getIsTwoRebateTime() == 1){
                    //开启
                    if(date.compareTo(twoZoneSuperior.getEndTimeTwo()) >= 0){
                        twoZoneSuperiorMapper.update(null, new UpdateWrapper<TwoZoneSuperior>()
                                .eq("user_id", userByZone.getUserId())
                                .set("invalid", 1));
                    }
                }else {
                    //不开启
                    if(date.compareTo(twoZoneSuperior.getEndTimeOne()) >= 0){
                        twoZoneSuperiorMapper.update(null, new UpdateWrapper<TwoZoneSuperior>()
                                .eq("user_id", userByZone.getUserId())
                                .set("invalid", 1));
                    }
                }
            }
        }
    }
}
