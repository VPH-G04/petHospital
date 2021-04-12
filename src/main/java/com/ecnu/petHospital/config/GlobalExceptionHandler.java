package com.ecnu.petHospital.config;

import com.ecnu.petHospital.enums.CustomExceptionType;
import com.ecnu.petHospital.exception.CustomException;
import com.ecnu.petHospital.result.CommonResult;
import com.ecnu.petHospital.result.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public Result<?> customerException(CustomException e) {
        if(e.getCode() == CustomExceptionType.SYSTEM_ERROR.getCode()){
            //400异常不需要持久化，将异常信息以友好的方式告知用户就可以
            //TODO 将500异常信息持久化处理，方便运维人员处理
        }
        return CommonResult.error(e);
    }

    //捕获验证异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Result<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return  CommonResult.error(CustomExceptionType.USER_INPUT_ERROR, Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
    }

    @ExceptionHandler(BindException.class)
    @ResponseBody
    public Result<?> handleBindException(BindException e) {
        return  CommonResult.error(CustomExceptionType.USER_INPUT_ERROR, Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());

    }

    //处理程序员在程序中未能捕获（遗漏的）异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result<?> exception(Exception e) {
        //TODO 将异常信息持久化处理，方便运维人员处理

        return CommonResult.error(new CustomException(
                CustomExceptionType.OTHER_ERROR));
    }


}
