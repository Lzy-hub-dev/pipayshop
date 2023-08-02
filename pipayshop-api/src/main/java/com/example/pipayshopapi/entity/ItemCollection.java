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
 * 网店商品收藏表	
 * </p>
 *
 * @author jjf
 * @since 2023-07-29
 */
@TableName("item_collection")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemCollection implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 收藏id
     */
    private String collectionId;

    /**
     * 商品id
     */
    private String commodityId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 收藏时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 收藏状态(0收藏 1取消)
     */
    private Boolean status;

}
