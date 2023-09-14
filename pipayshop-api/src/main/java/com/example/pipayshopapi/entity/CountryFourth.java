package com.example.pipayshopapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author wzx
 * @since 2023-09-13
 */
@TableName("country_fourth")
public class CountryFourth implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 四级行政区域的标识id
     */
    private String countryFourthId;

    /**
     * 四级行政区域的名字
     */
    private String name;

    /**
     * 父级分类的外键
     */
    private String pidId;

    /**
     * 0:正常1：逻辑删除
     */
    private Boolean delFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getCountryFourthId() {
        return countryFourthId;
    }

    public void setCountryFourthId(String countryFourthId) {
        this.countryFourthId = countryFourthId;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getPidId() {
        return pidId;
    }

    public void setPidId(String pidId) {
        this.pidId = pidId;
    }
    public Boolean getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return "CountryFourth{" +
            "id=" + id +
            ", countryFourthId=" + countryFourthId +
            ", name=" + name +
            ", pidId=" + pidId +
            ", delFlag=" + delFlag +
        "}";
    }
}
