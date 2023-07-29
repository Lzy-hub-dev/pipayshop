package com.example.pipayshopapi.service.Impl;

import com.example.pipayshopapi.entity.ItemCommodityInfo;
import com.example.pipayshopapi.entity.ItemInfo;
import com.example.pipayshopapi.entity.ShopCommodityInfo;
import com.example.pipayshopapi.entity.ShopInfo;
import com.example.pipayshopapi.entity.vo.ShopCommodityVO;
import com.example.pipayshopapi.mapper.ShopCommodityInfoMapper;
import com.example.pipayshopapi.service.ShopCommodityInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

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
     * 根据用户id查询 用户收藏的商品列表
     *
     * @param userId
     * @return
     */
    @Override
    public List<ShopCommodityInfo> getCollectList(String userId) {
        return shopCommodityInfoMapper.selectCollectProductByUserId(userId);
    }

    /**
     * 根据用户id查询用户关注的网店列表
     *
     * @param userId
     * @return
     */
    @Override
    public List<ShopInfo> getFollowList(String userId) {
        return shopCommodityInfoMapper.selectFollowProductByUserId(userId);
    }

    /**
     * 根据用户id查询用户浏览商品历史-实体店
     *
     * @param userId
     * @return
     */
    @Override
    public List<ShopCommodityVO> historyList(String userId) {
        return shopCommodityInfoMapper.selectHistoryProductByUserId(userId);
    }

}
