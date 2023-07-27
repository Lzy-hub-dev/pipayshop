package com.example.pipayshopapi.mapper;

import com.example.pipayshopapi.entity.BrandInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pipayshopapi.entity.vo.BrandInfoVO;
import com.example.pipayshopapi.entity.vo.ShopCategoryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author nws
 * @since 2023-07-26
 */
@Mapper
public interface BrandInfoMapper extends BaseMapper<BrandInfo> {
    /**
     * 查找二级分类的id查找品牌的集合
     * @param cateId
     * @return
     */
    List<BrandInfoVO> selectAllContentList(@Param("cate_id") String cateId);


    /**
     * 网店获取品牌的集合
     * @return
     */
    List<BrandInfoVO> itemSelectAllContentList();

}
