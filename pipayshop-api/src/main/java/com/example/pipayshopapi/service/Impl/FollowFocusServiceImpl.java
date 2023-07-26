package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.pipayshopapi.entity.FollowFocus;
import com.example.pipayshopapi.mapper.FollowFocusMapper;
import com.example.pipayshopapi.service.FollowFocusService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 * 粉丝关注表 服务实现类
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@Service
public class FollowFocusServiceImpl extends ServiceImpl<FollowFocusMapper, FollowFocus> implements FollowFocusService {

    @Resource
    private FollowFocusMapper followFocusMapper;

    /**
     * 根据用户Id或者粉丝Id查找粉丝关注时间
     * */
    @Override
    public Date selectUpdateTimeByFollowId( String followId) {
        FollowFocus followFocus = followFocusMapper.selectOne(new QueryWrapper<FollowFocus>()
                                                                    .eq(followId != null, "follow_id", followId));
        return followFocus.getCreateTime();
    }

    /**
     * 关注
     * */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean attentionFollowId(String followId) {
        int result = followFocusMapper.update(null, new UpdateWrapper<FollowFocus>()
                                                                .eq(followId != null, "uid", followId)
                                                                .set("status", 0));
        return result > 0;
    }

    /**
     * 取关
     * */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean cancelAttentionFollowId(String followId) {
        int result = followFocusMapper.update(null, new UpdateWrapper<FollowFocus>()
                                                                .eq(followId != null, "uid", followId)
                                                                .set("status", 1));
        return result > 0;
    }


}
