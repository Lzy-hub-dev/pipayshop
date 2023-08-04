package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.pipayshopapi.entity.ShopCommodityLiveInfo;
import com.example.pipayshopapi.entity.vo.ShopCommodityLiveInfoListVO;
import com.example.pipayshopapi.mapper.ShopCommodityLiveInfoMapper;
import com.example.pipayshopapi.service.ShopCommodityLiveInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.service.ShopHotelRecordService;
import com.example.pipayshopapi.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 酒店的房型表 服务实现类
 * </p>
 *
 * @author zxb
 * @since 2023-08-02
 */
@Service
public class ShopCommodityLiveInfoServiceImpl extends ServiceImpl<ShopCommodityLiveInfoMapper, ShopCommodityLiveInfo> implements ShopCommodityLiveInfoService {

    @Resource
    private ShopCommodityLiveInfoMapper shopCommodityLiveInfoMapper;

    @Resource
    private ShopHotelRecordService shopHotelRecordService;
    /**
     * 根据房型id查找房型的详细信息
     */
    // TODO
    @Override
    public ShopCommodityLiveInfo selectShopLiveByRoomId(String roomId) {
        return shopCommodityLiveInfoMapper.selectOne(new QueryWrapper<ShopCommodityLiveInfo>()
                .eq("room_id", roomId)
                .eq("del_flag", 0)
                .eq("status", 1));
    }



    /**
     * 增加房型的详细信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertShopLiveInfo(ShopCommodityLiveInfo shopCommodityLiveInfo) {
        shopCommodityLiveInfo.setRoomId(StringUtil.generateShortId());
        int result = shopCommodityLiveInfoMapper.insert(shopCommodityLiveInfo);
        return result>0;
    }

    /**
     * 根据房型id更改房型的详细信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateShopLiveInfo(ShopCommodityLiveInfo shopCommodityLiveInfo) {
        int result = shopCommodityLiveInfoMapper.update(shopCommodityLiveInfo, new QueryWrapper<ShopCommodityLiveInfo>()
                  .eq("room_id", shopCommodityLiveInfo.getRoomId()));
        return result > 0;
    }

    /**
     * 根据房型id删除房型的详细信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteShopLiveInfo(String roomId) {
        int result = shopCommodityLiveInfoMapper.update(null, new UpdateWrapper<ShopCommodityLiveInfo>()
                .eq("room_id", roomId)
                .set("del_flag", 1));
        return result>0;
    }

    /**
     * 根据实体店id和入住时间和离店时间来搜索房型
     * @param shopId
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public List<ShopCommodityLiveInfoListVO> selectShopCommodityLiveInfoList(String shopId, Date startTime, Date endTime) {
        List<ShopCommodityLiveInfoListVO> shopCommodityLiveInfoListVOS = shopCommodityLiveInfoMapper.selectShopCommodityLiveInfoList(shopId);
        shopCommodityLiveInfoListVOS.stream().parallel().forEach(info->info.setInventory(shopHotelRecordService.getInventory(info.getRoomId(),startTime,endTime)));
        return shopCommodityLiveInfoListVOS;
    }
}
