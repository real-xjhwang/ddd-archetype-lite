package com.xjhwang.security.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 安全领域权限实体
 *
 * @author 黄雪杰 on 2024-07-11 14:23
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PermissionEntity {
    
    /**
     * 权限名称
     */
    private String name;
    
    /**
     * 权限KEY
     */
    private String key;
}
