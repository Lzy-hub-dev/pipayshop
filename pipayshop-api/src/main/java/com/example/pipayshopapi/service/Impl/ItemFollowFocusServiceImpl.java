package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.entity.ItemFollowFocus;
import com.example.pipayshopapi.entity.vo.FansVO;
import com.example.pipayshopapi.mapper.ItemFollowFocusMapper;
import com.example.pipayshopapi.service.ItemFollowFocusService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
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


    /**
     * 用户关注网店接口
     * @param followId
     * @param itemId
     * @return
     */
    @Override
    public Boolean userFollowItem(String followId, String itemId) {
        Date date = new Date();
        int update = itemFollowFocusMapper.update(null, new UpdateWrapper<ItemFollowFocus>()
                .eq("follow_id", followId)
                .eq("item_id", itemId)
                .set("update_time", date)
                .set("status",0));
        if (update < 1){
            ItemFollowFocus itemFollowFocus = new ItemFollowFocus();
            itemFollowFocus.setFollowId(followId);
            itemFollowFocus.setItemId(itemId);
            int insert = itemFollowFocusMapper.insert(itemFollowFocus);
            return insert > 0;
        }
        return true;
    }

    /**
     * 用户取消关注
     * */
    @Override
    public Boolean userUnfollow(String followId, String itemId) {
        int result = itemFollowFocusMapper.update(null, new UpdateWrapper<ItemFollowFocus>()
                .eq("item_id", itemId)
                .eq("follow_id", followId)
                .set("update_time", new Date())
                .set("status", 1));
        return result>0;
    }

    /**
     * 查询网店粉丝列表

     */
    @Override
    public List<FansVO> itemFans(String itemId,Integer pageNum,Integer pageSize) {
        return itemFollowFocusMapper.itemFans(itemId,pageNum-1,pageSize);
    }

    @Override
    public Integer itemFansSum(String itemId) {
        return itemFollowFocusMapper.selectCount(new QueryWrapper<ItemFollowFocus>()
                .eq("item_id", itemId)
                .eq("status", 0)).intValue();
    }

    @Override
    public Boolean isItemFollow(String followId, String itemId) {
        ItemFollowFocus itemFollowFocus = itemFollowFocusMapper.selectOne(new QueryWrapper<ItemFollowFocus>()
                .eq("item_id", itemId)
                .eq("follow_id", followId)
                .eq("status", 0));
        return itemFollowFocus != null;
    }
}
