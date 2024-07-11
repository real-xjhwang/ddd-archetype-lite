package com.xjhwang.infrastructure.persistent.po;

import com.xjhwang.types.po.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 系统权限
 *
 * @author 黄雪杰 on 2024-07-11 14:20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SysPermission extends BasePo {
    
    /**
     * 权限名称
     */
    private String name;
    
    /**
     * 权限KEY
     */
    private String key;
}
