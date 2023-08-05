package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.entity.ShopEvaluate;
import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.entity.vo.ShopEvaluateVO;
import com.example.pipayshopapi.mapper.ShopEvaluateMapper;
import com.example.pipayshopapi.service.ShopEvaluateService;
import com.example.pipayshopapi.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 网店评价 服务实现类
 * </p>
 *
 * @author zxb
 * @since 2023-07-29
 */
@Service
public class ShopEvaluateServiceImpl extends ServiceImpl<ShopEvaluateMapper, ShopEvaluate> implements ShopEvaluateService {

    @Resource
    private ShopEvaluateMapper shopEvaluateMapper;

    /**
     * 根据实体店id获取实体店评价列表
     */
    @Override
    public PageDataVO getShopEvaluateListByItemId(Integer page, Integer limit, String shopId) {
        List<ShopEvaluateVO> list = shopEvaluateMapper.selectPageByShopId((page - 1) * limit, limit, shopId);
        int count = shopEvaluateMapper.selectPageByShopIdCount(shopId);
        return new PageDataVO(count, list);
    }


    /**
     * 增加实体店评价
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addShopEvaluate(ShopEvaluate shopEvaluate) {
        shopEvaluate.setEvaluateId(StringUtil.generateShortId());
        int result = shopEvaluateMapper.insert(shopEvaluate);
        return result>0;
    }


    /**
     * 删除实体店评价列表
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteShopEvaluate(String evaluateId, String userId) {
        int result = shopEvaluateMapper.update(null, new UpdateWrapper<ShopEvaluate>()
                .eq("evaluate_id", evaluateId)
                .eq("user_id", userId)
                .set("status", 1));
        return result>0;
    }
}
