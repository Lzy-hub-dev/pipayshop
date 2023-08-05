package com.example.pipayshopapi.service.Impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.entity.BrandInfo;
import com.example.pipayshopapi.entity.ItemCommodityEvaluate;
import com.example.pipayshopapi.entity.ItemCommodityInfo;
import com.example.pipayshopapi.entity.ItemFollowFocus;
import com.example.pipayshopapi.entity.dto.ApplyItemCommodityDTO;
import com.example.pipayshopapi.entity.dto.ExamineCommodityDTO;
import com.example.pipayshopapi.entity.dto.ItemSearchConditionDTO;
import com.example.pipayshopapi.entity.vo.*;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.mapper.*;
import com.example.pipayshopapi.service.ItemCommodityInfoService;
import com.example.pipayshopapi.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
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
    @Resource
    private ItemInfoMapper itemInfoMapper;
    @Resource
    private BrandInfoMapper brandInfoMapper;

    @Resource
    private ItemFollowFocusMapper itemFollowFocusMapper;

    @Resource
    private ItemCommodityEvaluateMapper itemCommodityEvaluateMapper;


    /**
     * 某一二级分类下的商品列表分页展示
     */
    @Override
    public PageDataVO commodityOfCateList(CommodityPageVO commodityPageVO) {

        Integer page = commodityPageVO.getPage();
        Integer limit = commodityPageVO.getLimit();
        int startIndex = (page - 1) * limit;

        List<CommodityVO> commodityList = commodityInfoMapper.commodityOfCateList(commodityPageVO.getSecondCategoryId(), startIndex, limit);

        return new PageDataVO(commodityInfoMapper.listCount(commodityPageVO.getSecondCategoryId()), commodityList);

    }

    /**
     * 发布网店商品
     *
     * @param itemCommodityInfoVO
     * @return
     */
    /**
     * 发布网店商品
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean issueItemCommodity(ApplyItemCommodityDTO applyItemCommodityDTO) {
        // 属性转移
        ItemCommodityInfo itemCommodityInfo = new ItemCommodityInfo();
        itemCommodityInfo.setCommodityId(StringUtil.generateShortId());
        itemCommodityInfo.setBrandId(applyItemCommodityDTO.getBrandId());
        itemCommodityInfo.setOriginPrice(applyItemCommodityDTO.getOriginPrice());
        itemCommodityInfo.setPrice(applyItemCommodityDTO.getPrice());
        itemCommodityInfo.setDegreeLoss(applyItemCommodityDTO.getDegreeLoss());
        itemCommodityInfo.setItemCommodityName(applyItemCommodityDTO.getItemCommodityName());
        itemCommodityInfo.setOriginAddress(applyItemCommodityDTO.getOriginAddress());
        itemCommodityInfo.setDetails(applyItemCommodityDTO.getDetails());
        itemCommodityInfo.setFreeShippingNum(applyItemCommodityDTO.getFreeShippingNum());
        itemCommodityInfo.setCategoryId(applyItemCommodityDTO.getCategoryId());
        itemCommodityInfo.setInventory(applyItemCommodityDTO.getInventory());
        itemCommodityInfo.setColorList(applyItemCommodityDTO.getColorList());
        itemCommodityInfo.setAcceptAddressList(applyItemCommodityDTO.getAcceptAddressList());
        itemCommodityInfo.setSizeList(applyItemCommodityDTO.getSizeList());
        itemCommodityInfo.setOriginName(applyItemCommodityDTO.getOriginName());
        itemCommodityInfo.setOriginPhone(applyItemCommodityDTO.getOriginPhone());
        itemCommodityInfo.setAvatarImag(applyItemCommodityDTO.getAvatarImag());
        itemCommodityInfo.setImagsList(applyItemCommodityDTO.getImagsList());
        itemCommodityInfo.setDetailImagList(applyItemCommodityDTO.getDetailImagList());
        int result = commodityInfoMapper.insert(itemCommodityInfo);
        return result > 0;
    }

    /**
     * 下面的搜索商品接口
     */
    @Override
    public PageDataVO itemSearchCommodity(ItemSearchConditionDTO dto) {
        if (dto == null) {
            log.error("参数列表为空");
            throw new BusinessException("服务器异常！");
        }
        LambdaQueryWrapper<ItemCommodityInfo> wrapper = new LambdaQueryWrapper<ItemCommodityInfo>()
                .eq(dto.getBrandId() != null && !"".equals(dto.getBrandId()), ItemCommodityInfo::getBrandId, dto.getBrandId())
                .eq(dto.getDegreeLoss() != null, ItemCommodityInfo::getDegreeLoss, dto.getDegreeLoss());


        if (dto.getMaxPrice() != null && dto.getMinPrice() != null) {
            // 过滤掉 最大价<最小价 的情况
            if (dto.getMinPrice().compareTo(dto.getMaxPrice()) == 1) {
                BigDecimal temp = dto.getMaxPrice();
                dto.setMaxPrice(dto.getMinPrice());
                dto.setMinPrice(temp);
            }
            wrapper.between(ItemCommodityInfo::getPrice, dto.getMinPrice(), dto.getMaxPrice());
        } else if (dto.getMaxPrice() != null) {
            // 最小价格为空
            wrapper.le(ItemCommodityInfo::getPrice, dto.getMaxPrice());
        } else if (dto.getMinPrice() != null) {
            // 最大价格为空
            wrapper.ge(ItemCommodityInfo::getPrice, dto.getMinPrice());
        }
        // 模糊查询-商品名称
        if (dto.getCommodityName() != null) {
            wrapper.like(ItemCommodityInfo::getItemCommodityName, dto.getCommodityName());
        }
        // 是否免运费
        if (dto.getFreeShippingNum() != null) {
            if (dto.getFreeShippingNum() == 0) {
                wrapper.eq(ItemCommodityInfo::getFreeShippingNum, 0);
            } else {
                wrapper.ne(ItemCommodityInfo::getFreeShippingNum, 0);
            }
        }
        // 价格排序
        if (dto.getPriceOrder() != null) {
            if (dto.getPriceOrder() == 0) {
                wrapper.orderByAsc(ItemCommodityInfo::getPrice);
            }else if (dto.getPriceOrder() == 1) {
                wrapper.orderByDesc(ItemCommodityInfo::getPrice);
            }
        }
        // 发布时间升序排列
        if (dto.getCreateTime()) {
            wrapper.orderByDesc(ItemCommodityInfo::getCreateTime);
        }
        // 设置分页参数
        Page<ItemCommodityInfo> page = new Page<>(dto.getPage(), dto.getLimit());
        wrapper.eq(ItemCommodityInfo::getStatus, 0);
        // 查询分页数据封装到page中
        commodityInfoMapper.selectPage(page, wrapper);
        // 封装数据
        return new PageDataVO((int) page.getTotal(), page.getRecords());

    }

    @Override
    public List<CommodityVO> itemCommodityChoose(String itemId, String brandId) {
        // 获取同一网店同一品牌的商品的vo
        List<CommodityVO> commodityVOS = commodityInfoMapper.itemCommodityChoose(itemId, brandId);
        return commodityVOS;
    }

    @Override
    public CommodityDetailVO itemCommodityDetail(String commodityId) {
        ItemCommodityInfo itemCommodityInfo = commodityInfoMapper.selectOne(new QueryWrapper<ItemCommodityInfo>()
                .eq("commodity_id", commodityId));
        String itemId = itemCommodityInfo.getItemId();
        CommodityDetailVO commodityDetailVO = new CommodityDetailVO(itemCommodityInfo.getCommodityId(), null, itemCommodityInfo.getItemCommodityName()
                , itemCommodityInfo.getOriginPrice(), null, null, itemCommodityInfo.getOriginAddress(), null
                , itemId, itemCommodityInfo.getPrice(), itemCommodityInfo.getDetails(), null,
                itemCommodityInfo.getInventory(), itemCommodityInfo.getFreeShippingNum(), itemCommodityInfo.getCategoryId(),
                null, null, itemCommodityInfo.getDegreeLoss(), null, null, null);
        String colorListString = itemCommodityInfo.getColorList();
        if (colorListString != null) {
            commodityDetailVO.setColorList(JSON.parseArray(colorListString, String.class));
        }
        String sizeListString = itemCommodityInfo.getSizeList();
        if (sizeListString != null) {
            commodityDetailVO.setSizeList(JSON.parseArray(sizeListString, String.class));
        }
        String acceptAddressListString = itemCommodityInfo.getAcceptAddressList();
        if (acceptAddressListString != null) {
            commodityDetailVO.setAcceptAddressList(JSON.parseArray(acceptAddressListString, String.class));
        }
        String imagsListString = itemCommodityInfo.getImagsList();
        if (imagsListString != null) {
            commodityDetailVO.setImagsList(JSON.parseArray(imagsListString, String.class));
        }
        String couponsListString = itemCommodityInfo.getCouponsList();
        if (couponsListString != null) {
            commodityDetailVO.setCouponsList(JSON.parseArray(couponsListString, String.class));
        }
        String tagListString = itemCommodityInfo.getTagList();
        if (tagListString != null) {
            commodityDetailVO.setTagList(JSON.parseArray(tagListString, String.class));
        }
        String detailImagList = itemCommodityInfo.getDetailImagList();
        if (detailImagList != null){
            commodityDetailVO.setDetailImagList(JSON.parseArray(detailImagList, String.class));
        }
        String brandId = itemCommodityInfo.getBrandId();
        if (brandId != null) {
            BrandInfo brandInfo = brandInfoMapper.selectOne(new QueryWrapper<BrandInfo>()
                    .eq("b_id", brandId)
                    .eq("del_flag", 0)
                    .select("title"));
            commodityDetailVO.setTitle(brandInfo.getTitle());
        }
        // 封装该商品的评论总数
        int evaluateCount = itemCommodityEvaluateMapper.selectCount(new QueryWrapper<ItemCommodityEvaluate>()
                .eq("commodity_id", commodityId)
                .eq("status", 0)).intValue();
        commodityDetailVO.setEvaluateCount(evaluateCount);
        // 封装该商品的所在店铺数据
        ItemVO itemVO = itemInfoMapper.selectBasicData(itemId);
        int fanSum = itemFollowFocusMapper.selectCount(new QueryWrapper<ItemFollowFocus>().eq("item_id", itemId)
                .eq("status", 0)).intValue();
        int commoditySum = commodityInfoMapper.selectCount(new QueryWrapper<ItemCommodityInfo>().eq("item_id", itemId)
                .eq("status", 0)).intValue();
        itemVO.setFanSum(fanSum);
        itemVO.setItemCommoditySum(commoditySum);
        commodityDetailVO.setItemVO(itemVO);
        return commodityDetailVO;
    }

    /**
     * 根据用户id查询 对应的 网店收藏列表
     */
    @Override
    public PageDataVO getCollectList(Integer page,Integer limit,String userId) {
        Integer integer = commodityInfoMapper.selectAllCollectProductByUserId(userId);
        List<ItemCommodityInfoVO> itemCommodityInfoVOS = commodityInfoMapper.selectCollectProductByUserId((page - 1) * limit, limit, userId);
        return new PageDataVO(integer,itemCommodityInfoVOS);
    }



    /**
     * 根据用户id查询用户浏览商品历史-网店
     */
    @Override
    public List<ItemCommodityInfoVO> historyList(String userId) {
        return commodityInfoMapper.selectHistoryProductByUserId(userId);
    }

    @Override
    public boolean changeCommodityStatus(String commodity, String status) {
        LambdaUpdateWrapper<ItemCommodityInfo> wr = new LambdaUpdateWrapper<ItemCommodityInfo>()
                .eq(ItemCommodityInfo::getCommodityId, commodity);
        if ("1".equals(status) || "2".equals(status)) {
            wr.set(ItemCommodityInfo::getStatus, status);
        }
        return commodityInfoMapper.update(null, wr) > 0;
    }

    /**
     * 根据网店id查询网店的商品列表
     * @param itemId
     * @return
     */
    @Override
    public ItemInfoVO commodityList(String itemId) {
        List<ItemCommodityVO> voList = commodityInfoMapper.commodityList(itemId);
        List<ItemInfoVO> itemInfoVO = itemInfoMapper.selectItemInfoByItemIdOrUserId(null, itemId);
        if (itemInfoVO != null) {
            ItemInfoVO vo = itemInfoVO.get(0);
            vo.setCommodityInfoList(voList);
            return vo;
        }
        return null;
    }

    /**
     * 根据卖家id查询网店的商品审核列表
     *
     * @return
     */
    @Override
    public List<ItemCommodityVO> examineCommodityList(ExamineCommodityDTO dto) {
        return commodityInfoMapper.examineCommodityList(dto);
    }


}
