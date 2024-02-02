package com.example.pipayshopapi.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComplaintDTO {

    // 举报人ID
    private String userId;
    // 关联的商店ID
    private String associatedStoresId;
    // 举报理由
    private String tipOff;
    // 举报类型
    private Integer type;
}
