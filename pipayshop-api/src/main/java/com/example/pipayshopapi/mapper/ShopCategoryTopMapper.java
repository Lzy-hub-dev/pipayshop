package com.example.pipayshopapi.mapper;

import com.example.pipayshopapi.entity.ShopCategoryTop;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pipayshopapi.entity.vo.ShopCategoryVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 实体店一级分类表 Mapper 接口
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@Mapper
public interface ShopCategoryTopMapper extends BaseMapper<ShopCategoryTop> {

    /**
     * 查找一级分类的id和内容的集合
     * @return
     */
       List<ShopCategoryVO> selectAllTopContentList();

}
