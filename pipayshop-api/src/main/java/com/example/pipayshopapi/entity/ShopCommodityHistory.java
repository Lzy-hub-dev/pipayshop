package com.example.pipayshopapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 实体店用户商品历史记录表
 * </p>
 *
 * @author zxb
 * @since 2023-07-29
 */
@TableName("shop_commodity_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopCommodityHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 服务id
     */
    private String commodityId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 历史查看时间
     */
    private LocalDateTime createTime;

    public ShopCommodityHistory(String commodityId, String userId) {
        this.commodityId = commodityId;
        this.userId = userId;
    }
}
