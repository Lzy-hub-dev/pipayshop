package com.example.pipayshopapi.service.Impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.entity.ShopCommodityHistory;
import com.example.pipayshopapi.entity.ShopCommodityInfo;
import com.example.pipayshopapi.entity.ShopInfo;
import com.example.pipayshopapi.entity.dto.ApplyShopCommodityDTO;
import com.example.pipayshopapi.entity.vo.*;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.mapper.ShopCommodityHistoryMapper;
import com.example.pipayshopapi.mapper.ShopCommodityInfoMapper;
import com.example.pipayshopapi.service.ShopCommodityInfoService;
import com.example.pipayshopapi.util.FileUploadUtil;
import com.example.pipayshopapi.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

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
    @Resource
    private ShopCommodityHistoryMapper shopCommodityHistoryMapper;
    /**
     * 发布实体店商品
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean issueShopCommodity(ApplyShopCommodityDTO applyShopCommodityDTO, MultipartFile[] files) {
        // 创建一个集合存储商品图片
        List<String> imagesList = new ArrayList<>();
        for (MultipartFile multipartFile : files) {
            // 获取存储到本地空间并返回图片url
            imagesList.add(FileUploadUtil.uploadFile(multipartFile,FileUploadUtil.SHOP_COMMODITY_IMG));
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
        shopCommodityInfo.setCategoryTopId(applyShopCommodityDTO.getCategoryTopId());
        shopCommodityInfo.setCategoryId(applyShopCommodityDTO.getCategoryId());
        shopCommodityInfo.setReservationInformation(applyShopCommodityDTO.getReservationInformation());
//        shopCommodityInfo.setTagList(applyShopCommodityDTO.getTagList());
        shopCommodityInfo.setMyEvaluate(applyShopCommodityDTO.getMyEvaluate());
        return shopCommodityInfoMapper.insert(shopCommodityInfo) > 0;
    }

    /**
     * 根据用户id查询 用户收藏的商品列表
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
     * 根据用户id查询，商品状态查询审核通过和未审核列表
     *
     * @param pageVO
     * @return
     */
    @Override
    public PageDataVO selectCommodityByUidAndStatus(OrderPageVO pageVO) {
        Integer integer = shopCommodityInfoMapper.selectAllCommodity(pageVO.getUid(), pageVO.getStatus());
        Integer page = pageVO.getPage();
        try {
            if (page==0){
                throw new Exception();
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("分页不能为0");
        }
        Integer limit = pageVO.getLimit()*page;
        int pages = page - 1;
        List<ShopCommodityInfoVO> shopCommodityInfoVOS = shopCommodityInfoMapper.selectCommodityByUidAndStatus(pages, limit, pageVO.getUid(), pageVO.getStatus());
        return new PageDataVO(integer,shopCommodityInfoVOS);
    }

    /**
     * 根据商品id，更改商品的上下架状态
     *
     * @param commodityId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateCommodityStatus(String commodityId, Integer status) {
        try {
            if (!(status==1||status==2)){
                throw new Exception();
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("你没有此权限");
        }
        int result = shopCommodityInfoMapper.update(null, new UpdateWrapper<ShopCommodityInfo>()
                .eq("commodity_id", commodityId)
                .set("status", status));
        return result>0;
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
     * 根据店铺id查找实体店商品的上架和下架列表
     */
    @Override
    public PageDataVO selectStatusListByShopId(CommodityStatusPageVO commodityStatusPageVO) {
        Page<ShopCommodityInfo> page = new Page<>(commodityStatusPageVO.getPage(),commodityStatusPageVO.getLimit());
        shopCommodityInfoMapper.selectPage(page,new QueryWrapper<ShopCommodityInfo>()
                .eq("shop_Id",commodityStatusPageVO.getShopId())
                .and(new Consumer<QueryWrapper<ShopCommodityInfo>>() {
                    @Override
                    public void accept(QueryWrapper<ShopCommodityInfo> wrapper) {
                        wrapper.eq("status",1)
                                .or()
                                .eq("status",2);
                    }
                }));

        return new PageDataVO((int) page.getTotal(),page.getRecords());
    }

    /**
     * 根据商品的id查找实体店商品的详情信息
     */
    @Override
    public ShopCommodityInfo selectShopInfoByCommodityId(String commodityId,String userId) {
        ShopCommodityInfo shopCommodityInfo = shopCommodityInfoMapper.selectOne(new QueryWrapper<ShopCommodityInfo>()
                .eq("commodity_id", commodityId));

        return shopCommodityInfo;
    }







}
