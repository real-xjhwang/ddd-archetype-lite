package com.xjhwang.domain.security.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 黄雪杰 on 2024-07-16 15:03
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpSubjectEntity {
    
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
