package com.fang.servicebase.exceptionhandler;

import com.fang.commonutils.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理类
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    //    指定出现什么异常执行该方法
    @ExceptionHandler(Exception.class)
    @ResponseBody // 为了返回数据
    public R error(Exception e) {
        e.printStackTrace();
        return R.error().message("执行了全局异常处理...");
    }

    //    指定执行了特定ArithmeticException异常处理 精确的异常先处理
//    @ExceptionHandler(Exception.class)
//    @ResponseBody // 为了返回数据
//    public R error(ArithmeticException e) {
//        e.printStackTrace();
//        return R.error().message("执行了特定ArithmeticException异常处理...");
//    }

    //    自定义异常处理 需要手动跑出异常
//    @ExceptionHandler(Exception.class)
//    @ResponseBody // 为了返回数据
//    public R error(GuliException e) {
//        e.printStackTrace();
//        return R.error().code(e.getCode()).message(e.getMsg());
//    }
}
