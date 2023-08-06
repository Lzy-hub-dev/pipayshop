package com.example.pipayshopapi.controller;


import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zxb
 * @since 2023-08-06
 */
@Slf4j
@Api(value = "记录用户充值店铺和商品的数量订单",tags = "记录用户充值店铺和商品的数量订单")
@RestController
@RequestMapping("/pipayshopapi/recharge-permissions-order")
public class RechargePermissionsOrderController {

    @Resource
    RechargePermissionsOrderService rechargePermissionsOrderService;
    /**
     * 下单提高一个用户可绑定的实体店余额数
     */
    @PostMapping("addShopSum")
    @ApiOperation("下单提高一个用户可绑定的实体店余额数")
    public ResponseVO<String> addShopSum(RechargeVO rechargeVO) {
        try {
            boolean flag = rechargePermissionsOrderService.addShopSum(rechargeVO);
            if (!flag){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("下单提高一个用户可绑定的实体店余额数成功");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException("下单提高一个用户可绑定的实体店余额数失败，请联系后台人员");
        }
    }
    @Resource
    private RechargePermissionsOrderService rechargePermissionsOrderService;

    @PostMapping("updateUploadBalanceInfo")
    @ApiOperation("更新网店可添加商品余额信息")
    public ResponseVO updateUploadBalanceInfo(@RequestBody RechargePermissionsOrderVO rechargePermissionsOrderVO){
        try {
            Boolean result = rechargePermissionsOrderService.updateUploadBalanceInfo(rechargePermissionsOrderVO);
            if(result == false){
                throw new Exception();
            }
            return ResponseVO.getSuccessResponseVo("更新成功");
        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException("更新网店可添加商品余额信息失败，请联系后台人员");
        }
    }

}
