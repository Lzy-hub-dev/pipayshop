package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.entity.ShopCommodityLive;
import com.example.pipayshopapi.entity.vo.LivePageVO;
import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.entity.vo.ShopCommodityLiveVO;
import com.example.pipayshopapi.mapper.ShopCommodityLiveMapper;
import com.example.pipayshopapi.service.ShopCommodityLiveService;
import com.example.pipayshopapi.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
        List<ShopCommodityLiveVO> shopCommodityLiveVOS = shopCommodityLiveMapper.selectShopCommodityLiveVO(limit, (pages-1)*limit);
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
    public PageDataVO selectShopLiveVOByCondition(LivePageVO livePageVO) {
        Integer integer = shopCommodityLiveMapper.selectAllShopLiveVOByCondition(livePageVO.getCheckInTime(),livePageVO.getDepartureTime(),livePageVO.getAdult(),livePageVO.getChildren());
        List<ShopCommodityLiveVO> shopCommodityLiveVOS = shopCommodityLiveMapper.selectShopLiveVOByCondition(livePageVO.getLimit(),(livePageVO.getPage()-1)*livePageVO.getLimit(), livePageVO.getCheckInTime(),livePageVO.getDepartureTime(),livePageVO.getAdult(),livePageVO.getChildren());
        return new PageDataVO(integer,shopCommodityLiveVOS);
    }

    /**
     * 发布实体店住的服务
     */
    @Override
    public boolean insertShopLive(ShopCommodityLive shopCommodityLive) {
        shopCommodityLive.setCommodityId(StringUtil.generateShortId());
        int result = shopCommodityLiveMapper.insert(shopCommodityLive);
        return result>0;
    }

    /**
     * 根据房型id更改房型信息
     */
    @Override
    public boolean updateShopLive(ShopCommodityLive shopCommodityLive) {
        int result = shopCommodityLiveMapper.update(null, new UpdateWrapper<ShopCommodityLive>()
                .eq("commodity_id", shopCommodityLive.getCommodityId())
                .set("detail", shopCommodityLive.getDetail())
                .set("tag_list", shopCommodityLive.getTagList())
                .set("image_list", shopCommodityLive.getImageList())
                .set("land", shopCommodityLive.getLand())
                .set("room", shopCommodityLive.getRoom())
                .set("rest_room", shopCommodityLive.getRestRoom())
                .set("bed", shopCommodityLive.getBed())
                .set("adult", shopCommodityLive.getAdult())
                .set("children", shopCommodityLive.getChildren())
                .set("restricted", shopCommodityLive.getRestricted())
                .set("check_in_time", shopCommodityLive.getCheckInTime())
                .set("departure_time", shopCommodityLive.getDepartureTime())
                .set("basics", shopCommodityLive.getBasics())
                .set("bath", shopCommodityLive.getBath())
                .set("status", shopCommodityLive.getStatus())
                .set("price",shopCommodityLive.getPrice())
                .set("avatar_imag",shopCommodityLive.getAvatarImag())
                .set("appliance",shopCommodityLive.getAppliance()));

        return result>0;
    }
}
