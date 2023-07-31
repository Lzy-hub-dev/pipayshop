package com.example.pipayshopapi.service;

import com.example.pipayshopapi.entity.FollowFocus;
import com.example.pipayshopapi.entity.ItemFollowFocus;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.vo.ItemInfoVO;
import com.example.pipayshopapi.entity.vo.UserInfoVO;

import java.util.List;

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

    /**
     * 用户取消关注
     * @param followId
     * @param itemId
     * @return
     * */
    Boolean userUnfollow(String followId,String itemId);

    /**
     * 根据网店id或实体店id查询该网店粉丝列表
     *
     * @param id 网店id或实体店id
     * @param itemFlag (0:网店  1:实体店)
     * @return
     */
    List<FollowFocus> getFollowList(String id, Integer itemFlag);
}
