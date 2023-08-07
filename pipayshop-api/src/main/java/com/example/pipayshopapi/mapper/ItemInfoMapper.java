package com.example.pipayshopapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pipayshopapi.entity.ItemInfo;
import com.example.pipayshopapi.entity.vo.ItemInfoVO;
import com.example.pipayshopapi.entity.vo.ItemListVO;
import com.example.pipayshopapi.entity.vo.ItemMinInfoVo;
import com.example.pipayshopapi.entity.vo.ItemVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

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

    List<ItemInfoVO> selectItemInfoByItemIdOrUserId(@Param("userId") String userId, @Param("itemId")String itemId);

    List<ItemListVO> selectFollowItemByUserId(@Param("userId")String userId, @Param("page")Integer page,@Param("limit")Integer limit);
    Integer selectAllFollowItemByUserId(@Param("userId") String userId);

    ItemMinInfoVo getItemInfoByUid(@Param("uid")String userId);

    ItemVO selectBasicData(@Param("itemId") String itemId);

    @Select("select upload_balance from item_info where item_id = #{itemId}")
    Integer getUploadBalance(@Param("itemId")String itemId);
}
