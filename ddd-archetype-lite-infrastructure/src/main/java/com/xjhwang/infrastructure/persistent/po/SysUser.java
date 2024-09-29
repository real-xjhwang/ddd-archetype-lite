package com.xjhwang.infrastructure.persistent.po;

import com.xjhwang.types.po.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 系统用户
 *
 * @author 黄雪杰 on 2024-07-11 11:29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SysUser extends BasePo {
    
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
