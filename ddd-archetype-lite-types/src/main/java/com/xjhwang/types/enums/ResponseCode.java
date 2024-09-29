package com.xjhwang.types.enums;

import lombok.Getter;

/**
 * 异常码
 *
 * @author 黄雪杰 on 2024-06-11 16:39
 */
@Getter
public enum ResponseCode {
    
    SUCCESS("0000", "成功"),
    UNKNOWN_ERROR("0001", "未知失败"),
    ILLEGAL_PARAMETER("0002", "非法参数"),
    INDEX_DUP("0003", "唯一索引冲突"),
    UNAUTHORIZED("0004", "无权访问"),
    
    USERNAME_OR_PASSWORD_INVALID("10001", "用户名或密码错误"),
    USERNAME_IS_BLANK("10002", "用户名为空"),
    PASSWORD_IS_BLANK("10003", "密码为空"),
    USER_ALREADY_EXISTS("10004", "用户已存在"),
    PHONE_IS_BLANK("10005", "电话为空"),
    ;
    
    private final String code;
    
    private final String info;
    
    ResponseCode(String code, String info) {
        
        this.code = code;
        this.info = info;
    }
}
