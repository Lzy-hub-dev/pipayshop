package com.example.pipayshopapi.service.Impl;

import com.example.pipayshopapi.entity.ItemFollowFocus;
import com.example.pipayshopapi.mapper.ItemFollowFocusMapper;
import com.example.pipayshopapi.service.ItemFollowFocusService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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

    /**
     * 用户关注网店接口
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
}
