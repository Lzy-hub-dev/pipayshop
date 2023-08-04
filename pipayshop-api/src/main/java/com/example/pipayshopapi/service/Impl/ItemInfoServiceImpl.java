package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.pipayshopapi.entity.ItemInfo;
import com.example.pipayshopapi.entity.vo.ItemInfoVO;
import com.example.pipayshopapi.entity.vo.UserInfoVO;
import com.example.pipayshopapi.mapper.ItemInfoMapper;
import com.example.pipayshopapi.service.ItemInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zxb
 * @since 2023-07-27
 */
@Service
public class ItemInfoServiceImpl extends ServiceImpl<ItemInfoMapper, ItemInfo> implements ItemInfoService {

    @Resource
    private ItemInfoMapper itemInfoMapper;

    /**
     * 获取网店商品详情的网店信息接口
     * @param commodityId
     * @return
     */
    @Override
    public ItemInfoVO getItemInfo(String commodityId) {
        ItemInfoVO itemInfo = itemInfoMapper.getItemInfo(commodityId);
        return itemInfo;
    }

    /**
     * 根据用户id获取网店信息
     *
     * @param userId
     * @return
     */
    @Override
    public List<ItemInfoVO> getItemInfoByUid(String userId) {
        return itemInfoMapper.selectItemInfoByItemIdOrUserId(userId,null);
    }
    /**
     * 根据用户id查询 对应的 网店关注列表
     *
     * @param userId
     * @return
     */
    @Override
    public List<ItemInfo> getFollowList(String userId) {
        return itemInfoMapper.selectFollowItemByUserId(userId);
    }
    /**
     * 根据用户id获取网店数量
     *
     * @param userId
     * @return
     */
    @Override
    public Integer getItemCountByUserId(String userId) {
        LambdaQueryWrapper<ItemInfo> wrapper = new LambdaQueryWrapper<ItemInfo>().eq(ItemInfo::getStatus, 0)
                .eq(ItemInfo::getUid, userId);
        return itemInfoMapper.selectCount(wrapper).intValue();
    }

    /**
     * 根据网店id获取网店地址
     * @param itemId
     * @return
     */
    @Override
    public String getItemAddressById(String itemId) {
        ItemInfo itemInfo = itemInfoMapper.selectOne(new QueryWrapper<ItemInfo>()
                .eq("item_id", itemId)
                .select("address"));

        return itemInfo.getAddress();
    }

    /**
     * 根据用户id-网店-升级vip
     *
     * @param userId
     * @return
     */
    @Override
    public boolean upVip(String userId) {
        return itemInfoMapper.update(null, new LambdaUpdateWrapper<ItemInfo>()
                .eq(ItemInfo::getUid, userId)
                .eq(ItemInfo::getStatus, 0)
                .set(ItemInfo::getMembership, true)) > 0;
    }

    /**
     * 根据用户id-判断对应网店是否vip
     *
     * @param userId
     * @return
     */
    @Override
    public boolean isVip(String userId) {
        return itemInfoMapper.selectCount(new LambdaQueryWrapper<ItemInfo>()
                .eq(ItemInfo::getUid, userId)
                .eq(ItemInfo::getStatus, 0)
                .eq(ItemInfo::getMembership, true)) > 0;
    }
}
