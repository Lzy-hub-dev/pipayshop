package com.example.pipayshopapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.ItemFollowFocus;
import com.example.pipayshopapi.entity.vo.FansVO;

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
     * */
    Boolean userFollowItem(String followId,String itemId);

    /**
     * 用户取消关注
     * */
    Boolean userUnfollow(String followId,String itemId);

    /**
     * 查询网店粉丝列表
     */
    List<FansVO> itemFans(String itemId,Integer pageNum,Integer pageSize);

    Integer itemFansSum(String itemId);

    Boolean isItemFollow(String followId, String itemId);
}
