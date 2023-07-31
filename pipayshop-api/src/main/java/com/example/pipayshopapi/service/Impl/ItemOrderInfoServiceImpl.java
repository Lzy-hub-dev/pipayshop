package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.pipayshopapi.entity.ItemOrderInfo;
import com.example.pipayshopapi.entity.vo.GetOrderDataVO;
import com.example.pipayshopapi.entity.vo.ItemOrderInfoVO;
import com.example.pipayshopapi.entity.vo.OrderDetailVO;
import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.mapper.ItemOrderInfoMapper;
import com.example.pipayshopapi.service.ItemOrderInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
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
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int completedOrder(String orderId) {
        return itemOrderInfoMapper.update(null, new UpdateWrapper<ItemOrderInfo>()
                .eq("order_id", orderId)
                .set("order_status", 2)
                .set("update_time", new Date()));
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
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int failOrder(String orderId) {
        return itemOrderInfoMapper.update(null, new UpdateWrapper<ItemOrderInfo>()
                .eq("order_id", orderId)
                .set("order_status", 3)
                .set("update_time", new Date()));
    }
    /**
     * 根据卖家id查询网店关联的订单
     * @param userId 卖家id
     * @param orderStatus 0:待支付;2:已完成;3:查询所有
     * @return
     */
    @Override
    public List<ItemOrderInfoVO> itemOrders(String userId, Integer orderStatus) {
        return itemOrderInfoMapper.selectItemOrdersBySellerId(userId,orderStatus);
    }

    @Override
    // TODO 这里的返回数据是不正确的。因该再特定一点
    public PageDataVO getOrderList(GetOrderDataVO getOrderDataVO) {
        Integer currentPage =getOrderDataVO.getCurrentPage();
        Integer pageSize = getOrderDataVO.getPageSize();
        Page<ItemOrderInfo> page = new Page<>(currentPage, pageSize);
        itemOrderInfoMapper.selectPage(page, new QueryWrapper<ItemOrderInfo>()
                .eq("uid", getOrderDataVO.getUserId())
                .eq("order_status", getOrderDataVO.getCategoryId())
                .eq("del_flag", 0));
        return new PageDataVO((int)page.getTotal(), page.getRecords());
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delOrderByOrderId(String orderId) {
        if (orderId == null || "".equals(orderId)){
            return 0;
        }
        return itemOrderInfoMapper.update(null, new UpdateWrapper<ItemOrderInfo>()
                .eq("order_id", orderId)
                .set("del_flag", 1)
                .set("update_time", new Date()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFailOrders() {
        itemOrderInfoMapper.update(null, new UpdateWrapper<ItemOrderInfo>()
                .eq("order_status", 3)
                .set("del_flag", 1)
                .set("update_time", new Date()));
    }



    @Override
    public OrderDetailVO getOrderDetail(String orderId) {
        return itemOrderInfoMapper.getOrderDetail(orderId);
    }

}
