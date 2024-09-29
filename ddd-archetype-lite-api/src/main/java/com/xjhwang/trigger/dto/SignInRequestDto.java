package com.xjhwang.trigger.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 黄雪杰 on 2024-07-16 11:25
 */
@Data
public class SignInRequestDto implements Serializable {
    
    private static final long serialVersionUID = -1521636986946587258L;
    
    /**
     * 电话
     */
    private String phone;
    
    /**
     * 密码
     */
    private String password;
}
