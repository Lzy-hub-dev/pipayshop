package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.alibaba.fastjson.JSON;
import com.example.pipayshopapi.entity.ShopCommodityInfo;
import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.entity.ShopInfo;
import com.example.pipayshopapi.entity.dto.ApplyShopCommodityDTO;
import com.example.pipayshopapi.entity.vo.ShopCommodityVO;
import com.example.pipayshopapi.mapper.ShopCommodityInfoMapper;
import com.example.pipayshopapi.service.ShopCommodityInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.util.FileUploadUtil;
import com.example.pipayshopapi.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.awt.*;
import java.util.ArrayList;
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
    public boolean issueShopCommodity(ApplyShopCommodityDTO applyShopCommodityDTO, MultipartFile[] files) {
        // 创建一个集合存储商品图片
        List<String> imagesList = new ArrayList<>();
        for (MultipartFile multipartFile : files) {
            // 获取存储到本地空间并返回图片url
            imagesList.add(FileUploadUtil.uploadFile(multipartFile));
        }
        // 将list集合转为string
        String jsonString = JSON.toJSONString(imagesList);
        // 属性转移
        ShopCommodityInfo shopCommodityInfo = new ShopCommodityInfo();
        shopCommodityInfo.setCommodityId(StringUtil.generateShortId());
        shopCommodityInfo.setCommodityName(applyShopCommodityDTO.getCommodityName());
        shopCommodityInfo.setCommodityImgList(jsonString);
        shopCommodityInfo.setCommodityDetail(applyShopCommodityDTO.getCommodityDetail());
        shopCommodityInfo.setPrice(applyShopCommodityDTO.getPrice());
        shopCommodityInfo.setShopId(applyShopCommodityDTO.getShopId());
        shopCommodityInfo.setResidue(applyShopCommodityDTO.getResidue());
        shopCommodityInfo.setReservationInformation(applyShopCommodityDTO.getReservationInformation());
//        shopCommodityInfo.setTagList(applyShopCommodityDTO.getTagList());
        shopCommodityInfo.setMyEvaluate(applyShopCommodityDTO.getMyEvaluate());
        return shopCommodityInfoMapper.insert(shopCommodityInfo) > 0;
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
