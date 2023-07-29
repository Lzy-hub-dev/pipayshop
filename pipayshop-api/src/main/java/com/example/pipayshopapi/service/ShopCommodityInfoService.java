package com.example.pipayshopapi.service;

import com.example.pipayshopapi.entity.ShopCommodityInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.dto.ApplyShopCommodityDTO;
import com.example.pipayshopapi.entity.vo.ShopCommodityVO;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 实体店的商品表 服务类
 * </p>
 *
 * @author zxb
 * @since 2023-07-27
 */
public interface ShopCommodityInfoService extends IService<ShopCommodityInfo> {

    /**
     * 发布实体店商品
     * @param applyShopCommodityDTO
     * @param files
     * @return
     */
    boolean issueShopCommodity(ApplyShopCommodityDTO applyShopCommodityDTO, MultipartFile[] files);

}
