package com.example.pipayshopapi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.BrandInfo;
import com.example.pipayshopapi.entity.vo.BrandInfoVO;

import java.util.List;

/**
 * <p>
 *  品牌服务类
 * </p>
 *
 * @author nws
 * @since 2023-07-26
 */
public interface BrandInfoService extends IService<BrandInfo> {

    /**
     * 查找二级分类的id查找品牌的集合
     * @return
     */
    List<BrandInfoVO> selectAllBrandList();


    /**
     * 网店获取品牌的集合
     * @return
     */
    List<BrandInfoVO> itemSelectAllBrandList();
}
