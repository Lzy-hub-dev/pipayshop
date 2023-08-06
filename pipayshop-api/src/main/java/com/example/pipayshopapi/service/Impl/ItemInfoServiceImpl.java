package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.entity.ItemInfo;
import com.example.pipayshopapi.entity.vo.EvaluateVO;
import com.example.pipayshopapi.entity.vo.ItemCommodityMinVO;
import com.example.pipayshopapi.entity.vo.ItemCommodityVO;
import com.example.pipayshopapi.entity.vo.ItemInfoVO;
import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.mapper.ItemCommodityEvaluateMapper;
import com.example.pipayshopapi.mapper.ItemCommodityInfoMapper;
import com.example.pipayshopapi.mapper.ItemInfoMapper;
import com.example.pipayshopapi.service.ItemInfoService;
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

    @Resource
    private ItemCommodityInfoMapper itemCommodityInfoMapper;

    @Resource
    private ItemCommodityEvaluateMapper itemCommodityEvaluateMapper;

    /**
     * 获取网店商品详情的网店信息接口
     * @param itemId
     * @return
     */
    @Override
    public PageDataVO getItemInfo(String itemId, Integer page, Integer limit, Boolean price) {
        List<ItemCommodityMinVO> itemCommodityMinVO = itemCommodityInfoMapper.getInfoByItemId(itemId, (page - 1) * limit, limit, price);
        Integer size = itemCommodityInfoMapper.getInfoSize(itemId);
        return new PageDataVO(size,itemCommodityMinVO);
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
    public PageDataVO getFollowList(String userId, Integer page, Integer limit) {
        Integer integer = itemInfoMapper.selectAllFollowItemByUserId(userId);
        List<ItemInfo> itemInfos = itemInfoMapper.selectFollowItemByUserId(userId, (page - 1) * limit, limit);
        return new PageDataVO(integer,itemInfos);
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
     */
    @Override
    public boolean isVip(String userId) {
        return itemInfoMapper.selectCount(new LambdaQueryWrapper<ItemInfo>()
                .eq(ItemInfo::getUid, userId)
                .eq(ItemInfo::getStatus, 0)
                .eq(ItemInfo::getMembership, true)) > 0;
    }


    // TODO
    @Override
    public PageDataVO getItemEvaluate(String itemId, Integer page, Integer limit) {
        List<EvaluateVO> itemCommodityEvaluate = itemCommodityEvaluateMapper.getItemCommodityEvaluate(itemId, (page - 1) * limit, limit);
        Integer size = itemCommodityEvaluateMapper.getInfoSize(itemId);
        return new PageDataVO(size,itemCommodityEvaluate);
    }


}
