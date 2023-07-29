package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.pipayshopapi.entity.ShopCommodityLive;
import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.entity.vo.ShopCommodityLiveVO;
import com.example.pipayshopapi.mapper.ShopCommodityLiveMapper;
import com.example.pipayshopapi.service.ShopCommodityLiveService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 实体店住的服务表 服务实现类
 * </p>
 *
 * @author zxb
 * @since 2023-07-29
 */
@Service
public class ShopCommodityLiveServiceImpl extends ServiceImpl<ShopCommodityLiveMapper, ShopCommodityLive> implements ShopCommodityLiveService {

    @Resource
    private ShopCommodityLiveMapper shopCommodityLiveMapper;

    /**
     * 查找实体店住的服务列表
     */
    @Override
    public PageDataVO selectShopCommodityLiveVO(Integer limit, Integer pages) {
        Integer integer = shopCommodityLiveMapper.selectAllShopCommodityLiveVO();
        int p=(pages-1)*limit;
        List<ShopCommodityLiveVO> shopCommodityLiveVOS = shopCommodityLiveMapper.selectShopCommodityLiveVO(limit, p);
        return new PageDataVO(integer,shopCommodityLiveVOS);
    }

    /**
     * 根据服务id查找服务的详情信息
     */
    @Override
    public ShopCommodityLive selectShopLiveById(String commodityId) {
        ShopCommodityLive shopCommodityLive = shopCommodityLiveMapper.selectOne(new QueryWrapper<ShopCommodityLive>()
                                                                                .eq("commodity_id", commodityId));
        return shopCommodityLive;
    }

    /**
     * 条件筛选查找实体店住的服务列表
     */
    @Override
    public PageDataVO selectShopLiveVOByCondition(Integer limit, Integer pages, Date checkInTime, Date departureTime, Integer adult, Integer children) {
        Integer integer = shopCommodityLiveMapper.selectAllShopLiveVOByCondition(checkInTime, departureTime, adult, children);
        int p=(pages-1)*limit;
        List<ShopCommodityLiveVO> shopCommodityLiveVOS = shopCommodityLiveMapper.selectShopLiveVOByCondition(limit, p, checkInTime, departureTime, adult, children);
        return new PageDataVO(integer,shopCommodityLiveVOS);
    }
}
