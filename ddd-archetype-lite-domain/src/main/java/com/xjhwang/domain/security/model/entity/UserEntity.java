package com.xjhwang.domain.security.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 安全领域用户实体
 *
 * @author 黄雪杰 on 2024-07-11 13:57
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    
    /**
     * 用户ID
     */
    private String id;
    
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 密码
     */
    private String password;
    
    /**
     * 盐值
     */
    private String salt;
    
    /**
     * 手机号
     */
    private String phone;
    
    /**
     * 邮箱
     */
    private String email;
}
