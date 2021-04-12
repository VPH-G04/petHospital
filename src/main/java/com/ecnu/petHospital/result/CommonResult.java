package com.ecnu.petHospital.result;

import com.ecnu.petHospital.enums.CustomExceptionType;
import com.ecnu.petHospital.exception.CustomException;

public class CommonResult {
    public static final int SUCCESS = 200;

    public static final int FAILED = 500;

    public static final int UNAUTHORIZED = 401;

    public static final int ACCESS_DENIED = 402;

    public static <T> Result<T> success() {
        return new Result<>(SUCCESS, "成功");
    }

    public static <T> Result<T> failed() {
        return new Result<>(FAILED, "失败");
    }

    public static <T> Result<T> unauthorized() {
        return new Result<>(UNAUTHORIZED, "用户未认证");
    }

    public static <T> Result<T> accessDenied() {
        return new Result<>(ACCESS_DENIED, "没有权限访问");
    }

    public static <T> Result<T> error(CustomExceptionType customExceptionType,
                                      String errorMessage){
        return new Result<>(customExceptionType.getCode(),errorMessage);

    }

    public static <T> Result<T> error(CustomException customException){
        return new Result<>(customException.getCode(),customException.getMessage());

    }
}