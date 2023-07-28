package com.example.pipayshopapi.service.Impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.entity.ShopInfo;
import com.example.pipayshopapi.entity.ShopTags;
import com.example.pipayshopapi.entity.dto.ShopDTO;
import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.entity.vo.ShopInfoVO;
import com.example.pipayshopapi.mapper.ShopInfoMapper;
import com.example.pipayshopapi.mapper.ShopTagsMapper;
import com.example.pipayshopapi.service.ShopInfoService;
import com.example.pipayshopapi.util.StringUtil;
import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

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

    @Autowired
    private ShopInfoMapper shopInfoMapper;
    @Autowired
    private ShopTagsMapper tagMapper;


    /**
     * 获取实体店列表
     *
     * @return
     */
    @Override
    public PageDataVO getShopInfoList(ShopDTO shopDTO) {
        LambdaQueryWrapper<ShopInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShopInfo::getStatus, 0);
        wrapper.eq(ShopInfo::getCategoryId, shopDTO.getCategorySecId());
        Page<ShopInfo> page = new Page<>(shopDTO.getPageNumber(), shopDTO.getPageSize());
        Page<ShopInfo> info = shopInfoMapper.selectPage(page, wrapper);
        List<ShopInfo> records = info.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            return new PageDataVO();
        }
        // 过滤掉不符合 标签 的实体店
        if (shopDTO.getTagId() != null) {
            records = records.stream().filter(shopInfo -> {
                String tagList = shopInfo.getTagList();
                if (tagList == null) {
                    return false;
                }
                JSONArray objects = JSONObject.parseArray(tagList);
                return objects.contains(shopDTO.getTagId());
            }).collect(Collectors.toList());
        }
        // 过滤掉不符合 一级分类 的实体店
        if (shopDTO.getCategoryTopId() != null) {
            records = records.stream().filter(shopInfo -> {
                String tagList = shopInfo.getTagList();
                if (tagList == null) {
                    return false;
                }
                JSONArray objects = JSONObject.parseArray(tagList);
                return objects.contains(shopDTO.getTagId());
            }).collect(Collectors.toList());
        }
        List<ShopInfoVO> shopVO = getShopVO(records, shopDTO);
        return new PageDataVO(Integer.valueOf(info.getTotal() + ""), shopVO);
    }

    /**
     * 根据条件筛选后获取实体店列表
     * @param limit
     * @param pages
     * @param categoryId
     * @param state
     * @return
     */
    @Override
    public PageDataVO getShopInfoListByCondition(Integer limit, Integer pages, String categoryId, Integer state) {
        Page<ShopInfo> page = new Page<>(pages, limit);
        //stata==1,按评分从低到高；stata==2,按评分从高到低
        shopInfoMapper.selectPage(page,new QueryWrapper<ShopInfo>()
                .eq(!categoryId.equals(0),"category_id",categoryId)
                .orderByAsc(state==1,"score")
                .orderByDesc(state==2,"score"));

        return new PageDataVO((int) page.getTotal(),page.getRecords());
    }

    /**
     * 处理实体店响应实体(距离)
     * @param result
     * @param shopDTO
     * @return
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
        System.out.println("距离： " + r + " km");
        return r + "km";
    }

    /**
     * 根据实体店id查询实体店信息
     */
    @Override
    public ShopInfo getShopInfoById(String shopId) {
        return shopInfoMapper.selectOne(new LambdaQueryWrapper<ShopInfo>()
                .eq(ShopInfo::getStatus, 0)
                .eq(ShopInfo::getShopId, shopId));
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
     * 新增实体店
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addShopInfo(ShopInfo shopInfo) {
        String shortId = StringUtil.generateShortId();
        shopInfo.setShopId(shortId);
        return shopInfoMapper.insert(shopInfo) > 0;
    }


}
