package com.fang.commonutils;

public class BusinessException extends Exception {
//    封装一个异常类来替代Exception
    private static final long serialVersionUID = 6186374673988783194L;
    private static  Integer code = ResultCode.ERROR;


    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public static Integer getCode() {
        return code;
    }

    public static void setCode(Integer code) {
        BusinessException.code = code;
    }
}