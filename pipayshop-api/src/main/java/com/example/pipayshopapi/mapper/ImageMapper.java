package com.example.pipayshopapi.mapper;

import com.example.pipayshopapi.entity.Image;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zxb
 * @since 2023-08-31
 */
public interface ImageMapper extends BaseMapper<Image> {

    @Select("select origin_path from image where image_id = #{imageId}")
    String selectPath(@Param("imageId") String imageId);
}
