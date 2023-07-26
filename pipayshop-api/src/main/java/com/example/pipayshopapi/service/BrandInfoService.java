package com.example.pipayshopapi.service;

import com.example.pipayshopapi.entity.BrandInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.vo.BrandInfoVO;
import com.example.pipayshopapi.entity.vo.ShopCategoryVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author nws
 * @since 2023-07-26
 */
public interface BrandInfoService extends IService<BrandInfo> {

    /**
     * 查找品牌分类列表
     * @return
     */
    List<ShopCategoryVO> selectAllBrandList();

}
