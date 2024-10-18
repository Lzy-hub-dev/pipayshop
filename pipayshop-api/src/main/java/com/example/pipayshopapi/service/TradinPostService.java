package com.example.pipayshopapi.service;

import com.example.pipayshopapi.entity.TradinPost;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.vo.DealDetailVO;
import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.entity.vo.TraditionDetailVO;
import com.example.pipayshopapi.entity.vo.TraditionListVO;
import org.springframework.transaction.annotation.Transactional;

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

    //业务员的批量发布
    boolean publishTradition(String token);


    @Transactional(rollbackFor = Exception.class)
    boolean batchPublishTradition(String token, int total);

    TraditionDetailVO selectTraditionDetail(String tradinId) throws InterruptedException;




    List<TraditionListVO> selectTradinPostByUid(String token);


    PageDataVO selectTraditionListByPiName(Integer typeId, Integer page, Integer limit, String piName);

    //范围查询
    PageDataVO selectTraditionScopeListByPiName(Integer typeId, Integer page, Integer limit,
                                                String piName,String start,String end);

    void cancelTradition(String tradinId, String piName);

}
