package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.entity.BgImg;
import com.example.pipayshopapi.mapper.BgImgMapper;
import com.example.pipayshopapi.service.BgImgService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * item/shop的首页背景轮播图数据 服务实现类
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@Service
public class BgImgServiceImpl extends ServiceImpl<BgImgMapper, BgImg> implements BgImgService {

    @Resource
    private BgImgMapper bgImgMapper;

    /**
     * 逻辑删除店的首页背景图片
     * @param bgId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean logicDeleteBgImg(String bgId) {
       int result = bgImgMapper.update(null, new UpdateWrapper<BgImg>()
                                                       .eq("bg_id",bgId)
                                                       .set("del_flag",1));
       return result > 0;
    }

}
