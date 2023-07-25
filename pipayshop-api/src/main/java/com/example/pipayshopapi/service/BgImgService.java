package com.example.pipayshopapi.service;

import com.example.pipayshopapi.entity.BgImg;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * item/shop的首页背景轮播图数据 服务类
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
public interface BgImgService extends IService<BgImg> {


    /**
     *增加店的首页背景图片
     */
    boolean addBgImg(BgImg bgImg);

    /**
     * 逻辑删除店的首页背景图片
     */
    boolean logicDeleteBgImg(String bgId);

    /**
     * 真实删除店的首页背景图片
     */
    boolean realDeleteBgImg(String bgId);

    /**
     * 店的首页背景图片详情信息
     */
     BgImg bgImgDetail(String bgId);

    /**
     * 修改店的首页背景图片信息
     */
    boolean editBgImg(BgImg bgImg);



}
