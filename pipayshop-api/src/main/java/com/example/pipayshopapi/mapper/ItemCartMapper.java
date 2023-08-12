package com.example.pipayshopapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pipayshopapi.entity.ItemCart;
import com.example.pipayshopapi.entity.vo.ItemCartVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zxb
 * @since 2023-07-28
 */
@Mapper
public interface ItemCartMapper extends BaseMapper<ItemCart> {
    /**
     * 根据用户id和商品id查找购物车
     */
    List<ItemCartVO> selectItemCartByIds(@Param("userId") String userId);

    Integer selectItemCartTotal(@Param("userId") String userId);
}
