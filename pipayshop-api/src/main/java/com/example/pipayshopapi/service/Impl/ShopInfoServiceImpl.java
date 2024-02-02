package com.example.pipayshopapi.service.Impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.entity.*;
import com.example.pipayshopapi.entity.dto.*;
import com.example.pipayshopapi.entity.vo.*;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.mapper.*;
import com.example.pipayshopapi.service.ShopInfoService;
import com.example.pipayshopapi.util.*;
import com.google.common.collect.Sets;
import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.*;
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

    @Resource
    private ShopInfoMapper shopInfoMapper;

    @Resource
    private ShopTagsMapper tagMapper;
    @Resource
    private UserInfoMapper userInfoMapper;

    private final static Set<String> USER_ID_LIST = Sets.newConcurrentHashSet();

    @Resource
    private BUserInfoMapper bUserInfoMapper;

    @Resource
    private ImageMapper imageMapper;

    @Resource
    private CountrySecondMapper countrySecondMapper;

    @Resource
    private CountryThirdMapper countryThirdMapper;

    @Resource
    private ShopRegionMapper shopRegionMapper;

    @Resource
    private CountryFourthMapper countryFourthMapper;
    private final RedisUtil<CountryMinVO> redisUtil = new RedisUtil<>();

    @Override
    public String getShopCodeByShopId(String shopId) {
        return shopInfoMapper.getShopCodeByShopId(shopId);
    }

    /**
     * 首页获取所有实体店列表
     */
    @Override
    public PageDataVO getShopInfoListByCondition(ShopInfoListByConditionDTO shopInfoListByConditionDTO) {

        if (StringUtils.isBlank(shopInfoListByConditionDTO.getRegionId())) {
            throw new BusinessException("数据异常，regionId不能为空");}

        // 获取总条数
        Integer count = shopInfoMapper.getIndexShopInfoVOCount(shopInfoListByConditionDTO.getCategoryId(), shopInfoListByConditionDTO.getRegionId(),shopInfoListByConditionDTO.getShopName());
        List<IndexShopInfoVO> indexShopInfoVO = shopInfoMapper.getIndexShopInfoVO(shopInfoListByConditionDTO.getCategoryId(),
                (shopInfoListByConditionDTO.getPages() - 1) * shopInfoListByConditionDTO.getLimit(),
                                                                shopInfoListByConditionDTO.getLimit(),
                                                                shopInfoListByConditionDTO.getScore(),
                                                                shopInfoListByConditionDTO.getRegionId(),
                                                                shopInfoListByConditionDTO.getShopName());
        indexShopInfoVO
                .stream()
                .parallel()
                .forEach(shopInfoVO -> {
                    List<String> list1 = new ArrayList<>();
                    List<String> list = JSON.parseArray(shopInfoVO.getTagList(), String.class);
                    if (list==null || list.isEmpty()){
                        return;
                    }
                    for (String tagId : list) {
                        String tagContent = tagMapper.selectOneContent(tagId);
                        list1.add(tagContent);
                    }
                    shopInfoVO.setShopTagsList(list1);
                });
        return new PageDataVO(count, indexShopInfoVO);
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
        ShopInfo shopInfo = shopInfoMapper.selectOne(new QueryWrapper<ShopInfo>()
                .eq("status", 0)
                .eq("shop_id",shopId));

        BeanUtils.copyProperties(shopInfo, shopInfoVO);
        // 解析imageList数据
        List<String> imageList = JSON.parseArray(shopInfo.getShopImagList(), String.class);
        List<String> images = imageList.stream()
                                     .parallel()
                .map(imageId -> imageMapper.selectPath(imageId))
                .collect(Collectors.toList());
        // 解析userImage数据
        shopInfoVO.setShopImagList(images);
        shopInfoVO.setUserImage(imageMapper.selectPath(shopInfoVO.getUserImage()));
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
        return shopInfoMapper.getShopInfoVO(shopId);
    }

    /**
     * 根据实体店id上下架实体店
     */
    @Override
    public void deleteShopInfoById(String shopId, Integer status) {
        if (status == null) {throw new BusinessException("状态不能为空");}
        if (status == 0) {
            // 上架
            int update = shopInfoMapper.update(null, new UpdateWrapper<ShopInfo>()
                    .eq("shop_id", shopId)
                    .eq("status", 1)
                    .set("status", status));
            if (update == 0) {throw new BusinessException("未查找到店铺，或该店铺已上架");}
            return;
        }else if (status == 1) {
            // 下架
            int update = shopInfoMapper.update(null, new UpdateWrapper<ShopInfo>()
                    .eq("shop_id", shopId)
                    .eq("status", 0)
                    .set("status", status));
            if (update == 0) {throw new BusinessException("未查找到店铺，或该店铺已下架");}
            return;
        }
        throw new BusinessException("状态不正确");
    }

    /**
     * 根据实体店id修改实体店
     */
    @Override
    public void updateShopInfoById(ShopInfoDTO shopInfoDTO) {
        int i = shopInfoMapper.updateShopInfoById(shopInfoDTO);
        if (i == 0) {throw new BusinessException("修改失败");}
    }



    /**
     * 申请实体店
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void applyShop(ApplyShopDTO applyShopDTO) {
        if (applyShopDTO.getShopImagList() == null || applyShopDTO.getShopImagList().size() == 0) {
            throw new BusinessException("实体店图片不能为空");
        }

        if (!USER_ID_LIST.add(applyShopDTO.getUid())) {
            throw new BusinessException("请勿重复提交!");
        }
        try {
            String uid = applyShopDTO.getUid();
            UserInfo userInfo = userInfoMapper.selectOne(new QueryWrapper<UserInfo>()
                    .eq("uid", uid)
                    .eq("status", 0));
            if (userInfo == null){
                throw new BusinessException("申请实体店失败");
            }
            // 看该用户是否是vip用户，如果是就将他绑定的这家实体店直接升级为vip店铺
            Integer membership = userInfo.getLevel();
            // 属性转移
            ShopInfo shopInfo = new ShopInfo();
            BeanUtils.copyProperties(applyShopDTO, shopInfo);
            String shopId = StringUtil.generateShortId();
            shopInfo.setShopId(shopId);
            List<String> shopImagList = applyShopDTO.getShopImagList();
            shopInfo.setShopImagList(JSON.toJSONString(shopImagList.subList(1, shopImagList.size())));
            shopInfo.setUserImage(shopImagList.get(0));
            shopInfo.setUid(uid);
            shopInfo.setMembership(membership);
            shopInfo.setPiratio(applyShopDTO.getPiratio());
            log.error("shopinfo======================================"+shopInfo);
            // 新增实体店
            int insert = shopInfoMapper.insert(shopInfo);
            log.error("insert======================================"+insert);
            if (insert < 1){
                throw new BusinessException("申请实体店失败");
            }
            log.error("================================================error");
            // 用户可绑定实体店剩余数量减一
            int update = userInfoMapper.update(null, new LambdaUpdateWrapper<UserInfo>()
                    .eq(UserInfo::getUid, applyShopDTO.getUid())
                    .eq(UserInfo::getStatus, 0)
                    .gt(UserInfo::getShopBalance, 0)
                    .setSql("shop_balance=shop_balance-1"));
            if (update < 1) {
                throw new BusinessException("申请实体店失败");
            }
            // 将当前店铺根据市级id来进行划分，存入实体店分区表
            ShopRegion shopRegion = new ShopRegion(null, applyShopDTO.getRegionId(), shopId, null, null);
            int insert1 = shopRegionMapper.insert(shopRegion);
            if (insert1 < 1) {
                throw new BusinessException("申请实体店失败");
            }
            // 如果是第一次绑定实体店的话要给他分配一个B端账号
            int shopSum = shopInfoMapper.selectCount(new QueryWrapper<ShopInfo>()
                    .eq("uid", uid)).intValue();
            if (shopSum == 0) {
                // 注册一个B端账号
                Date date = new Date();
                BUserInfo bUserInfo = new BUserInfo(null, userInfo.getPiName(), userInfo.getPiName()
                        , date, date, date, 0);
                int insert2 = bUserInfoMapper.insert(bUserInfo);
                if (insert2 < 1){
                    throw new BusinessException("申请实体店失败");
                }
            }
        } catch (Exception e) {
            throw new BusinessException("申请实体店失败");
        } finally {
            USER_ID_LIST.remove(applyShopDTO.getUid());
        }
    }
    /**
     * 根据一级分类-获取所有实体店列表
     */
    @Override
    public PageDataVO getSecShopInfoListByCondition(SecShopInfoListByConditionDTO secShopInfoListByConditionDTO) {
        Integer count = shopInfoMapper.getAllIndexShopInfoVO(secShopInfoListByConditionDTO.getCategoryId(), secShopInfoListByConditionDTO.getRegionId(),secShopInfoListByConditionDTO.getShopName());
        // stata==1,按评分从低到高；stata==2,按评分从高到低
        List<IndexShopInfoVO> indexShopInfoVO = shopInfoMapper.getIndexShopInfoVOById(secShopInfoListByConditionDTO.getCategoryId(),
                             (secShopInfoListByConditionDTO.getPages() - 1) * secShopInfoListByConditionDTO.getLimit(),
                                secShopInfoListByConditionDTO.getLimit(),
                                secShopInfoListByConditionDTO.getRegionId(),secShopInfoListByConditionDTO.getShopName());
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
        return new PageDataVO(count,indexShopInfoVO);
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
                .peek(shopId -> {
                    shopInfoMapper.update(null, new UpdateWrapper<ShopInfo>()
                            .eq("shop_id", shopId)
                            .eq("status", 0)
                            .set("membership", 1));
                }).count();
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
    public Boolean setShopScore() {
        Integer update = shopInfoMapper.setItemScore();
        return update >0;
    }

    /**
     * 根据条件查询酒店信息
     */
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
                livePageVO.getChildren(),
                livePageVO.getRegionId());
        for (IndexShopInfoVO indexShopInfoVO : indexShopInfoVOS) {
            indexShopInfoVO.setUserImage(imageMapper.selectPath(indexShopInfoVO.getUserImage()));
        }
        for (IndexShopInfoVO shopInfoVO : indexShopInfoVOS) {
            List<String> list1 = new ArrayList<>();
            List<String> list = JSON.parseArray(shopInfoVO.getTagList(), String.class);
            if (list == null || list.isEmpty()) {
                continue;
            }
            for (String s : list) {
                String tagId = tagMapper.selectOneContent(s);
                list1.add(tagId);
            }

            shopInfoVO.setShopTagsList(list1);
        }

        Integer num = shopInfoMapper.getHotelInfoNum(
                livePageVO.getShopName(),
                livePageVO.getCheckInTime(),
                livePageVO.getDepartureTime(),
                livePageVO.getAdult(),
                livePageVO.getChildren(),livePageVO.getRegionId());
        return new PageDataVO(num,indexShopInfoVOS);
    }


    @Override
    public String shopTopImageUp(MultipartFile multipartFile) {
        List<String> imageSizeList = new ArrayList<>();
        imageSizeList.add(ImageConstants.SHOP_TOP_IMAGE_UP_SMALL);
        return FileUploadUtil.allUploadImageData(multipartFile, imageMapper, FileUploadUtil.SHOP_TOP_IMAGE_UP,imageSizeList);
    }

    @Override
    public String shopImageUp(MultipartFile multipartFile) {
        List<String> imageSizeList = new ArrayList<>();
        imageSizeList.add(ImageConstants.SHOP_IMAGE_UP_BIG);
        return FileUploadUtil.allUploadImageData(multipartFile, imageMapper, FileUploadUtil.SHOP_IMAGE_UP,imageSizeList);
    }

    @Override
    public List<CountryMinVO> getSecondDistrictList(String countryCode) {
        // 直接走缓存拿
        List<CountryMinVO> dataListFromRedisList = redisUtil.getDataListFromRedisList(Constants.COUNTRY_SECOND_REGION + "_" + countryCode);
        // 校验缓存结果
        if (dataListFromRedisList == null || dataListFromRedisList.size() == 0) {
            // 刷新缓存
            dataListFromRedisList = countrySecondMapper.getSecondDistrictList(countryCode);
            if (dataListFromRedisList == null || dataListFromRedisList.size() == 0){return null;}
            redisUtil.savaDataListToRedisList(Constants.COUNTRY_SECOND_REGION + "_" + countryCode, dataListFromRedisList);
        }
        return dataListFromRedisList;
    }

    @Override
    public List<CountryMinVO> getThirdDistrictList(String countrySecondId) {
        // 直接走缓存拿
        List<CountryMinVO> dataListFromRedisList = redisUtil.getDataListFromRedisList(Constants.COUNTRY_THIRD_REGION + "_" + countrySecondId);
        // 校验缓存结果
        if (dataListFromRedisList == null || dataListFromRedisList.size() == 0) {
            // 刷新缓存
            dataListFromRedisList = countryThirdMapper.getThirdDistrictList(countrySecondId);
            if (dataListFromRedisList == null || dataListFromRedisList.size() == 0){return null;}
            redisUtil.savaDataListToRedisList(Constants.COUNTRY_THIRD_REGION + "_" + countrySecondId, dataListFromRedisList);
        }
        return dataListFromRedisList;
    }

    @Override
    public List<CountryMinVO> getFourthDistrictList(String countryThirdId) {
        // 直接走缓存拿
        List<CountryMinVO> dataListFromRedisList = redisUtil.getDataListFromRedisList(Constants.COUNTRY_FOURTH_REGION + "_" + countryThirdId);
        // 校验缓存结果
        if (dataListFromRedisList == null || dataListFromRedisList.size() == 0) {
            // 刷新缓存
            dataListFromRedisList = countryFourthMapper.getFourthDistrictList(countryThirdId);
            if (dataListFromRedisList == null){return null;}
            redisUtil.savaDataListToRedisList(Constants.COUNTRY_THIRD_REGION + "_" + countryThirdId, dataListFromRedisList);
        }
        return dataListFromRedisList;    }
}
