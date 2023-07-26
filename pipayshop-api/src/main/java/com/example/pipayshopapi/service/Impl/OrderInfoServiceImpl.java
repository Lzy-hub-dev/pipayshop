package com.example.pipayshopapi.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.pipayshopapi.entity.OrderInfo;
import com.example.pipayshopapi.entity.vo.OrderPageVO;
import com.example.pipayshopapi.entity.vo.PageDataVO;
import com.example.pipayshopapi.mapper.OrderInfoMapper;
import com.example.pipayshopapi.service.OrderInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pipayshopapi.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 订单数据表 服务实现类
 * </p>
 *
 * @author nws
 * @since 2023-07-25
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {


    @Resource
    private OrderInfoMapper orderInfoMapper;


    /**
     * 用户的全部订单列表分页展示
     * @param orderPageVO
     * @return
     */
    @Override
    public PageDataVO OrderList(OrderPageVO orderPageVO) {

        //设置分页参数
        Page<OrderInfo> page=new Page<>(orderPageVO.getPage(),orderPageVO.getLimit());

        //查询分页数据封装到page中
        orderInfoMapper.selectPage(page,new QueryWrapper<OrderInfo>()
                                            .eq("uid",orderPageVO.getUid())
                                            .eq("del_flag",0));

        //封装数据
        return new PageDataVO((int)page.getTotal(),page.getRecords());

    }

    /**
     * 用户的不同状态的订单列表的分页展示
     * @param orderPageVO
     * @return
     */
    @Override
    public PageDataVO OrderListByStatus(OrderPageVO orderPageVO) {


        Page<OrderInfo> page=new Page<>(orderPageVO.getPage(),orderPageVO.getLimit());

        orderInfoMapper.selectPage(page,new QueryWrapper<OrderInfo>()
                                            .eq("uid",orderPageVO.getUid())
                                            .eq("del_flag",0)
                                            .eq("order_status",orderPageVO.getStatus()));

        return new PageDataVO((int)page.getTotal(),page.getRecords());

    }

    /**
     * 订单生成 (未支付状态)
     * @param orderInfo 订单实体
     * @return 布尔值
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addOrder(OrderInfo orderInfo) {

       String orderId = StringUtil.generateShortId();
       orderInfo.setOrderId(orderId);

       int result = orderInfoMapper.insert(orderInfo);
       return result > 0;

    }

    /**
     * 支付订单
     * @param orderId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean payOrder(String orderId) {
       int result = orderInfoMapper.update(null,new UpdateWrapper<OrderInfo>()
                                                           .eq("order_id",orderId)
                                                           .set("order_status",1));
        return result > 0;
    }

    /**
     * 完成订单
     * @param orderId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean finishOrder(String orderId) {
        int result = orderInfoMapper.update(null,new UpdateWrapper<OrderInfo>()
                                                            .eq("order_id",orderId)
                                                            .set("order_status",2));
        return result > 0;
    }

    /**
     * 订单删除
     * @param orderId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean logicDeleteOrder(String orderId) {
        int result = orderInfoMapper.update(null,new UpdateWrapper<OrderInfo>()
                                                            .eq("order_id",orderId)
                                                            .set("del_flag",1));
        return result > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean realDeleteOrder(String orderId) {
        int result = orderInfoMapper.update(null,new UpdateWrapper<OrderInfo>()
                                                            .eq("order_id",orderId)
                                                            .set("del_flag",2));
        return result > 0;
    }


}
