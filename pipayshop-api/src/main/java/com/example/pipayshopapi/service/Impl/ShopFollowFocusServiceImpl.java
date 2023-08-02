package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.entity.ShopCommodityEvaluate;
import com.example.pipayshopapi.entity.ShopFollowFocus;
import com.example.pipayshopapi.entity.UserInfo;
import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.entity.vo.ShopCommodityEvaluateVO;
import com.example.pipayshopapi.entity.vo.ShopUserFollowInfoVO;
import com.example.pipayshopapi.mapper.ShopFollowFocusMapper;
import com.example.pipayshopapi.mapper.UserInfoMapper;
import com.example.pipayshopapi.service.ShopFollowFocusService;
import com.example.pipayshopapi.service.UserInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private UserInfoMapper userInfoMapper;

    /**
     * 关注实体店
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


    /**
     * 获取粉丝列表
     */
    @Override
    public PageDataVO getFollowList(String shopId, Integer pageNum, Integer pageSize) {
        List<ShopUserFollowInfoVO> result = shopFollowFocusMapper.shopFollowFocusList(shopId, pageNum - 1, pageSize);
        Long count = shopFollowFocusMapper.selectCount(new QueryWrapper<ShopFollowFocus>().eq("shop_id", shopId));
        return new PageDataVO(count.intValue(), result);
    }

}
