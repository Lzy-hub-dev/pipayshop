package com.example.pipayshopapi.service;

import com.example.pipayshopapi.entity.FollowFocus;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;

/**
 * <p>
 * 粉丝关注表 服务类
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
public interface FollowFocusService extends IService<FollowFocus> {


    /**
     * 根据粉丝Id查找粉丝关注时间
     * @param followId
     * @return
     * */
    Date selectUpdateTimeByFollowId(String followId);


    /**
     * 关注
     * @param followId
     * @return
     * */
    boolean attentionFollowId(String followId);

    /**
     * 取关
     * @param followId
     * @return
     * */
    boolean cancelAttentionFollowId(String followId);


}
