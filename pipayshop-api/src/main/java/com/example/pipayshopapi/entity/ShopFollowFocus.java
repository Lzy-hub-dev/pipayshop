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
 * 粉丝关注表
 * </p>
 *
 * @author jjf
 * @since 2023-07-29
 */
@TableName("shop_follow_focus")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShopFollowFocus implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 实体店id
     */
    private String shopId;

    /**
     * 粉丝id（用户id）
     */
    private String followId;

    /**
     * 关注时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 关注状态(0关注 1取消)
     */
    private Integer status;

}
