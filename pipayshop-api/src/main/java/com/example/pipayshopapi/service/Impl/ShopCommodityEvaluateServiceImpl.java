package com.example.pipayshopapi.service.Impl;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.pipayshopapi.entity.ShopCommodityEvaluate;
import com.example.pipayshopapi.entity.dto.EvaluateDTO;
import com.example.pipayshopapi.entity.vo.ShopCommodityEvaluateVO;
import com.example.pipayshopapi.mapper.ShopCommodityEvaluateMapper;
import com.example.pipayshopapi.service.ShopCommodityEvaluateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    @Autowired
    private ShopCommodityEvaluateMapper shopCommodityEvaluateMapper;
    /**
     * 实体店-商品-评论列表
     *
     * @param commodityId
     * @return
     */
    @Override
    public List<ShopCommodityEvaluateVO> commodityEvaluateList(String commodityId,Integer pageNum,Integer pageSize) {
        return shopCommodityEvaluateMapper.commodityEvaluateList(commodityId,pageNum-1,pageSize);
    }

    /**
     * 实体店-商品-添加评论
     *
     * @param evaluate
     * @return
     */
    @Override
    public Boolean addEvaluate(ShopCommodityEvaluate evaluate) {
        evaluate.setEvaluateId(StringUtil.generateShortId());
        evaluate.setStatus(false);
        return shopCommodityEvaluateMapper.insert(evaluate)>0;
    }

    /**
     * 实体店-商品-删除评论
     *
     * @return
     */
    @Override
    public Boolean deleteEvaluate(String evaluateId) {
        return shopCommodityEvaluateMapper.update(null, new LambdaUpdateWrapper<ShopCommodityEvaluate>()
                .eq(ShopCommodityEvaluate::getEvaluateId, evaluateId)
                .set(ShopCommodityEvaluate::getStatus, true)
        ) > 0;
    }
}
