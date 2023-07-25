package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.pipayshopapi.entity.BgImg;
import com.example.pipayshopapi.mapper.BgImgMapper;
import com.example.pipayshopapi.service.BgImgService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.util.StringUtil;
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
     * 增加店的首页背景图片
     * @param bgImg
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addBgImg(BgImg bgImg) {

        String bgId= StringUtil.generateShortId();
        bgImg.setBgId(bgId);
        int result = bgImgMapper.insert(bgImg);
        return result > 0;

    }

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

    /**
     * 真实删除店的首页背景图片
     * @param bgId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean realDeleteBgImg(String bgId) {
        int result = bgImgMapper.update(null, new UpdateWrapper<BgImg>()
                                                        .eq("bg_id",bgId)
                                                        .set("del_flag",2));
        return result > 0;
    }

    /**
     * 店的首页背景图片详情信息
     * @param bgId
     * @return
     */
    @Override
    public BgImg bgImgDetail(String bgId) {
        BgImg bgImg = bgImgMapper.selectOne(new QueryWrapper<BgImg>()
                                                .eq("bg_id",bgId));
        return bgImg;
    }

    /**
     * 修改店的首页背景图片信息
     * @param bgImg
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean editBgImg(BgImg bgImg) {

       int result = bgImgMapper.update(bgImg,new UpdateWrapper<BgImg>()
                                                .eq("bg_id",bgImg.getBgId()));
       return result > 0;

    }


}
