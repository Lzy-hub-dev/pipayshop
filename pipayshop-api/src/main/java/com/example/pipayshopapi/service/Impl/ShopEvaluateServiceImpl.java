package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.pipayshopapi.entity.ShopEvaluate;
import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.mapper.ShopEvaluateMapper;
import com.example.pipayshopapi.service.ShopEvaluateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

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
    public PageDataVO getShopEvaluateListByItemId(Integer page, Integer limit, String itemId) {
        Page<ShopEvaluate> pages = new Page<>(page,limit);
        shopEvaluateMapper.selectPage(pages,new QueryWrapper<ShopEvaluate>()
                                            .eq("shop_id",itemId));
        return new PageDataVO((int) pages.getTotal(),pages.getRecords());
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
