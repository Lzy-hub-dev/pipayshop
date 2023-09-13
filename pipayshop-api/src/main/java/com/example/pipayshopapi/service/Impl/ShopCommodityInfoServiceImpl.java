package com.example.pipayshopapi.service.Impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.entity.ShopCommodityInfo;
import com.example.pipayshopapi.entity.ShopDetailInfoVO;
import com.example.pipayshopapi.entity.ShopInfo;
import com.example.pipayshopapi.entity.ShopTags;
import com.example.pipayshopapi.entity.dto.ApplyShopCommodityDTO;
import com.example.pipayshopapi.entity.vo.*;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.mapper.ImageMapper;
import com.example.pipayshopapi.mapper.ShopCommodityInfoMapper;
import com.example.pipayshopapi.mapper.ShopInfoMapper;
import com.example.pipayshopapi.mapper.ShopTagsMapper;
import com.example.pipayshopapi.service.ShopCommodityInfoService;
import com.example.pipayshopapi.util.FileUploadUtil;
import com.example.pipayshopapi.util.ImageConstants;
import com.example.pipayshopapi.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

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
    private ShopTagsMapper shopTagsMapper;
    @Resource
    private ShopInfoMapper shopInfoMapper;

    @Resource
    private ImageMapper imageMapper;
    /**
     * 发布实体店商品
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean issueShopCommodity(ApplyShopCommodityDTO applyShopCommodityDTO) {
        // 属性转移
        String commodityId = StringUtil.generateShortId();
        ShopCommodityInfo shopCommodityInfo = new ShopCommodityInfo(null,
                commodityId, applyShopCommodityDTO.getCommodityName(),
                applyShopCommodityDTO.getCommodityImgList().get(0),
                JSON.toJSONString(applyShopCommodityDTO.getCommodityImgList()),
                applyShopCommodityDTO.getCommodityDetail(), applyShopCommodityDTO.getPrice(),
                null, applyShopCommodityDTO.getShopId(), null, null,
                applyShopCommodityDTO.getValidityTime(), applyShopCommodityDTO.getResidue(),
                applyShopCommodityDTO.getReservationInformation(), 0,
                null, null);
        //shop-1剩余数量
        int shopId = shopInfoMapper.update(null, new UpdateWrapper<ShopInfo>()
                .eq("shop_id", applyShopCommodityDTO.getShopId())
                .setSql("upload_commodity_balance= upload_commodity_balance -1"));
        if (shopId < 1){throw new RuntimeException();}
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
     * 根据实体店id查询商品列表
     */
    @Override
    public List<ApplicationRecordVO> selectCommodityByUidAndStatus(String shopId) {
        return shopCommodityInfoMapper.selectCommdityListByShopIdPage(shopId);
    }



    /**
     * 根据商品id，更改商品的上下架状态
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateCommodityStatus(String commodityId, Integer status) {
        try {
            if (!(status==0||status==1)){
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
    @Transactional(rollbackFor = Exception.class)
    public boolean updateCommodityUp(String commodityId) {
        int result1 = shopCommodityInfoMapper.update(null, new UpdateWrapper<ShopCommodityInfo>()
                .eq("commodity_id", commodityId)
                .set("status", 1));
        int result2 = shopInfoMapper.addUploadBalanceByCommodityId(commodityId);
        if (result2 <= 0 || result1 <= 0) {
            throw new BusinessException("服务异常,请稍后重试");
        }
        return true;
    }

    /**
     * 根据商品id，下架变为审核中
     *
     * @param commodityId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateCommodityCheck(String commodityId) {
        int balance = shopInfoMapper.selectUploadCommodityBalanceByCommodityId(commodityId);
        if (balance <= 0) {
            throw new BusinessException("该店铺商品可上架数量余额不足！");
        }
        int result = shopCommodityInfoMapper.update(null, new UpdateWrapper<ShopCommodityInfo>()
                .eq("commodity_id", commodityId)
                .set("status", 2));
        return result > 0;
    }

    @Override
    public Integer getResidueByCommodityId(String commodityId) {
        QueryWrapper wrapper = new QueryWrapper<ShopCommodityInfo>();
        wrapper.select("residue");
        wrapper.eq("commodity_id",commodityId);
        ShopCommodityInfo shopCommodityInfo = shopCommodityInfoMapper.selectOne(wrapper);
        return shopCommodityInfo.getResidue();
    }

    /**
     * 根据店铺id查找实体店商品的详情信息列表
     */
    @Override
    public List<ShopCommodityInfo1VO> selectShopInfoListByShopId(String shopId) {

        List<ShopCommodityInfo1VO> shopCommodityInfo1VOS = shopCommodityInfoMapper.selectCommodityByShopId(shopId);
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
        return shopCommodityInfo1VOS;
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
                        wrapper.eq("status",0)
                                .or()
                                .eq("status",1);
                    }
                }));
        List<ShopCommodityInfo> shopCommodityInfos = page.getRecords();
        for (ShopCommodityInfo shopCommodityInfo : shopCommodityInfos) {
            shopCommodityInfo.setAvatarImag(imageMapper.selectPath(shopCommodityInfo.getAvatarImag()));
            List<String> imageIdlist = JSON.parseArray(shopCommodityInfo.getCommodityImgList(), String.class);
            List<String> imageList = imageIdlist.stream()
                                                .parallel()
                                                .map(imageId -> imageMapper.selectPath(imageId))
                                                .collect(Collectors.toList());
            shopCommodityInfo.setCommodityImgList(imageList.toString());
        }
        return new PageDataVO((int) page.getTotal(),shopCommodityInfos);
    }

    /**
     * 根据商品的id查找实体店商品的详情信息
     */
    @Override
    public ShopDetailInfoVO selectShopInfoByCommodityId(String commodityId) {
        //获取商品基本信息
        ShopDetailInfoVO shopDetailInfoVO = shopCommodityInfoMapper.selectShopInfoByCommodityId(commodityId);
        List<String> imageIdList = JSON.parseArray(shopDetailInfoVO.getCommodityImgList(), String.class);
        List<String> imageList = imageIdList.stream()
                                            .parallel()
                                            .map(imageId -> imageMapper.selectPath(imageId))
                                            .collect(Collectors.toList());
        shopDetailInfoVO.setCommodityImgList1(imageList);
        return shopDetailInfoVO ;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String shopCommodityTopImageUp(MultipartFile multipartFile) {
        return FileUploadUtil.allUploadImageData(multipartFile, imageMapper, FileUploadUtil.SHOP_COMMODITY_TOP_IMAGE_UP,null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<String> shopCommodityImageUp(MultipartFile[] multipartFile) {
        List<String> imageSizeList_1 = new ArrayList<>();
        List<String> imageSizeList_2 = new ArrayList<>();
        List<String> imageIdList = new ArrayList<>();
        imageSizeList_1.add(ImageConstants.SHOP_COMMODITY_IMAGE_UP_SMALL);
        imageSizeList_1.add(ImageConstants.SHOP_COMMODITY_IMAGE_UP_BIG);
        imageSizeList_2.add(ImageConstants.SHOP_COMMODITY_IMAGE_UP_BIG);
        System.out.println(multipartFile);
        for (int i = 0; i < multipartFile.length; i++) {
            if (i==0){

                imageIdList.add(FileUploadUtil.allUploadImageData(multipartFile[i], imageMapper, FileUploadUtil.SHOP_COMMODITY_IMAGE_UP,imageSizeList_1));
            }else {

                imageIdList.add(FileUploadUtil.allUploadImageData(multipartFile[i], imageMapper, FileUploadUtil.SHOP_COMMODITY_IMAGE_UP,imageSizeList_2));
            }
        }
        return imageIdList;
    }
}
