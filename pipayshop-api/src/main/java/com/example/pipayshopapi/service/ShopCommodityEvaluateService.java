package com.example.pipayshopapi.service;

import com.example.pipayshopapi.entity.ShopCommodityEvaluate;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.pipayshopapi.entity.dto.EvaluateDTO;
import com.example.pipayshopapi.entity.vo.ShopCommodityEvaluateVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zxb
 * @since 2023-07-27
 */
public interface ShopCommodityEvaluateService extends IService<ShopCommodityEvaluate> {

    /**
     * 实体店-商品-评论列表
     * @param commodityId
     * @return
     */
    List<ShopCommodityEvaluateVO> commodityEvaluateList(String commodityId,Integer pageNum,Integer pageSize);

    /**
     * 实体店-商品-添加评论
     * @param evaluate
     * @return
     */
    Boolean addEvaluate(ShopCommodityEvaluate evaluate);

    /**
     * 实体店-商品-删除评论
     * @return
     */
    Boolean deleteEvaluate(String evaluateId);
}
