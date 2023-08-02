package com.example.pipayshopapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.BgImg;
import com.example.pipayshopapi.entity.vo.BgImgVO;
import com.example.pipayshopapi.entity.vo.ResponseVO;
import org.springframework.web.multipart.MultipartFile;

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
     * 逻辑删除店的首页背景图片
     */
    boolean logicDeleteBgImg(String bgId);


    /**
     * 新增首页背景轮播图
     * @param file
     * @param bgImg
     * @return
     */
    Boolean addBgImg(MultipartFile file, BgImg bgImg);

    /**
     * 查询首页轮播背景图列表
     * @return
     */
    List<BgImgVO> selectBgImgList();
}
