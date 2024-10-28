package com.xjhwang.infrastructure.persistent.repository;

import com.xjhwang.infrastructure.persistent.dao.*;
import com.xjhwang.infrastructure.persistent.po.SysUser;
import com.xjhwang.domain.security.model.entity.UserEntity;
import com.xjhwang.domain.security.repository.ISecurityRepository;
import com.xjhwang.types.enums.ResponseCode;
import com.xjhwang.types.exception.ApplicationException;
import com.xjhwang.types.util.IdUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

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
    
    @Resource
    private TransactionTemplate transactionTemplate;
    
    @Override
    public UserEntity getUserByUsername(String username) {
        
        SysUser sysUser = sysUserDao.getUserByUsername(username);
        if (sysUser == null) {
            return null;
        }
        return UserEntity.builder()
            .username(sysUser.getUsername())
            .password(sysUser.getPassword())
            .salt(sysUser.getSalt())
            .phone(sysUser.getPhone())
            .email(sysUser.getEmail())
            .build();
    }
    
    @Override
    public void saveSysUser(UserEntity userEntity) {
        
        SysUser sysUser = new SysUser();
        sysUser.setId(IdUtils.getSnowflakeNextIdStr());
        sysUser.setUsername(userEntity.getUsername());
        sysUser.setPassword(userEntity.getPassword());
        sysUser.setPhone(userEntity.getPhone());
        sysUser.setEmail(userEntity.getEmail());
        transactionTemplate.execute(status -> {
            try {
                int affectedRows = sysUserDao.insert(sysUser);
                if (affectedRows <= 0) {
                    throw new ApplicationException(ResponseCode.UNKNOWN_ERROR);
                }
            } catch (DuplicateKeyException duplicateKeyException) {
                throw new ApplicationException(ResponseCode.INDEX_DUP, duplicateKeyException);
            }
            return status;
        });
    }
    
    @Override
    public UserEntity getUserById(String id) {
        
        SysUser sysUser = sysUserDao.getUserById(id);
        if (sysUser == null) {
            return null;
        }
        return UserEntity.builder()
            .id(sysUser.getId())
            .username(sysUser.getUsername())
            .password(sysUser.getPassword())
            .salt(sysUser.getSalt())
            .phone(sysUser.getPhone())
            .email(sysUser.getEmail())
            .build();
    }
    
    @Override
    public UserEntity getUserByPhone(String phone) {
        
        SysUser sysUser = sysUserDao.getUserByPhone(phone);
        if (sysUser == null) {
            return null;
        }
        return UserEntity.builder()
            .id(sysUser.getId())
            .username(sysUser.getUsername())
            .password(sysUser.getPassword())
            .salt(sysUser.getSalt())
            .phone(sysUser.getPhone())
            .email(sysUser.getEmail())
            .build();
    }
}
