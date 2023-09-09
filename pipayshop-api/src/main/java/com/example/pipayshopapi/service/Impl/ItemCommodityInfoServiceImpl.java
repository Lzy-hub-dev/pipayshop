package com.example.pipayshopapi.service.Impl;

import cn.hutool.core.collection.ConcurrentHashSet;
import cn.hutool.core.thread.ThreadUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.entity.*;
import com.example.pipayshopapi.entity.dto.ApplyItemCommodityDTO;
import com.example.pipayshopapi.entity.dto.ItemSearchConditionDTO;
import com.example.pipayshopapi.entity.vo.*;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.mapper.*;
import com.example.pipayshopapi.service.ItemCommodityInfoService;
import com.example.pipayshopapi.util.FileUploadUtil;
import com.example.pipayshopapi.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 网店的商品表 服务实现类
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@Service
@Slf4j
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

    @Resource
    private ItemCommodityInfoMapper itemCommodityInfoMapper;

    @Resource
    private CountryCommodityMapper countryCommodityMapper;

    @Resource
    private ImageMapper imageMapper;


    /**
     * 某一二级分类下的商品列表分页展示
     */
    @Override
    public PageDataVO commodityOfCateList(CommodityPageVO commodityPageVO) {
        Integer limit = commodityPageVO.getLimit();
        int startIndex = (commodityPageVO.getPage() - 1) * limit;
        List<CommodityVO> commodityList = commodityInfoMapper.commodityOfCateList(commodityPageVO.getSecondCategoryId(),commodityPageVO.getCountryCode(), startIndex, limit);
        Integer count = commodityInfoMapper.listCount(commodityPageVO.getSecondCategoryId());
        return new PageDataVO(count, commodityList);

    }


    /**
     * 发布网店商品
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean issueItemCommodity(ApplyItemCommodityDTO applyItemCommodityDTO) {
        ItemCommodityInfo itemCommodityInfo = new ItemCommodityInfo();
        // 属性转移
        BeanUtils.copyProperties(applyItemCommodityDTO, itemCommodityInfo);
        // 补全其他字段数据
        String commodityId = StringUtil.generateShortId();
        itemCommodityInfo.setCommodityId(commodityId);
        // 收货人地址集合
        itemCommodityInfo.setAcceptAddressList(JSON.toJSONString(applyItemCommodityDTO.getAcceptAddressList()));
        // 商品图片的地址集合
        itemCommodityInfo.setImagsList(JSON.toJSONString(applyItemCommodityDTO.getImagsList()));
        itemCommodityInfo.setAvatarImag(applyItemCommodityDTO.getImagsList().get(0));
        itemCommodityInfo.setDetailImagList(JSON.toJSONString(applyItemCommodityDTO.getDetailImags()));
        // 商品状态为上架状态
        itemCommodityInfo.setStatus(0);
        // 插入新增商品数据
        int result = commodityInfoMapper.insert(itemCommodityInfo);
        if (result < 1) {
            log.error("插入新增商品数据失败");
            throw new RuntimeException();
        }
        // 网店商品上架剩余数-1
        int update = itemInfoMapper.update(null, new UpdateWrapper<ItemInfo>()
                .eq("item_id", applyItemCommodityDTO.getItemId())
                .gt("upload_balance", 0)
                .setSql("upload_balance = upload_balance - 1"));
        if (update < 1) {
            log.error("网店商品上架剩余数扣减失败");
            throw new RuntimeException();
        }

        // 将商品数据记录到发布表上
        CountryCommodity countryCommodity = new CountryCommodity(null, applyItemCommodityDTO.getCountryCode(), commodityId, null, null);
        int insert = countryCommodityMapper.insert(countryCommodity);
        return insert > 0;
    }

    /**
     * 网店首页条件过滤列表展示
     */
    @Override
    public PageDataVO itemSearchCommodity(ItemSearchConditionDTO dto) {
        // 为分页操作的相关数据赋予正确offset
        int starIndex = (dto.getPage() - 1) * dto.getLimit();
        dto.setPage(starIndex);
        List<ItemCommoditiesVO> resultList= commodityInfoMapper.itemSearchCommodity(dto);
        int count = commodityInfoMapper.itemSearchCommodityCount(dto);
        // 封装数据
        return new PageDataVO(count, resultList);
    }

    @Override
    public List<CommodityVO> itemCommodityChoose(String itemId, String brandId) {
        // 获取同一网店同一品牌的商品的vo
        return commodityInfoMapper.itemCommodityChoose(itemId, brandId);
    }

    @Override
    public CommodityDetailVO itemCommodityDetail(String commodityId) {
        // 获取网店的数据
        ItemCommodityInfo itemCommodityInfo = commodityInfoMapper.selectOne(new QueryWrapper<ItemCommodityInfo>()
                .eq("commodity_id", commodityId));
//        log.error(String.valueOf(itemCommodityInfo)+"==========================");
        String itemId = itemCommodityInfo.getItemId();
        Map<String, List<String>> typeMap = new HashMap<>();
        // 转移部分非json型的数据
        CommodityDetailVO commodityDetailVO = new CommodityDetailVO();
        // 属性转移
        BeanUtils.copyProperties(itemCommodityInfo, commodityDetailVO);
        String acceptAddressListString = itemCommodityInfo.getAcceptAddressList();
        if (acceptAddressListString != null) {
            commodityDetailVO.setAcceptAddressList(JSON.parseArray(acceptAddressListString, String.class));

        }
        String imagsListString = itemCommodityInfo.getImagsList();
        if (imagsListString != null) {
            List<String> imageIdList= JSON.parseArray(imagsListString, String.class);
            List<String> images = imageIdList.stream().parallel()
                    .map(imageId -> imageMapper.selectPath(imageId))
                    .collect(Collectors.toList());
            commodityDetailVO.setImagsList(images);
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
        if (detailImagList != null) {
            List<String> imageIdList = JSON.parseArray(detailImagList, String.class);
            List<String> imageList = imageIdList.stream()
                                                .parallel()
                                                .map(imageId -> imageMapper.selectPath(imageId))
                                                .collect(Collectors.toList());
            commodityDetailVO.setDetailImagList(imageList);
        }
        if (typeMap.size() != 0) {
            commodityDetailVO.setTypeMap(typeMap);
        }
        String brandId = itemCommodityInfo.getBrandId();
        // 解析品牌字段
        if (brandId != null) {
            BrandInfo brandInfo = brandInfoMapper.selectOne(new QueryWrapper<BrandInfo>()
                    .eq("b_id", brandId)
                    .eq("del_flag", 0)
                    .select("title"));
            if (brandInfo != null) {
                commodityDetailVO.setTitle(brandInfo.getTitle());
            }
        }
        // 封装该商品的评论总数
        int evaluateCount = itemCommodityEvaluateMapper.selectCount(new QueryWrapper<ItemCommodityEvaluate>()
                .eq("commodity_id", commodityId)
                .eq("status", 0)).intValue();
        commodityDetailVO.setEvaluateCount(evaluateCount);
        // 封装该商品的所在店铺数据
        ItemVO itemVO = itemInfoMapper.selectBasicData(itemId);
        int fanSum = Math.toIntExact(itemFollowFocusMapper.selectCount(new QueryWrapper<ItemFollowFocus>().eq("item_id", itemId)
                .eq("status", 0)));
        int commoditySum = Math.toIntExact(commodityInfoMapper.selectCount(new QueryWrapper<ItemCommodityInfo>().eq("item_id", itemId)
                .eq("status", 0)));
        itemVO.setFanSum(fanSum);
        itemVO.setItemCommoditySum(commoditySum);
        commodityDetailVO.setItemVO(itemVO);
        return commodityDetailVO;
    }

    /**
     * 根据用户id查询 对应的 网店收藏列表
     */
    @Override
    public PageDataVO getCollectList(Integer page, Integer limit, String userId) {
        Integer integer = commodityInfoMapper.selectAllCollectProductByUserId(userId);
        List<ItemCollectionVO> itemCommodityInfoVOS = commodityInfoMapper.selectCollectProductByUserId((page - 1) * limit, limit, userId);
        return new PageDataVO(integer, itemCommodityInfoVOS);
    }


    /**
     * 根据用户id查询用户浏览商品历史-网店
     */
    @Override
    public PageDataVO historyList(Integer page, Integer limit, String userId) {
        Integer integer = commodityInfoMapper.selectAllHistoryProductByUserId(userId);
        List<ItemCollectionVO> itemCommodityInfoVOS = commodityInfoMapper.selectHistoryProductByUserId((page - 1) * limit, limit, userId);
        return new PageDataVO(integer, itemCommodityInfoVOS);
    }

    /**
     * 根据商品id，上架变为下架
     *
     * @param commodity
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean changeCommodityUp(String commodity) {
        int result1 = commodityInfoMapper.update(null, new UpdateWrapper<ItemCommodityInfo>()
                .eq("commodity_id", commodity)
                .set("status", 1));
        int result2 = itemInfoMapper.addUploadBalanceByCommodityId(commodity);
        if (result1 <= 0 || result2 <= 0) {
            throw new BusinessException();
        }
        return true;
    }


    /**
     * 根据商品id，下架变为审核中
     *
     * @param commodity
     * @return
     */
    @Override
    public boolean changeCommodityCheck(String commodity) {
        // 判断商品上架剩余数 是否为0
        int balance = itemInfoMapper.selectUploadCommodityBalanceByCommodityId(commodity);
        if (balance <= 0) {
            throw new BusinessException("商品可上架数量为0");
        }
        int result = commodityInfoMapper.update(null, new UpdateWrapper<ItemCommodityInfo>()
                .eq("commodity_id", commodity)
                .set("status", 2));
        return result > 0;
    }

    @Override
    public Integer getInventoryByCommodityId(String commodityId) {
        QueryWrapper wrapper = new QueryWrapper<ItemCommodityInfo>();
        wrapper.select("inventory");
        wrapper.eq("commodity_id",commodityId);
        ItemCommodityInfo shopCommodityInfo = itemCommodityInfoMapper.selectOne(wrapper);
        return shopCommodityInfo.getInventory();
    }

    @Override
    public String getOriginAddressById(String commodityId) {
        ItemCommodityInfo commodity_id = commodityInfoMapper.selectOne(new QueryWrapper<ItemCommodityInfo>()
                .eq("commodity_id", commodityId));
        return commodity_id.getOriginAddress();
    }



    /**
     * 根据网店id查询网店的商品列表
     */
    @Override
    public ItemInfoVO commodityList(String itemId) {
        // 获取商品列表
        List<ItemCommodityVO> voList = commodityInfoMapper.commodityList(itemId);
        // 获取网店基本信息
        List<ItemInfoVO> itemInfoVO = itemInfoMapper.selectItemInfoByItemIdOrUserId(null, itemId);
        if (itemInfoVO != null) {
            ItemInfoVO vo = itemInfoVO.get(0);
            List<String> imageIds = JSON.parseArray(vo.getItemImagList(), String.class);
            List<String> imageList = imageIds.stream()
                                            .parallel()
                                            .map(imageId -> imageMapper.selectPath(imageId))
                                            .collect(Collectors.toList());
            vo.setItemImagList(imageList.toString());
            vo.setCommodityInfoList(voList);
            return vo;
        }
        return null;
    }

    /**
     * 根据卖家id查询网店的商品审核列表
     */
    @Override
    public List<AuditItemVO> examineCommodityList(String itemId) {
        return commodityInfoMapper.examineCommodityList(itemId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String itemTopImagsUp(MultipartFile multipartFile) {
        List<String> imageSizeList = new ArrayList<>();
        imageSizeList.add("320,180");
        imageSizeList.add("640,360");
        return FileUploadUtil.allUploadImageData(multipartFile, imageMapper, FileUploadUtil.ROOM_TOP_IMG,imageSizeList);
    }


}
