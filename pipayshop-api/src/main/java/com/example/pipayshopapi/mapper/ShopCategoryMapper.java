package com.example.pipayshopapi.mapper;

import com.example.pipayshopapi.entity.ShopCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pipayshopapi.entity.vo.ShopCategoryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 实体店二级分类表 Mapper 接口
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@Mapper
public interface ShopCategoryMapper extends BaseMapper<ShopCategory> {

    /**
     * 查找二级分类的id和内容的集合
     * @param categoryPid
     * @return
     */
    List<ShopCategoryVO> selectAllContentList(@Param("category_pid") String categoryPid);

}
