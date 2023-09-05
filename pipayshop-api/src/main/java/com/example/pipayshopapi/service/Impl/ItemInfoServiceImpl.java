package com.example.pipayshopapi.service.Impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.entity.ItemInfo;
import com.example.pipayshopapi.entity.vo.*;
import com.example.pipayshopapi.mapper.ImageMapper;
import com.example.pipayshopapi.mapper.ItemCommodityEvaluateMapper;
import com.example.pipayshopapi.mapper.ItemCommodityInfoMapper;
import com.example.pipayshopapi.mapper.ItemInfoMapper;
import com.example.pipayshopapi.service.ItemInfoService;
import com.example.pipayshopapi.util.FileUploadUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

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

    @Resource
    private ImageMapper imageMapper;

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
        List<ItemInfoVO> itemInfoVOS = itemInfoMapper.selectItemInfoByItemIdOrUserId(userId, null);
        for (ItemInfoVO itemInfoVO : itemInfoVOS) {
            List<String> imageList = JSON.parseArray(itemInfoVO.getItemImagList(), String.class)
                                        .stream()
                                        .parallel()
                                        .map(imageId -> imageMapper.selectPath(imageId))
                                        .collect(Collectors.toList());
            itemInfoVO.setItemImagList(imageList.toString());
        }
        return itemInfoVOS;
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
        List<ItemListVO> itemInfos = itemInfoMapper.selectFollowItemByUserId(userId, (page - 1) * limit, limit);
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



    @Override
    public PageDataVO getItemEvaluate(String itemId, Integer page, Integer limit) {
        List<EvaluateVO> itemCommodityEvaluate = itemCommodityEvaluateMapper.getItemCommodityEvaluate(itemId, (page - 1) * limit, limit);
        Integer size = itemCommodityEvaluateMapper.getInfoSize(itemId);
        return new PageDataVO(size,itemCommodityEvaluate);
    }

    /**
     * 根据评论评分设置网店评分
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean setItemScore() {
        Integer update = itemInfoMapper.setItemScore();
        return update >0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String itemTopCategoryImags(MultipartFile multipartFile) {
        return FileUploadUtil.allUploadImageData(multipartFile, imageMapper, FileUploadUtil.ITEM_TOP_CATEGORY_IMG,null);
    }
}
