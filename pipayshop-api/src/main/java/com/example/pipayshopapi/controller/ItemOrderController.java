package com.example.pipayshopapi.controller;

import com.example.pipayshopapi.entity.ItemOrderInfo;
import com.example.pipayshopapi.entity.vo.*;
import com.example.pipayshopapi.exception.BusinessException;
import com.example.pipayshopapi.service.ItemOrderInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wzx
 */
@Api(value = "网店订单处理接口（最终版）",tags = "网店订单处理接口（最终版）")
@RestController
@RequestMapping("/pipayshopapi/item-order")
@Slf4j
public class ItemOrderController {

    @Resource
    private ItemOrderInfoService itemOrderInfoService;

    /**
     * 查看未支付/已支付/已完成订单列表接口(通过一个标识id来获取对应的状态的列表展示)
     *  标识id -1：所有订单   0：未支付订单    1：已支付订单   2：已完成（已经收货）订单
     */
    @GetMapping("getOrderList")
    @ApiOperation("用户的全部订单列表分页展示标识id -1：所有订单   0：未支付订单    1：已支付订单   2：已完成（已经收货）订单")
    public ResponseVO<List<OrderListVO>> getOrderList(GetOrderDataVO getOrderDataVO) {
        try {
            List<OrderListVO> list = itemOrderInfoService.getOrderList(getOrderDataVO);
            if (list == null){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo(list);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException("用户的订单列表展示数据为0");
        }
    }


    /**
    删除订单接口
     */
    @PostMapping("delOrder/{orderId}")
    @ApiOperation("删除订单接口")
    public ResponseVO<String> delOrderByOrderId(@PathVariable String orderId) {
        try {
            int delete = itemOrderInfoService.delOrderByOrderId(orderId);
            if (delete < 1){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("删除订单成功");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException("删除订单失败，请联系后台人员");
        }
    }


    /**
     订单的详情接口
     */
    @GetMapping("getOrderDetail/{orderId}")
    @ApiOperation("订单（未支付 / 已支付）的详情接口")
    public ResponseVO<OrderDetailVO> getOrderDetail(@PathVariable String orderId) {
        try {
            OrderDetailVO orderDetail = itemOrderInfoService.getOrderDetail(orderId);
            if (orderDetail == null){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo(orderDetail);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException("订单的详情获取失败，请联系后台人员");
        }
    }


    /**
    到货后的订单改为已完成状态接口
     */
    @PostMapping("completedOrder/{orderId}")
    @ApiOperation("订单已完成接口")
    public ResponseVO<String> completedOrder(@PathVariable String orderId) {
        try {
            int update = itemOrderInfoService.completedOrder(orderId);
            if (update < 1){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("收货成功");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException("收货失败，请联系后台人员");
        }
    }


    /**
    订单超时未支付的失效操作
     */
    @PostMapping("failOrder/{orderId}")
    @ApiOperation("订单超时未支付的失效操作接口")
    public ResponseVO<String> failOrder(@PathVariable String orderId) {
        try {
            int update = itemOrderInfoService.failOrder(orderId);
            if (update < 1){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("订单超时未支付导致失效成功");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException("订单超时未支付导致失效失败，请联系后台人员");
        }
    }

    /**
    定时轮询删除失效订单接口
     */
    @PostMapping("deleteFailOrders")
    @ApiOperation("定时轮询删除失效订单接口")
    public ResponseVO<String> deleteFailOrders() {
        try {
            itemOrderInfoService.deleteFailOrders();
            return ResponseVO.getSuccessResponseVo("定时轮询删除失效订单成功");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException("定时轮询删除失效订单失败，请联系后台人员");
        }
    }

    /**
     * 生成未支付订单
     */
    @PostMapping("generateUnpaidOrder")
    @ApiOperation("生成未支付订单")
    public ResponseVO<String> generateUnpaidOrder(@RequestBody ItemOrderInfo itemOrderInfo) {
        try {
            String orderId = itemOrderInfoService.generateUnpaidOrder(itemOrderInfo);
            return ResponseVO.getSuccessResponseVo("生成未支付订单成功" + "订单id为：" + orderId);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException("生成未支付订单失败，请联系后台人员");
        }
    }


    /**
    支付下单接口
     */
    @PostMapping("payOrder")
    @ApiOperation("支付下单接口")
    public ResponseVO<String> payOrder(@RequestBody PayOrderVO payOrderVO) {
        try {
            boolean flag = itemOrderInfoService.payOrder(payOrderVO);
            if (!flag) {
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("支付下单成功!");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException("支付下单失败，请联系后台人员");
        }
    }

    @GetMapping("getMyOrderByUid/{page}/{limit}/{uid}")
    @ApiOperation("根据用户id查询网店的所有订单")
    public ResponseVO<PageDataVO> getMyOrderByUid(@PathVariable Integer page,@PathVariable Integer limit,@PathVariable String uid){
        try {
            PageDataVO myOrderByUid = itemOrderInfoService.getMyOrderByUid(page, limit, uid);
            return ResponseVO.getSuccessResponseVo(myOrderByUid);
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("根据用户id查询网店的所有订单失败，请联系后台人员");
        }
    }

}
