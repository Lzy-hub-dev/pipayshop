package com.example.pipayshopapi.service;

import com.example.pipayshopapi.entity.TradinPost;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.vo.DealDetailVO;
import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.entity.vo.TraditionDetailVO;
import com.example.pipayshopapi.entity.vo.TraditionListVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wzx
 * @since 2023-09-09
 */
public interface TradinPostService extends IService<TradinPost> {

    boolean publishTradition(String token);

    PageDataVO selectTraditionList(Integer typeId, Integer page, Integer limit);

    TraditionDetailVO selectTraditionDetail(String tradinId) throws InterruptedException;




    List<TraditionListVO> selectTradinPostByUid(String token);







}
