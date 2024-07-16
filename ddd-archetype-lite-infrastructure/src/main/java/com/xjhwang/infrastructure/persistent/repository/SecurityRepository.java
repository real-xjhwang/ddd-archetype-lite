package com.xjhwang.infrastructure.persistent.repository;

import com.xjhwang.infrastructure.persistent.dao.*;
import com.xjhwang.infrastructure.persistent.po.SysUser;
import com.xjhwang.security.model.entity.UserEntity;
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
    
    @Override
    public UserEntity getUserByUsername(String username) {
        
        SysUser sysUser = sysUserDao.getUserByUsername(username);
        if (sysUser == null) {
            return null;
        }
        return UserEntity.builder()
            .username(sysUser.getUsername())
            .password(sysUser.getPassword())
            .phone(sysUser.getPhone())
            .email(sysUser.getEmail())
            .build();
    }
}
