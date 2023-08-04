package com.example.pipayshopapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.ShopFollowFocus;
import com.example.pipayshopapi.entity.ShopInfo;
import com.example.pipayshopapi.entity.vo.PageDataVO;

import java.util.List;

/**
 * <p>
 * 粉丝关注表 服务类
 * </p>
 *
 * @author jjf
 * @since 2023-07-29
 */
public interface ShopFollowFocusService extends IService<ShopFollowFocus> {

    /**
     * 关注实体店
     */
    Boolean userFollowItem(String followId, String shopId);

    /**
     * 用户取消关注
     */
    Boolean userUnfollow(String followId, String shopId);

    /**
     * 获取粉丝列表
     */
    PageDataVO getFollowList(String shopId, Integer pageNum, Integer pageSize);

    boolean isFollowShop(String shopId, String userId);

    Integer SelectFollowShopSum(String shopId);

    /**
     * 根据用户id查询用户关注的实体店列表
     * @param userId
     * @return
     */
    List<ShopInfo> getFollowList(String userId);
}
