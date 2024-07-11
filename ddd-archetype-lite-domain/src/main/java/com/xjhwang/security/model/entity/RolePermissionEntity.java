package com.xjhwang.security.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 安全领域角色权限实体
 *
 * @author 黄雪杰 on 2024-07-11 14:49
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RolePermissionEntity {
    
    /**
     * 角色ID
     */
    private String roleId;
    
    /**
     * 权限ID
     */
    private String permissionId;
}
