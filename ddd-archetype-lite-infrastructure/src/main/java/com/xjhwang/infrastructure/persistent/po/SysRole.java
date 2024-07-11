package com.xjhwang.infrastructure.persistent.po;

import com.xjhwang.types.po.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 系统角色
 *
 * @author 黄雪杰 on 2024-07-11 13:53
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SysRole extends BasePo {
    
    /**
     * 角色名
     */
    private String name;
}
