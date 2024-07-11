package com.xjhwang.infrastructure.persistent.po;

import com.xjhwang.types.po.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 系统用户角色
 *
 * @author 黄雪杰 on 2024-07-11 14:32
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SysUserRole extends BasePo {
    
    /**
     * 用户ID
     */
    private String userId;
    
    /**
     * 角色ID
     */
    private String roleId;
}
