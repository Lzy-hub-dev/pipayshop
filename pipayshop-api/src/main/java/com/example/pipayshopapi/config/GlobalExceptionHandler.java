package com.example.pipayshopapi.config;

import com.example.pipayshopapi.entity.vo.ResponseVO;
import com.example.pipayshopapi.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author: jjf
 * @ClassName GlobalExceptionHandler
 * @Description 全局异常处理
 * @date 2023/8/7 19:30
 * @Version 1.0
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseVO handleException(BusinessException e) {
        e.printStackTrace();
        return ResponseVO.getFalseResponseMsg(e.getMessage());
    }
    @ExceptionHandler(Exception.class)
    public ResponseVO baseException(BusinessException e) {
        e.printStackTrace();
        return ResponseVO.getFalseResponseMsg("服务异常，请联系客服");
    }
}
