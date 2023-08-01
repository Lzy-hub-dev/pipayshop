package com.example.pipayshopapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.BgImg;


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
     * 逻辑删除店的首页背景图片
     */
    boolean logicDeleteBgImg(String bgId);


}
