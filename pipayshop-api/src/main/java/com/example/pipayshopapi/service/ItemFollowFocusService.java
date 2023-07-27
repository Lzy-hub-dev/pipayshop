package com.example.pipayshopapi.service;

import com.example.pipayshopapi.entity.ItemFollowFocus;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.vo.ItemInfoVO;
import com.example.pipayshopapi.entity.vo.UserInfoVO;

/**
 * <p>
 * 粉丝关注表 服务类
 * </p>
 *
 * @author zxb
 * @since 2023-07-27
 */
public interface ItemFollowFocusService extends IService<ItemFollowFocus> {


    /**
     * 用户关注网店
     * @param followId
     * @param itemId
     * @return
     * */
    Boolean userFollowItem(String followId,String itemId);

}
