package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.entity.ShopCommodityEvaluate;
import com.example.pipayshopapi.entity.ShopOrderInfo;
import com.example.pipayshopapi.entity.dto.ShopCommodityEvaluateDTO;
import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.entity.vo.ShopCommodityEvaluateVO;
import com.example.pipayshopapi.mapper.ShopCommodityEvaluateMapper;
import com.example.pipayshopapi.mapper.ShopCommodityInfoMapper;
import com.example.pipayshopapi.mapper.ShopOrderInfoMapper;
import com.example.pipayshopapi.service.ShopCommodityEvaluateService;
import com.example.pipayshopapi.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zxb
 * @since 2023-07-27
 */
@Service
public class ShopCommodityEvaluateServiceImpl extends ServiceImpl<ShopCommodityEvaluateMapper, ShopCommodityEvaluate> implements ShopCommodityEvaluateService {

    @Resource
    private ShopCommodityEvaluateMapper shopCommodityEvaluateMapper;

    @Resource
    ShopCommodityInfoMapper shopCommodityInfoMapper;

    @Resource
    ShopOrderInfoMapper shopOrderInfoMapper;
    /**
     * 实体店-商品-评论列表
     */
    @Override
    public PageDataVO commodityEvaluateList(String commodityId, Integer pageNum, Integer pageSize) {
        List<ShopCommodityEvaluateVO> result = shopCommodityEvaluateMapper.commodityEvaluateList(commodityId, (pageNum-1)*pageSize, pageSize);
        Long count = shopCommodityEvaluateMapper.selectCount(new QueryWrapper<ShopCommodityEvaluate>()
                .eq("commodity_id", commodityId)
                .eq("status", 0));
        return new PageDataVO(count.intValue(), result);
    }

    /**
     * 实体店-商品-添加评论
     */
    @Override
    public Boolean addEvaluate(ShopCommodityEvaluateDTO dto) {
        // 查询当前实体店商品所在的网店id
        String shopId = shopCommodityInfoMapper.selectShopIdByCommodityId(dto.getCommodityId());
        ShopCommodityEvaluate evaluate = new ShopCommodityEvaluate();
        BeanUtils.copyProperties(dto, evaluate);
        evaluate.setItemId(shopId);
        evaluate.setEvaluateId(StringUtil.generateShortId());
        evaluate.setStatus(false);
        int update = shopOrderInfoMapper.update(null, new UpdateWrapper<ShopOrderInfo>()
                .eq("order_id", dto.getOrderId())
                .set("order_status", 4));
        if (update < 1){throw new RuntimeException();}
        return shopCommodityEvaluateMapper.insert(evaluate)>0;

    }

    /**
     * 实体店-商品-删除评论
     */
    @Override
    public Boolean deleteEvaluate(String evaluateId, String userId) {
        return shopCommodityEvaluateMapper.update(null, new LambdaUpdateWrapper<ShopCommodityEvaluate>()
                .eq(ShopCommodityEvaluate::getEvaluateId, evaluateId)
                .eq(ShopCommodityEvaluate::getUserId, userId)
                .set(ShopCommodityEvaluate::getStatus, true)
        ) > 0;
    }
}
