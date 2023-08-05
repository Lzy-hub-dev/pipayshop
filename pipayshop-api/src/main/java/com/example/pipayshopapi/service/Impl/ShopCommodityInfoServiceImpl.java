package com.example.pipayshopapi.service.Impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.entity.ShopCommodityInfo;
import com.example.pipayshopapi.entity.ShopDetailInfoVO;
import com.example.pipayshopapi.entity.ShopTags;
import com.example.pipayshopapi.entity.dto.ApplyShopCommodityDTO;
import com.example.pipayshopapi.entity.vo.*;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.mapper.ShopCommodityEvaluateMapper;
import com.example.pipayshopapi.mapper.ShopCommodityInfoMapper;
import com.example.pipayshopapi.mapper.ShopTagsMapper;
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
    private ShopCommodityEvaluateMapper shopCommodityEvaluateMapper;
    @Resource
    private ShopTagsMapper shopTagsMapper;
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
        shopCommodityInfo.setReservationInformation(applyShopCommodityDTO.getReservationInformation());
//        shopCommodityInfo.setTagList(applyShopCommodityDTO.getTagList());
        shopCommodityInfo.setMyEvaluate(applyShopCommodityDTO.getMyEvaluate());
        return shopCommodityInfoMapper.insert(shopCommodityInfo) > 0;
    }

    /**
     * 根据用户id查询 用户收藏的商品列表
     */
    @Override
    public PageDataVO getCollectList(Integer page,Integer limit,String userId) {
        Integer integer = shopCommodityInfoMapper.selectAllCollectProductByUserId(userId);
        List<ShopCommodityInfoVO> shopCommodityInfoVOS = shopCommodityInfoMapper.selectCollectProductByUserId((page - 1) * limit, limit, userId);
        return new PageDataVO(integer,shopCommodityInfoVOS);
    }



    /**
     * 根据用户id查询用户浏览商品历史-实体店
     */
    @Override
    public PageDataVO historyList(Integer page,Integer limit,String userId) {
        Integer integer = shopCommodityInfoMapper.selectAllHistoryProductByUserId(userId);
        List<ShopCommodityListVO> shopCommodityListVOS = shopCommodityInfoMapper.selectHistoryProductByUserId((page - 1) * limit, limit, userId);
        return new PageDataVO(integer,shopCommodityListVOS);
    }

    /**
     * 根据用户id查询，商品状态查询审核通过和未审核列表
     */
    @Override
    public PageDataVO selectCommodityByUidAndStatus(OrderPageVO pageVO) {
        Integer integer = shopCommodityInfoMapper.selectAllCommodity(pageVO.getUid(), pageVO.getStatus());
        List<ShopCommodityInfoVO> shopCommodityInfo = shopCommodityInfoMapper.selectCommodityByUidAndStatus((pageVO.getPage()*pageVO.getLimit()), pageVO.getLimit(), pageVO.getUid(), pageVO.getStatus());
        return new PageDataVO(integer,shopCommodityInfo);
    }

    /**
     * 根据商品id，更改商品的上下架状态
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
     * 根据商品id，上架变为下架
     *
     * @param commodityId
     * @return
     */
    @Override
    public boolean updateCommodityUp(String commodityId) {
        int result = shopCommodityInfoMapper.update(null, new UpdateWrapper<ShopCommodityInfo>()
                .eq("commodity_id", commodityId)
                .set("status", 2));
        return result > 0;
    }

    /**
     * 根据商品id，下架变为审核中
     *
     * @param commodityId
     * @return
     */
    @Override
    public boolean updateCommodityCheck(String commodityId) {
        int result = shopCommodityInfoMapper.update(null, new UpdateWrapper<ShopCommodityInfo>()
                .eq("commodity_id", commodityId)
                .set("status", 0));
        return result > 0;
    }

    /**
     * 根据店铺id查找实体店商品的详情信息列表
     */
    @Override
    public PageDataVO selectShopInfoListByShopId(Integer limit, Integer pages, String shopId) {
        Integer count = shopCommodityInfoMapper.selectAllCommodityByShopId(shopId);
        int page = (pages - 1) * limit;
        List<ShopCommodityInfo1VO> shopCommodityInfo1VOS = shopCommodityInfoMapper.selectCommodityByShopId(page, limit, shopId);
        List<ShopTags> list1 = new ArrayList<>();
        for (ShopCommodityInfo1VO shopCommodityInfo1VO : shopCommodityInfo1VOS) {
            List<String> list = JSON.parseArray(shopCommodityInfo1VO.getTagList(), String.class);
            if (list==null||list.isEmpty()){
                continue;
            }
            for (String s : list) {
                ShopTags tag_ids = shopTagsMapper.selectOne(new QueryWrapper<ShopTags>().eq("tag_id", s));
                list1.add(tag_ids);
            }
            shopCommodityInfo1VO.setShopTagsList(list1);
        }
        return new PageDataVO(count,shopCommodityInfo1VOS);
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
    public ShopDetailInfoVO selectShopInfoByCommodityId(String commodityId) {
        //获取商品基本信息
        ShopDetailInfoVO shopDetailInfoVO = shopCommodityInfoMapper.selectShopInfoByCommodityId(commodityId);
        //根据商品ID 查十条商品评论列表
        List<EvaluateVO> list = shopCommodityEvaluateMapper.getEvaluateList(shopDetailInfoVO.getCommodityId());
        System.out.println(list);
        shopDetailInfoVO.setEvaluateVOList(list);
        return shopDetailInfoVO;
    }



}
