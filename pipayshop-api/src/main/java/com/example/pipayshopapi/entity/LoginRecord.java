package com.example.pipayshopapi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author zxb
 * @since 2023-08-14
 */
@TableName("login_record")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户ip
     */
    private String ip;

    /**
     * 国家地区
     */
    private String region;

    /**
     * 登录时间
     */
    private Date loginTime;

    public LoginRecord(String userId, String ip, String region, Date loginTime){
        this.userId=userId;
        this.ip=ip;
        this.region=region;
        this.loginTime=loginTime;
    }

}
