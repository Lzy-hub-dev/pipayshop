package com.example.pipayshopapi.mapper;

import com.example.pipayshopapi.entity.ItemInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pipayshopapi.entity.vo.ItemInfoVO;
import org.apache.ibatis.annotations.Mapper;

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
}
