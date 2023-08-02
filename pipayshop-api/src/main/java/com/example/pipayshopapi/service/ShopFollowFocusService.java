package com.example.pipayshopapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.ShopFollowFocus;
import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.entity.vo.ShopUserFollowInfoVO;
import java.util.*;

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
     * @param followId
     * @param shopId
     * @return
     */
    Boolean userFollowItem(String followId, String shopId);

    /**
     * 用户取消关注
     * @param followId
     * @param shopId
     * @return
     */
    Boolean userUnfollow(String followId, String shopId);

    PageDataVO getFollowList(String shopId, Integer pageNum, Integer pageSize);
}
