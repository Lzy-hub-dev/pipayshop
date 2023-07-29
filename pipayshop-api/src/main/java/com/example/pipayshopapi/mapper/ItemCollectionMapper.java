package com.example.pipayshopapi.mapper;

import com.example.pipayshopapi.entity.ItemCollection;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 网店商品收藏表	 Mapper 接口
 * </p>
 *
 * @author jjf
 * @since 2023-07-29
 */
@Mapper
public interface ItemCollectionMapper extends BaseMapper<ItemCollection> {

}
