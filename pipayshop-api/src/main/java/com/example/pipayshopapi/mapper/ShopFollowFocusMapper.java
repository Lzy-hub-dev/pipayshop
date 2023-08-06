package com.example.pipayshopapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pipayshopapi.entity.ShopFollowFocus;
import com.example.pipayshopapi.entity.vo.ShopInfoVO1;
import com.example.pipayshopapi.entity.vo.ShopUserFollowInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 粉丝关注表 Mapper 接口
 * </p>
 *
 * @author jjf
 * @since 2023-07-29
 */
@Mapper
public interface ShopFollowFocusMapper extends BaseMapper<ShopFollowFocus> {


    List<ShopUserFollowInfoVO> shopFollowFocusList(@Param("shopId") String shopId,
                                                   @Param("pageNum") Integer pageNum,
                                                   @Param("pageSize") Integer pageSize);

    /**
     * 根据用户id-查询-关注的实体店-列表
     * @param page
     * @param limit
     * @param userId
     * @return list<shopInfo>
     */
    List<ShopInfoVO1> selectFollowProductByUserId(@Param("page") Integer page, @Param("limit") Integer limit, @Param("userId") String userId);

    Integer selectAllFollowProductByUserId (@Param("userId") String userId);

    Long getCount(String shopId);
}
