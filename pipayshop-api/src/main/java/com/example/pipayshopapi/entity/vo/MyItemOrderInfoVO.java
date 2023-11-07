package com.example.pipayshopapi.entity.vo;

import com.example.pipayshopapi.entity.dto.ItemOrderDetailDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author wzx
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyItemOrderInfoVO {
    /**
     * 订单id
     */
    private String orderId;

    /**
     * 网店id
     */
    private String itemId;

    /**
     * 网店名称
     */
    private String itemName;
    /**
     * 网店头像
     */
    private String userImage;

    /**
     * 商品数据
     */
    private List<ItemOrderDetailDTO> commodityList;

    /**
     * 交易总金额
     */
    private BigDecimal transactionAmount;

    /**
     * 交易pi总金额
     */
    private BigDecimal piAmount;


    /**
     * 优惠后的总价
     */
     private BigDecimal discount;

    /**
     * 0:待支付1:已支付2：已完成3：无效订单
     */
    private Integer orderStatus;

}
