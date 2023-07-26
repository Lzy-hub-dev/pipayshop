package com.example.pipayshopapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author nws
 * @since 2023-07-26
 */
@TableName("brand_info")
public class BrandInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 品牌id
     */
    private String bId;

    /**
     * 二级分类的id(外键)
     */
    private Integer cateId;

    /**
     * 品牌
     */
    private String brand;

    /**
     * 0:启用 1：逻辑删除 2：删除
     */
    private Boolean delFlag;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getbId() {
        return bId;
    }

    public void setbId(String bId) {
        this.bId = bId;
    }
    public Integer getCateId() {
        return cateId;
    }

    public void setCateId(Integer cateId) {
        this.cateId = cateId;
    }
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
    public Boolean getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "BrandInfo{" +
            "id=" + id +
            ", bId=" + bId +
            ", cateId=" + cateId +
            ", brand=" + brand +
            ", delFlag=" + delFlag +
            ", createTime=" + createTime +
        "}";
    }
}
