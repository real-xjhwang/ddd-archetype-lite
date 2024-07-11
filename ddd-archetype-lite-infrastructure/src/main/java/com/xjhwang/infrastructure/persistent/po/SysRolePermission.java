package com.xjhwang.infrastructure.persistent.po;

import com.xjhwang.types.po.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 系统角色权限
 *
 * @author 黄雪杰 on 2024-07-11 14:45
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SysRolePermission extends BasePo {
    
    /**
     * 角色ID
     */
    private String roleId;
    
    /**
     * 权限ID
     */
    private String permissionId;
}
