package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.pipayshopapi.entity.ItemFollowFocus;
import com.example.pipayshopapi.mapper.ItemFollowFocusMapper;
import com.example.pipayshopapi.service.ItemFollowFocusService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    /**
     * 用户取消关注
     * @param followId
     * @param itemId
     * @return
     * */
    @Override
    public Boolean userUnfollow(String followId, String itemId) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(date);
        int result = itemFollowFocusMapper.update(null, new UpdateWrapper<ItemFollowFocus>()
                .eq("item_id", itemId)
                .eq("follow_id", followId)
                .set("update_time", format)
                .set("status", 1));
        return result>0;
    }
}
