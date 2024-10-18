package com.example.pipayshopapi.mapper;

import com.example.pipayshopapi.entity.TradinPost;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.pipayshopapi.entity.vo.DealDetailVO;
import com.example.pipayshopapi.entity.vo.TraditionDetailVO;
import com.example.pipayshopapi.entity.vo.TraditionListVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wzx
 * @since 2023-09-09
 */
@Mapper
public interface TradinPostMapper extends BaseMapper<TradinPost> {

    List<TraditionListVO> selectTraditionList(Integer typeId,Integer page,Integer limit);

    TraditionDetailVO selectTraditionDetail(String tradinId);

    List<TraditionListVO> selectTradinPostByUid(String userId);

    Integer selectTraditionListCount(Integer typeId);


    List<TraditionListVO> selectTraditionListByPiName(Integer typeId, Integer page, Integer limit, String piName);
    //范围分页
    List<TraditionListVO> selectTraditionScopeListByPiName(Integer typeId, Integer page,
                                                           Integer limit, String piName,
                                                           String start,String end);

    Integer selectCountTraditionList(Integer typeId, String piName);

    //范围选择统计
    Integer selectCountTraditionScopeList(Integer typeId, String piName,String start,String end);

}
