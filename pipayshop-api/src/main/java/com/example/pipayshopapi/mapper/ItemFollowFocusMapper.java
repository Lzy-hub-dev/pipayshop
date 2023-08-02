package com.example.pipayshopapi.mapper;

import com.example.pipayshopapi.entity.ItemFollowFocus;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pipayshopapi.entity.vo.FansVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 粉丝关注表 Mapper 接口
 * </p>
 *
 * @author zxb
 * @since 2023-07-27
 */
@Mapper
public interface ItemFollowFocusMapper extends BaseMapper<ItemFollowFocus> {
    List<FansVO> itemFans(@Param("itemId") String itemId,
                          @Param("pageNum")Integer pageNum,
                          @Param("pageSize")Integer pageSize);
}
