package com.example.pipayshopapi.service.Impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.pipayshopapi.entity.ItemCommodityHistory;
import com.example.pipayshopapi.entity.ItemCommodityInfo;
import com.example.pipayshopapi.entity.ItemInfo;
import com.example.pipayshopapi.entity.dto.ApplyItemCommodityDTO;
import com.example.pipayshopapi.entity.dto.ItemSearchConditionDTO;
import com.example.pipayshopapi.entity.vo.*;
import com.example.pipayshopapi.mapper.ItemCommodityHistoryMapper;
import com.example.pipayshopapi.mapper.ItemCommodityInfoMapper;
import com.example.pipayshopapi.mapper.ItemInfoMapper;
import com.example.pipayshopapi.service.ItemCommodityInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.util.FileUploadUtil;
import com.example.pipayshopapi.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
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
    private ItemCommodityHistoryMapper itemCommodityHistoryMapper;


    /**
     * 某一二级分类下的商品列表分页展示
     */
    @Override
    public PageDataVO commodityOfCateList(CommodityPageVO commodityPageVO) {

        Integer page = commodityPageVO.getPage();
        Integer limit = commodityPageVO.getLimit();
        int startIndex = (page - 1) * limit;

        List<CommodityVO> commodityList = commodityInfoMapper.commodityOfCateList(commodityPageVO.getCategoryPid(), startIndex, limit);

        return new PageDataVO(commodityInfoMapper.listCount(commodityPageVO.getCategoryPid()), commodityList);

    }

    /**
     * 发布网店商品
     *
     * @param itemCommodityInfoVO
     * @return
     */
    /**
     * 发布网店商品
     * @param applyItemCommodityDTO
     * @param files
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean issueItemCommodity(ApplyItemCommodityDTO applyItemCommodityDTO, MultipartFile[] files) {

        // 创建集合放图片url
        List<String> iamgesList = new ArrayList<>();
        for (MultipartFile file : files) {
            // 通过工具类放入本地文件并返回文件路径存入集合中
            iamgesList.add(FileUploadUtil.uploadFile(file,FileUploadUtil.ITEM_COMMODITY_IMG));
        }
        // 将list集合转为string
        String jsonString = JSON.toJSONString(iamgesList);
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
        itemCommodityInfo.setImagsList(jsonString);
        int result = commodityInfoMapper.insert(itemCommodityInfo);
        return result > 0;
    }

    /**
     * 网店首页下面的搜索商品接口
     *
     * @param itemSearchConditionDTO
     * @return
     */
    @Override
    public PageDataVO itemSearchCommodity(ItemSearchConditionDTO itemSearchConditionDTO) {

        // 设置分页参数
        Page<ItemCommodityInfo> page = new Page<>(itemSearchConditionDTO.getPage(), itemSearchConditionDTO.getLimit());

        // 查询分页数据封装到page中
        commodityInfoMapper.selectPage(page, new LambdaQueryWrapper<ItemCommodityInfo>()
                // 可选的条件查询，可要可不要
                .eq(itemSearchConditionDTO.getBrandId() != null && !"".equals(itemSearchConditionDTO.getBrandId()), ItemCommodityInfo::getBrandId, itemSearchConditionDTO.getBrandId())
                .eq(itemSearchConditionDTO.getDegreeLoss() != null, ItemCommodityInfo::getDegreeLoss, itemSearchConditionDTO.getDegreeLoss())
                .between(itemSearchConditionDTO.getMaxPrice() != null && itemSearchConditionDTO.getMinPrice() != null, ItemCommodityInfo::getPrice, itemSearchConditionDTO.getMinPrice(), itemSearchConditionDTO.getMaxPrice())
        );
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
    public CommodityDetailVO itemCommodityDetail(String commodityId,String userId) {
        CommodityDetailVO commodityDetailVO = commodityInfoMapper.itemCommodityDetail(commodityId);

        return commodityDetailVO;
    }

    /**
     * 根据用户id查询 对应的 网店收藏列表
     *
     * @param userId
     * @return
     */
    @Override
    public List<ItemCommodityInfo> getCollectList(String userId) {
        return commodityInfoMapper.selectCollectProductByUserId(userId);
    }

    /**
     * 根据用户id查询 对应的 网店关注列表
     *
     * @param userId
     * @return
     */
    @Override
    public List<ItemInfo> getFollowList(String userId) {
        return commodityInfoMapper.selectFollowItemByUserId(userId);
    }

    /**
     * 根据用户id查询用户浏览商品历史-网店
     *
     * @param userId
     * @return
     */
    @Override
    public List<ItemCommodityInfoVO> historyList(String userId) {
        return commodityInfoMapper.selectHistoryProductByUserId(userId);
    }



    /**
     * @param commodity
     * @param status    1:上架;2:下架
     * @return
     */
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
        List<ItemCommodityInfoVO> voList = commodityInfoMapper.commodityList(itemId);
        List<ItemInfoVO> itemInfoVO = itemInfoMapper.selectItemInfoByItemIdOrUserId(null,itemId);
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
     * @param userId
     * @param examineStatus 0:审核中;1:审核通过
     * @return
     */
    @Override
    public List<ItemCommodityInfoVO> examineCommodityList(String userId, Integer examineStatus) {
        return commodityInfoMapper.examineCommodityList(userId, examineStatus);
    }


}
