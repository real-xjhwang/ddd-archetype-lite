package com.xjhwang.security.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 安全领域用户角色实体
 *
 * @author 黄雪杰 on 2024-07-11 14:34
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleEntity {
    
    /**
     * 用户ID
     */
    private String userId;
    
    /**
     * 角色ID
     */
    private String roleId;
}
