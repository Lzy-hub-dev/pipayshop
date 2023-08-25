package com.example.pipayshopapi.entity.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
public class ResponseResultVO {

    private Integer code;
    private String msg;
    private Map<String,Object> data;
    public ResponseResultVO(Integer code, String msg, HashMap<String, Object> data) {
        this.code=code;
        this.msg=msg;
        this.data=data;

    }
}
