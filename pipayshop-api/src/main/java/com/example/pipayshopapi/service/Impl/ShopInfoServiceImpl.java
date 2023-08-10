package com.example.pipayshopapi.service.Impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.entity.ShopInfo;
import com.example.pipayshopapi.entity.ShopTags;
import com.example.pipayshopapi.entity.UserInfo;
import com.example.pipayshopapi.entity.dto.ApplyShopDTO;
import com.example.pipayshopapi.entity.dto.ShopDTO;
import com.example.pipayshopapi.entity.vo.*;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.mapper.ShopInfoMapper;
import com.example.pipayshopapi.mapper.ShopTagsMapper;
import com.example.pipayshopapi.mapper.UserInfoMapper;
import com.example.pipayshopapi.service.ShopInfoService;
import com.example.pipayshopapi.util.StringUtil;
import com.google.common.collect.Sets;
import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 实体店的信息 服务实现类
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@Service
public class ShopInfoServiceImpl extends ServiceImpl<ShopInfoMapper, ShopInfo> implements ShopInfoService {

    @Resource
    private ShopInfoMapper shopInfoMapper;

    @Resource
    private ShopTagsMapper tagMapper;
    @Resource
    private UserInfoMapper userInfoMapper;

    private static Set<String> userIdList = Sets.newConcurrentHashSet();

    /**
     * 根据二级分类-获取所有实体店列表
     */
    @Override
    public PageDataVO getShopInfoListByCondition(Integer limit, Integer pages, String categoryId,Boolean score) {

        // 获取总条数
        Integer indexShopInfoVOCount = shopInfoMapper.getIndexShopInfoVOCount(categoryId);
        List<IndexShopInfoVO> indexShopInfoVO = shopInfoMapper.getIndexShopInfoVO(categoryId, (pages - 1) * limit, limit,score);
        for (IndexShopInfoVO shopInfoVO : indexShopInfoVO) {
            List<String> list1 = new ArrayList<>();
            List<String> list = JSON.parseArray(shopInfoVO.getTagList(), String.class);
            if (list==null || list.isEmpty()){
                continue;
            }
            for (String tagId : list) {
                String tagContent = tagMapper.selectOneContent(tagId);
                list1.add(tagContent);
            }
            shopInfoVO.setShopTagsList(list1);
        }
        return new PageDataVO(indexShopInfoVOCount,indexShopInfoVO);
    }

    /**
     * 处理实体店响应实体(距离)
     */
    private List<ShopInfoVO> getShopVO(List<ShopInfo> result, ShopDTO shopDTO) {
        List<ShopInfoVO> container = new ArrayList<>();
        if (result == null) {
            return container;
        }
        result.stream().forEach(shopInfo -> {
            ShopInfoVO vo = new ShopInfoVO();
            vo.setShopId(shopInfo.getShopId());
            vo.setShopName(shopInfo.getShopName());
            vo.setAddress(shopInfo.getAddress());
            vo.setScore(shopInfo.getScore());
            vo.setShopIntroduce(shopInfo.getShopIntroduce());
            // 处理标签列表
            String tagList = shopInfo.getTagList();
            if (tagList != null) {
                String[] split = tagList.split(",");
                List<ShopTags> tagsList = tagMapper.selectList(new LambdaQueryWrapper<ShopTags>()
                        .in(ShopTags::getTagId, split));
                vo.setShopTagsList(tagsList);
            }
            vo.setDistance(calculateDistance(shopDTO, shopInfo));
            container.add(vo);
        });
        return container;
    }

    /**
     * 根据用户定位 以及 商家位置  计算两者距离
     *
     * @param shopDTO  用户经纬度
     * @param shopInfo 商家经纬度
     * @return 00.00km
     */
    private static String calculateDistance(ShopDTO shopDTO, ShopInfo shopInfo) {
        if (shopDTO.getLatitude() == null && shopDTO.getLongitude() == null) {
            return null;
        }
        // 定义两个经纬度点
        // 用户所在的经纬度
        LatLng point1 = new LatLng(shopDTO.getLatitude().doubleValue(), shopDTO.getLongitude().doubleValue());
        // 商家所在的经纬度
        LatLng point2 = new LatLng(shopInfo.getLocalhostLatitude().doubleValue(), shopInfo.getLocalhostLongitude().doubleValue());
        // 计算两点之间的距离（单位：千米）
        double distance = LatLngTool.distance(point1, point2, LengthUnit.KILOMETER);
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String r = decimalFormat.format(distance);

        return r + "km";
    }

    /**
     * 根据实体店id查询实体店信息
     */
    @Override
    public ShopInfoVO getShopInfoById(String shopId) {
        ShopInfoVO shopInfoVO = new ShopInfoVO();
        List<ShopTags> list1=new ArrayList<>();

        ShopInfo shopInfo = shopInfoMapper.selectOne(new QueryWrapper<ShopInfo>()
                .eq("status", 0)
                .eq("shop_id",shopId));

        List<String> taglist = JSON.parseArray(shopInfo.getTagList(), String.class);
        List<String> imagelist = JSON.parseArray(shopInfo.getShopImagList(), String.class);
        for (String s : taglist) {
            ShopTags tagId = tagMapper.selectOne(new QueryWrapper<ShopTags>().eq("tag_id", s));
            list1.add(tagId);
        }
        shopInfoVO.setShopId(shopInfo.getShopId());
        shopInfoVO.setShopName(shopInfo.getShopName());
        shopInfoVO.setLocalhostLatitude(shopInfo.getLocalhostLatitude());
        shopInfoVO.setLocalhostLongitude(shopInfo.getLocalhostLongitude());
        shopInfoVO.setAddress(shopInfo.getAddress());
        shopInfoVO.setScore(shopInfo.getScore());
        shopInfoVO.setShopIntroduce(shopInfo.getShopIntroduce());
        shopInfoVO.setShopTagsList(list1);
        shopInfoVO.setShopImagList(imagelist);
        shopInfoVO.setUserImage(shopInfo.getUserImage());
        return shopInfoVO;

    }

    /**
     * 根据用户id查询用户名下多少间实体店
     */
    @Override
    public Integer getShopNumber(String uid) {
        Long count = shopInfoMapper.selectCount(new QueryWrapper<ShopInfo>()
                                                .eq("uid", uid));
        return Math.toIntExact(count);
    }

    /**
     * 根据用户id查询实体店列表(我的)
     */
    @Override
    public PageDataVO getShopList(UidPageVO uidPageVO) {

        Integer shopNumber = shopInfoMapper.getShopNumber(uidPageVO.getUid());
        try {
            if (uidPageVO.getPage()==0){
                throw new Exception();
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("分页不能为0");
        }
        List<ShopInfoVO1> shopList = shopInfoMapper.getShopList((uidPageVO.getPage()-1)*uidPageVO.getLimit(), uidPageVO.getLimit(), uidPageVO.getUid());
        return new PageDataVO(shopNumber,shopList);
    }

    /**
     * 根据实体店id查询实体店信息(我的)
     */
    @Override
    public ShopInfoVO1 getShopInfoVO(String shopId) {
        ShopInfoVO1 shopInfoVO = shopInfoMapper.getShopInfoVO(shopId);
        return shopInfoVO;
    }

    /**
     * 根据实体店id删除实体店
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteShopInfoById(String shopId) {
        return shopInfoMapper.update(null, new LambdaUpdateWrapper<ShopInfo>()
                .eq(ShopInfo::getShopId, shopId)
                .eq(ShopInfo::getStatus, 0)
                .set(ShopInfo::getStatus, 1)) > 0;
    }

    /**
     * 根据实体店id修改实体店
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateShopInfoById(ShopInfo shopInfo) {
        return shopInfoMapper.update(shopInfo, new LambdaQueryWrapper<ShopInfo>()
                .eq(ShopInfo::getStatus, 0)
                .eq(ShopInfo::getShopId, shopInfo.getShopId())) > 0;
    }



    /**
     * 申请实体店
     * @param applyShopDTO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean applyShop(ApplyShopDTO applyShopDTO) {
        if (!userIdList.add(applyShopDTO.getUid())) {
            throw new BusinessException("请勿重复提交!");
        }
        try {
        // 属性转移
            ShopInfo shopInfo = new ShopInfo(null,StringUtil.generateShortId(),applyShopDTO.getShopName(),
                    applyShopDTO.getLocalhostLatitude(),applyShopDTO.getLocalhostLongitude(),
                    null,applyShopDTO.getPhone(),applyShopDTO.getAddress(),null,
                    applyShopDTO.getShopIntroduce(),JSON.toJSONString(applyShopDTO.getShopImagList()),
                    applyShopDTO.getShopImagList().get(0),applyShopDTO.getCategoryId(),
                    applyShopDTO.getUid(),null,null,applyShopDTO.getUploadCommodityBalance(), applyShopDTO.getQrcode());
        //用户剩余数量减一
            int update = userInfoMapper.update(null, new LambdaUpdateWrapper<UserInfo>()
                    .eq(UserInfo::getUid, applyShopDTO.getUid())
                    .eq(UserInfo::getStatus, 0)
                    .gt(UserInfo::getShopBalance, 0)
                    .setSql("shop_balance=shop_balance-1"));
            if (update > 0) {
                return shopInfoMapper.insert(shopInfo) > 0;
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            userIdList.remove(applyShopDTO.getUid());
        }
    }
    /**
     * 根据一级分类-获取所有实体店列表
     */
    @Override
    public PageDataVO getSecShopInfoListByCondition(Integer limit, Integer pages, String categoryId) {
        Integer n = shopInfoMapper.getAllIndexShopInfoVO(categoryId);
        // stata==1,按评分从低到高；stata==2,按评分从高到低
        List<IndexShopInfoVO> indexShopInfoVO = shopInfoMapper.getIndexShopInfoVOById(categoryId, (pages - 1) * limit, limit);
        // TODO
        for (IndexShopInfoVO shopInfoVO : indexShopInfoVO) {
            List<String> list1 = new ArrayList<>();
            List<String> list = JSON.parseArray(shopInfoVO.getTagList(), String.class);
            if (list == null || list.isEmpty()) {
                continue;
            }
            for (String s : list) {
                String tag_id = tagMapper.selectOneContent(s);
                list1.add(tag_id);
            }

            shopInfoVO.setShopTagsList(list1);
        }
        return new PageDataVO(n,indexShopInfoVO);
    }



    @Override
    public boolean isVipShop(String shopId) {
        int count = shopInfoMapper.selectCount(new QueryWrapper<ShopInfo>()
                .eq("shop_id", shopId)
                .eq("status", 0)
                .eq("membership", 1)).intValue();
        return count == 1;
    }

    @Override
    public List<String> getShopIdListByUid(String uid) {
        return shopInfoMapper.getShopIdListByUid(uid);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean upVipByShopIdList(String shopIds) {
        String[] shopIdArray = shopIds.split(",");
        long count = Arrays.stream(shopIdArray)
                .parallel()
                .peek(shopId -> {
                    shopInfoMapper.update(null, new UpdateWrapper<ShopInfo>()
                            .eq("shop_id", shopId)
                            .eq("status", 0)
                            .set("membership", 1));
                })
                .count();
        return true;
    }

    /**
     * 查询指定实体店还可以上传的商品数量
     */
    @Override
    public Integer updateShopCommodity(String shopId) {
        return shopInfoMapper.updateAllNumber(shopId);
    }

    @Override
    public CheckVO checkId(String qrcode) {
        ShopInfo shopInfo = shopInfoMapper.selectOne(new QueryWrapper<ShopInfo>().eq("qrcode", qrcode)
                .eq("status", 0));
        if (shopInfo == null) {
            return null;
        }
        return new CheckVO(shopInfo.getShopId(), shopInfo.getShopName(), shopInfo.getUserImage());
    }

    @Override
    @Transactional
    public Boolean setShopScore() {
        Integer update = shopInfoMapper.setItemScore();
        return update >0;
    }

    /**
     * 根据条件查询酒店信息
     * @param livePageVO
     * @return
     */
    // TODO
    @Override
    public PageDataVO getHotelInfoByCondition(LivePageVO livePageVO) {
        Integer limit = livePageVO.getLimit();
        Integer page = livePageVO.getPage();

        List<IndexShopInfoVO> indexShopInfoVOS = shopInfoMapper.getHotelInfoByCondition(
                livePageVO.getShopName(),
                limit,
                (page-1)*limit,
                livePageVO.getCheckInTime(),
                livePageVO.getDepartureTime(),
                livePageVO.getAdult(),
                livePageVO.getChildren());
        for (IndexShopInfoVO shopInfoVO : indexShopInfoVOS) {
            List<String> list1 = new ArrayList<>();
            List<String> list = JSON.parseArray(shopInfoVO.getTagList(), String.class);
            if (list == null || list.isEmpty()) {
                continue;
            }
            for (String s : list) {
                String tag_id = tagMapper.selectOneContent(s);
                list1.add(tag_id);
            }

            shopInfoVO.setShopTagsList(list1);
        }

        Integer num = shopInfoMapper.getHotelInfoNum(
                livePageVO.getShopName(),
                livePageVO.getCheckInTime(),
                livePageVO.getDepartureTime(),
                livePageVO.getAdult(),
                livePageVO.getChildren());
        return new PageDataVO(num,indexShopInfoVOS);
    }
}
