package com.example.pipayshopapi.mapper;

import com.example.pipayshopapi.entity.ShopCommodityLiveInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pipayshopapi.entity.vo.ShopCommodityLiveInfoListVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 酒店的房型表 Mapper 接口
 * </p>
 *
 * @author zxb
 * @since 2023-08-02
 */
@Mapper
public interface ShopCommodityLiveInfoMapper extends BaseMapper<ShopCommodityLiveInfo> {

    List<ShopCommodityLiveInfoListVO> selectShopCommodityLiveInfoList(String shopId);
}
