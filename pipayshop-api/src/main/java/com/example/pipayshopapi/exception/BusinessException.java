package com.example.pipayshopapi.exception;

//import com.namecard.namecardapi.entity.emums.ResponseCodeEnum;


import com.example.pipayshopapi.entity.enums.ResponseCodeEnum;

/**
 * @author wzx
 */
public class BusinessException extends RuntimeException {

    private ResponseCodeEnum codeEnum;

    private Integer code;

    private String message;

    public BusinessException(String message, Throwable e) {
        super(message, e);
        this.message = message;
    }

    public BusinessException(String message) {
        super(message);
        this.message = message;
    }
    public BusinessException() {
        this.message = "服务异常,请联系后台管理人员";
    }

    public BusinessException(Throwable e) {
        super(e);
    }

    public BusinessException(ResponseCodeEnum codeEnum) {
        super(codeEnum.getMsg());
        this.codeEnum = codeEnum;
        this.code = codeEnum.getCode();
        this.message = codeEnum.getMsg();
    }

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public ResponseCodeEnum getCodeEnum() {
        return codeEnum;
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    /**
     * 重写fillInStackTrace 业务异常不需要堆栈信息，提高效率.
     */
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

}
