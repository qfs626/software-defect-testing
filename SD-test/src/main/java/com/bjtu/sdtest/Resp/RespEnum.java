package com.bjtu.sdtest.Resp;

import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum RespEnum {
    DEFAULT_FAIL(-1,"系统繁忙,请稍后重试"),
    SUCCESS(200,"请求成功"),

    USER_NOT_EXIT(1000,"账号不存在"),
    ACCOUNT_INVALID(1001,"账号或密码错误"),
    USER_ID_NULL(1002,"用户对象或id为空"),
    NAME_OR_PWD_NULL(1003,"用户名或密码为空"),
    USERNAME_HAS_EXISTED(1004,"用户名已存在");
    private Integer code;
    private String message;

    private RespEnum(Integer code, String message){
        this.code =  code;
        this.message = message;
    }
    public static RespEnum getRespEnumByCode(Integer code){
        return Stream.of(RespEnum.values()).filter(t-> t.getCode().equals(code)).findFirst().orElse(null);
    }
}
