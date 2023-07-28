package com.example.pipayshopapi.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.pipayshopapi.entity.ItemCommodityInfo;
import com.example.pipayshopapi.entity.OrderInfo;
import com.example.pipayshopapi.entity.dto.ItemSearchConditionDTO;
import com.example.pipayshopapi.entity.vo.*;
import com.example.pipayshopapi.mapper.ItemCommodityInfoMapper;
import com.example.pipayshopapi.service.ItemCommodityInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.util.StringUtil;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 网店的商品表 服务实现类
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@Service
public class ItemCommodityInfoServiceImpl extends ServiceImpl<ItemCommodityInfoMapper, ItemCommodityInfo> implements ItemCommodityInfoService {

    @Resource
    private ItemCommodityInfoMapper commodityInfoMapper;

    /**
     *某一二级分类下的商品列表分页展示
     */
    @Override
    public PageDataVO commodityOfCateList(commodityPageVO commodityPageVO) {

        Integer page =commodityPageVO.getPage();
        Integer limit=commodityPageVO.getLimit();
        int startIndex= (page-1)*limit;

        List<commodityVO> commodityList = commodityInfoMapper.commodityOfCateList(commodityPageVO.getCategoryId(),startIndex,limit);

        return new PageDataVO( commodityInfoMapper.listCount(commodityPageVO.getCategoryId()),commodityList);

    }

    /**
     * 发布网店商品
     * @param itemCommodityInfoVO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean issueItemCommodity(ItemCommodityInfoVO itemCommodityInfoVO, String imagsList) {

        ItemCommodityInfo itemCommodityInfo = new ItemCommodityInfo();
        itemCommodityInfo.setCommodityId(StringUtil.generateShortId());
        itemCommodityInfo.setPrice(itemCommodityInfoVO.getPrice());
        itemCommodityInfo.setItemCommodityName(itemCommodityInfoVO.getItemCommodityName());
        itemCommodityInfo.setOriginAddress(itemCommodityInfoVO.getOriginAddress());
        itemCommodityInfo.setDetails(itemCommodityInfoVO.getDetails());
        itemCommodityInfo.setImagsList(imagsList);
        itemCommodityInfo.setCategoryId(itemCommodityInfoVO.getCategoryId());
        int result = commodityInfoMapper.insert(itemCommodityInfo);
        return result>0;
    }

    /**
     * 网店首页下面的搜索商品接口
     * @param itemSearchConditionDTO
     * @return
     */
    @Override
    public PageDataVO itemSearchCommodity(ItemSearchConditionDTO itemSearchConditionDTO) {

        // 设置分页参数
        Page<ItemCommodityInfo> page = new Page<>(itemSearchConditionDTO.getPage(),itemSearchConditionDTO.getLimit());

        // 查询分页数据封装到page中
        commodityInfoMapper.selectPage(page, new LambdaQueryWrapper<ItemCommodityInfo>()
        // 可选的条件查询，可要可不要
.eq(itemSearchConditionDTO.getBrandId()!=null && !"".equals(itemSearchConditionDTO.getBrandId()),ItemCommodityInfo::getBrandId,itemSearchConditionDTO.getBrandId())
.eq(itemSearchConditionDTO.getDegreeLoss() != null ,ItemCommodityInfo::getDegreeLoss,itemSearchConditionDTO.getDegreeLoss())
.between(itemSearchConditionDTO.getMaxPrice()!=null && itemSearchConditionDTO.getMinPrice() != null,ItemCommodityInfo::getPrice,itemSearchConditionDTO.getMinPrice(),itemSearchConditionDTO.getMaxPrice())
        );
        // 封装数据
        return new PageDataVO((int)page.getTotal(), page.getRecords());

    }

    @Override
    public List<commodityVO> itemCommodityChoose(String itemId,String brandId) {
        // 获取同一网店同一品牌的商品的vo
        List<commodityVO> commodityVOS = commodityInfoMapper.itemCommodityChoose(itemId, brandId);
        return commodityVOS;
    }

    @Override
    public CommodityDetailVO itemCommodityDetail(String commodityId) {
        CommodityDetailVO commodityDetailVO = commodityInfoMapper.itemCommodityDetail(commodityId);

        return commodityDetailVO;
    }
}
