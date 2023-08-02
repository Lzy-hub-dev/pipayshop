package com.example.pipayshopapi.service.Impl;

import com.example.pipayshopapi.entity.ShopFollowFocus;
import com.example.pipayshopapi.mapper.ShopFollowFocusMapper;
import com.example.pipayshopapi.service.ShopFollowFocusService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * 粉丝关注表 服务实现类
 * </p>
 *
 * @author jjf
 * @since 2023-07-29
 */
@Service
public class ShopFollowFocusServiceImpl extends ServiceImpl<ShopFollowFocusMapper, ShopFollowFocus> implements ShopFollowFocusService {

    @Resource
    private ShopFollowFocusMapper shopFollowFocusMapper;

    /**
     * 关注实体店
     *
     * @param followId
     * @param shopId
     * @return
     */
    @Override
    public Boolean userFollowItem(String followId, String shopId) {
        ShopFollowFocus shopFollow = new ShopFollowFocus();
        shopFollow.setFollowId(followId);
        shopFollow.setShopId(shopId);
        int insert = shopFollowFocusMapper.insert(shopFollow);
        return insert > 0;
    }

    /**
     * 用户取消关注
     *
     * @param followId
     * @param shopId
     * @return
     */
    @Override
    public Boolean userUnfollow(String followId, String shopId) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(date);
        int result = shopFollowFocusMapper.update(null, new UpdateWrapper<ShopFollowFocus>()
                .eq("shop_id", shopId)
                .eq("follow_id", followId)
                .set("update_time", format)
                .set("status", 1));
        return result>0;
    }
}
