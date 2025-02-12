package com.xjhwang.types.enums;

import lombok.Getter;

/**
 * 异常码
 *
 * @author 黄雪杰 on 2024-06-11 16:39
 */
@Getter
public enum ResponseCode {
    
    SUCCESS("00000", "成功"),
    UNKNOWN_ERROR("00001", "未知失败"),
    ILLEGAL_PARAMETER("00002", "非法参数"),
    INDEX_DUP("00003", "唯一索引冲突"),
    UNAUTHENTICATED("00004", "认证失败"),
    UNAUTHORIZED("00005", "无权访问"),
    TOKEN_SIGNATURE_INVALID("00006", "Token 签名无效"),
    TOKEN_EXPIRED("00007", "Token 过期"),
    TOKEN_INVALID("00008", "无效Token"),
    
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
