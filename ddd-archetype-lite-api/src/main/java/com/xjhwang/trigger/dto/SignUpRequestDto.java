package com.xjhwang.trigger.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 黄雪杰 on 2024-07-16 15:04
 */
@Data
public class SignUpRequestDto implements Serializable {
    
    private static final long serialVersionUID = -5638044872503306519L;
    
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 密码
     */
    private String password;
    
    /**
     * 手机号
     */
    private String phone;
    
    /**
     * 邮箱
     */
    private String email;
}
