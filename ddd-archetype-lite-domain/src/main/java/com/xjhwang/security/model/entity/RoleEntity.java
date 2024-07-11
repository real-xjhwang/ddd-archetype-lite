package com.xjhwang.security.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 安全领域角色实体
 *
 * @author 黄雪杰 on 2024-07-11 13:57
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleEntity {
    
    /**
     * 角色名
     */
    private String name;
}
