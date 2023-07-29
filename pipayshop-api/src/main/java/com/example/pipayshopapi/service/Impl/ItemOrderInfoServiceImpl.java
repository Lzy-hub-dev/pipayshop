package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.pipayshopapi.entity.ItemOrderInfo;
import com.example.pipayshopapi.entity.vo.ItemOrderInfoVO;
import com.example.pipayshopapi.mapper.ItemOrderInfoMapper;
import com.example.pipayshopapi.service.ItemOrderInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 商户订单数据表 服务实现类
 * </p>
 *
 * @author zxb
 * @since 2023-07-28
 */
@Service
public class ItemOrderInfoServiceImpl extends ServiceImpl<ItemOrderInfoMapper, ItemOrderInfo> implements ItemOrderInfoService {

    @Resource
    private ItemOrderInfoMapper itemOrderInfoMapper;

    /**
     * 获取用户网店订单列表
     * @param userId
     * @return
     */
    @Override
    public List<ItemOrderInfoVO> selectUserItemOrders(String userId) {
        List<ItemOrderInfoVO> itemOrderInfoVOS = itemOrderInfoMapper.selectItemOrders(userId);
        return  itemOrderInfoVOS;
    }

    /**
     * 逻辑删除网店用户订单
     * @param orderId
     * @return
     */
    @Override
    public Boolean deleteUserItemOrder(String orderId) {
        int update = itemOrderInfoMapper.update(null, new UpdateWrapper<ItemOrderInfo>()
                                                                .eq("order_id", orderId)
                                                                .set("del_flag", 1));
        return update > 0;
    }

    /**
     * 根据用户id查询 订单列表
     *
     * @param userId
     * @return
     */
    @Override
    public List<ItemOrderInfoVO> selectOrderByUerId(String userId) {
        return itemOrderInfoMapper.selectOrderByUerId(userId);
    }
}
