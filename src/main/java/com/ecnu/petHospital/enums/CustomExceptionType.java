package com.ecnu.petHospital.enums;

/**
 * 异常类型统一处理，自己添加状态码和信息
 */
public enum CustomExceptionType {
     INVALID_EMAIL_OR_PASSWORD(101,"无效的邮箱或密码") ,
     INCORRECT_EMAIL_OR_PASSWORD (102,"邮箱或密码错误"),
     EMAIL_ALREADY_EXISTS (103,"邮箱已被注册！"),
     USERNAME_NOT_EXISTS (104,"不存在该用户"),

    DEPARTMENT_ADD_ERROR(105,"科室添加重复"),
    DEPARTMENT_MODIFY_ERROR(106,"科室修改重复"),
    USER_INPUT_ERROR(400,"您输入的数据错误或您没有权限访问资源！"),
    SYSTEM_ERROR (500,"系统出现异常，请您稍后再试或联系管理员！"),
    OTHER_ERROR(999,"系统出现未知异常，请联系管理员！");

    CustomExceptionType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private String desc;//异常类型中文描述

    private int code; //code

    public String getDesc() {
        return desc;
    }

    public int getCode() {
        return code;
    }
}
