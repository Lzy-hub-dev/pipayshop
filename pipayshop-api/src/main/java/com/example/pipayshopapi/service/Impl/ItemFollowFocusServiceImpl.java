package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.pipayshopapi.entity.FollowFocus;
import com.example.pipayshopapi.entity.ItemFollowFocus;
import com.example.pipayshopapi.entity.ShopFollowFocus;
import com.example.pipayshopapi.mapper.ItemFollowFocusMapper;
import com.example.pipayshopapi.mapper.ShopFollowFocusMapper;
import com.example.pipayshopapi.mapper.ShopInfoMapper;
import com.example.pipayshopapi.service.ItemFollowFocusService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 粉丝关注表 服务实现类
 * </p>
 *
 * @author zxb
 * @since 2023-07-27
 */
@Service
public class ItemFollowFocusServiceImpl extends ServiceImpl<ItemFollowFocusMapper, ItemFollowFocus> implements ItemFollowFocusService {

    @Resource
    private ItemFollowFocusMapper itemFollowFocusMapper;
    @Resource
    private ShopFollowFocusMapper shopFollowFocusMapper;


    /**
     * 用户关注网店接口
     *
     * @param followId
     * @param itemId
     * @return
     */
    @Override
    public Boolean userFollowItem(String followId, String itemId) {
        ItemFollowFocus itemFollowFocus = new ItemFollowFocus();
        itemFollowFocus.setFollowId(followId);
        itemFollowFocus.setItemId(itemId);
        int insert = itemFollowFocusMapper.insert(itemFollowFocus);
        return insert > 0;
    }

    /**
     * 根据网店id或实体店id查询该网店粉丝列表
     *
     * @param id       网店id或实体店id
     * @param itemFlag (0:网店  1:实体店)
     * @return
     */
    @Override
    public List getFollowList(String id, Integer itemFlag) {
        List container;
        // 网店
        if (itemFlag == 0) {
            container = itemFollowFocusMapper.selectList(new LambdaQueryWrapper<ItemFollowFocus>()
                    .eq(ItemFollowFocus::getItemId, id)
                    .eq(ItemFollowFocus::getStatus, 0));
        } else {// 实体店
            container = shopFollowFocusMapper.selectList(new LambdaQueryWrapper<ShopFollowFocus>()
                    .eq(ShopFollowFocus::getShopId, id)
                    .eq(ShopFollowFocus::getStatus, 0));
        }
        return container;
    }
}
