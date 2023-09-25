package com.example.pipayshopapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.ws.soap.Addressing;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author wzx
 * @since 2023-09-09
 */
@TableName("tradin_post")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradinPost implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 交易id
     */
    private String tradinId;

    /**
     * 发布者uid
     */
    private String publisherUid;



    /**
     * 0：换积分 1：换pi币
     */
    private Integer typeId;

    /**
     * 发布者说明
     */
    private String content;

    /**
     * 交换积分
     */
    private BigDecimal pointBalance;

    /**
     * 交易pi币
     */
    private BigDecimal piBalance;

    /**
     * pi钱包地址
     */
    private String piAddress;



    /**
     * 订单id
     */
    private String orderId;

    /**
     * 0：发布 1：有人查看 2：交易进行中 3：交易完成 4:取消
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    public TradinPost(String tradinId, String publisherUid, Integer typeId, String content, BigDecimal pointBalance, BigDecimal piBalance) {
        this.tradinId = tradinId;
        this.publisherUid = publisherUid;
        this.typeId = typeId;
        this.content = content;
        this.pointBalance = pointBalance;
        this.piBalance = piBalance;

    }

    public TradinPost(String tradinId, String publisherUid, Integer typeId, String content, BigDecimal pointBalance, BigDecimal piBalance, String piAddress) {
        this.tradinId = tradinId;
        this.publisherUid = publisherUid;
        this.typeId = typeId;
        this.content = content;
        this.pointBalance = pointBalance;
        this.piBalance = piBalance;
        this.piAddress = piAddress;

    }


}
