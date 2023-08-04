package com.example.pipayshopapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pipayshopapi.entity.ItemInfo;
import com.example.pipayshopapi.entity.vo.ItemInfoVO;
import com.example.pipayshopapi.entity.vo.ItemMinInfoVo;
import com.example.pipayshopapi.entity.vo.ItemVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zxb
 * @since 2023-07-27
 */
@Mapper
public interface ItemInfoMapper extends BaseMapper<ItemInfo> {
    ItemInfoVO getItemInfo(String commodityId);

    List<ItemInfoVO> selectItemInfoByItemIdOrUserId(@Param("userId") String userId, @Param("itemId")String itemId);

    List<ItemInfo> selectFollowItemByUserId(String userId);

    ItemMinInfoVo getItemInfoByUid(@Param("uid")String userId);

    ItemVO selectBasicData(@Param("itemId") String itemId);
}
