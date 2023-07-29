package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.pipayshopapi.entity.ShopCommodityInfo;
import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.entity.vo.ShopCommodityVO;
import com.example.pipayshopapi.mapper.ShopCommodityInfoMapper;
import com.example.pipayshopapi.service.ShopCommodityInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.awt.*;

/**
 * <p>
 * 实体店的商品表 服务实现类
 * </p>
 *
 * @author zxb
 * @since 2023-07-27
 */
@Service
public class ShopCommodityInfoServiceImpl extends ServiceImpl<ShopCommodityInfoMapper, ShopCommodityInfo> implements ShopCommodityInfoService {

    @Resource
    private ShopCommodityInfoMapper shopCommodityInfoMapper;

    /**
     * 发布实体店商品
     * @param shopCommodityVO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean issueShopCommodity(ShopCommodityVO shopCommodityVO, String commodityImgList) {
        ShopCommodityInfo shopCommodity = new ShopCommodityInfo();

        shopCommodity.setCommodityImgList(commodityImgList);

        shopCommodity.setCommodityName(shopCommodityVO.getCommodityName());
        shopCommodity.setCommodityId(StringUtil.generateShortId());
        shopCommodity.setCommodityDetail(shopCommodityVO.getCommodityDetail());
        shopCommodity.setShopId(shopCommodityVO.getShopId());
        shopCommodity.setPrice(shopCommodityVO.getPrice());

        int result = shopCommodityInfoMapper.insert(shopCommodity);


        return result>0;
    }

    /**
     * 根据店铺id查找实体店商品的详情信息列表
     */
    @Override
    public PageDataVO selectShopInfoListByShopId(Integer limit, Integer pages, String shopId) {
        Page<ShopCommodityInfo> page = new Page<>(pages,limit);
        shopCommodityInfoMapper.selectPage(page,new QueryWrapper<ShopCommodityInfo>()
                                                    .eq("shop_id",shopId));
        return new PageDataVO((int) page.getTotal(),page.getRecords());
    }

    /**
     * 根据商品的id查找实体店商品的详情信息
     */
    @Override
    public ShopCommodityInfo selectShopInfoByCommodityId(String commodityId) {
        ShopCommodityInfo shopCommodityInfo = shopCommodityInfoMapper.selectOne(new QueryWrapper<ShopCommodityInfo>()
                                                                                .eq("commodity_id", commodityId));
        return shopCommodityInfo;
    }







}
