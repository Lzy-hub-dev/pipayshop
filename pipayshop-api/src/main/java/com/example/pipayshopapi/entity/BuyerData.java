package com.example.pipayshopapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 网店商品收货地址
 * </p>
 *
 * @author zxb
 * @since 2023-07-31
 */
@TableName("buyer_data")
public class BuyerData implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 收货数据id
     */
    private String buyerDataId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 收货人名字
     */
    private String userName;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 0:正常1逻辑删除2：绝对删除
     */
    private Boolean delFlag;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 默认地址( 0：非默认 1：默认地址)
     */
    private Integer isDefault;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getBuyerDataId() {
        return buyerDataId;
    }

    public void setBuyerDataId(String buyerDataId) {
        this.buyerDataId = buyerDataId;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public Boolean getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    @Override
    public String toString() {
        return "BuyerData{" +
            "id=" + id +
            ", buyerDataId=" + buyerDataId +
            ", userId=" + userId +
            ", userName=" + userName +
            ", phone=" + phone +
            ", address=" + address +
            ", delFlag=" + delFlag +
            ", createTime=" + createTime +
            ", isDefault=" + isDefault +
        "}";
    }
}
