package com.example.pipayshopapi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class ResponseVO<T> {

    private Integer code;
    private String msg;
    private T data;

    /**
     成功的响应
     */
    public static <T> ResponseVO<T> getSuccessResponseVo(T data){
        return new ResponseVO<>( 200, "请求成功", data);
    }
    /**
     请求成功的信息响应
     */
    public static <T> ResponseVO<T> getSuccessResponseMsg(T data,String msg){
        return new ResponseVO<>( 200, msg,data);
    }

    /**
     失败的响应
     */
    public static <T> ResponseVO<T> getFalseResponseVo(T data){
        return new ResponseVO<>( 500, "请求失败", data);
    }
    /**
     失败的信息响应
     */
    public static  ResponseVO<String> getFalseResponseMsg(String msg){
        return new ResponseVO<>( 500, msg,null);
    }


}


