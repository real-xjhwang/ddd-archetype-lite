package com.xjhwang.infrastructure.persistent.repository;

import com.xjhwang.infrastructure.persistent.dao.*;
import com.xjhwang.security.repository.ISecurityRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author 黄雪杰 on 2024-07-11 14:02
 */
@Repository
public class SecurityRepository implements ISecurityRepository {
    
    @Resource
    private SysUserDao sysUserDao;
    
    @Resource
    private SysRoleDao sysRoleDao;
    
    @Resource
    private SysPermissionDao sysPermissionDao;
    
    @Resource
    private SysUserRoleDao sysUserRoleDao;
    
    @Resource
    private SysRolePermissionDao sysRolePermissionDao;
}
