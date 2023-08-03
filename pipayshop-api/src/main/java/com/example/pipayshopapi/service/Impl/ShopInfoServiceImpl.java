package com.example.pipayshopapi.service.Impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.entity.ShopInfo;
import com.example.pipayshopapi.entity.ShopTags;
import com.example.pipayshopapi.entity.dto.ApplyShopDTO;
import com.example.pipayshopapi.entity.dto.ShopDTO;
import com.example.pipayshopapi.entity.vo.*;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.mapper.ShopInfoMapper;
import com.example.pipayshopapi.mapper.ShopTagsMapper;
import com.example.pipayshopapi.service.ShopInfoService;
import com.example.pipayshopapi.util.FileUploadUtil;
import com.example.pipayshopapi.util.StringUtil;
import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

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
     * 根据条件筛选后获取实体店列表
     */
    @Override
    public List<IndexShopInfoVO> getShopInfoListByCondition(Integer limit, Integer pages, String categoryId, Integer state) {

        // stata==1,按评分从低到高；stata==2,按评分从高到低
        List<IndexShopInfoVO> indexShopInfoVO = shopInfoMapper.getIndexShopInfoVO(categoryId, (pages - 1) * limit, limit,state);
        return indexShopInfoVO;
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
        System.out.println("距离： " + r + " km");
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
        Integer page = uidPageVO.getPage();
        try {
            if (page==0){
                throw new Exception();
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("分页不能为0");
        }
        Integer limit = uidPageVO.getLimit()*page;
        int pages = page - 1;
        List<ShopInfoVO1> shopList = shopInfoMapper.getShopList(pages, limit, uidPageVO.getUid());
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
     * @param file
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean applyShop(ApplyShopDTO applyShopDTO, MultipartFile[] file) {
        // 创建一个集合存储商品图片
        List<String> imagesList = new ArrayList<>();
        for (MultipartFile multipartFile : file) {
            // 获取存储到本地空间并返回图片url
            imagesList.add(FileUploadUtil.uploadFile(multipartFile,FileUploadUtil.SHOP_IMG));
        }
        // 将list集合转为string
        String jsonString = JSON.toJSONString(imagesList);
        // 属性转移
        ShopInfo shopInfo = new ShopInfo();
        shopInfo.setShopId(StringUtil.generateShortId());
        shopInfo.setShopImagList(jsonString);
        shopInfo.setLocalhostLatitude(applyShopDTO.getLocalhostLatitude());
        shopInfo.setLocalhostLongitude(applyShopDTO.getLocalhostLongitude());
        shopInfo.setShopName(applyShopDTO.getShopName());
        shopInfo.setPhone(applyShopDTO.getPhone());
        shopInfo.setUid(applyShopDTO.getUid());
        shopInfo.setCategoryId(applyShopDTO.getCategoryId());
        shopInfo.setShopIntroduce(applyShopDTO.getShopIntroduce());
        return shopInfoMapper.insert(shopInfo) > 0;
    }
    /**
     * 根据条件筛选后获取实体店列表
     *
     * @param limit
     * @param pages
     * @param categoryId
     * @param state
     * @return
     */
    @Override
    public List<IndexShopInfoVO> getSecShopInfoListByCondition(Integer limit, Integer pages, String categoryId, Integer state) {
        // stata==1,按评分从低到高；stata==2,按评分从高到低
        List<IndexShopInfoVO> indexShopInfoVO = shopInfoMapper.getIndexShopInfoVO(categoryId, (pages - 1) * limit, limit,state);
        return indexShopInfoVO;

    }

}
