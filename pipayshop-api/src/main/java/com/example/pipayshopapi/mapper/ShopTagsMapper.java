package com.example.pipayshopapi.mapper;

import com.example.pipayshopapi.entity.ShopTags;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 实体店的标签 Mapper 接口
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@Mapper
public interface ShopTagsMapper extends BaseMapper<ShopTags> {

    String selectOneContent(@Param("tagId") String tagId);
}
