package com.xjhwang.infrastructure.persistent.dao;

import com.xjhwang.infrastructure.persistent.po.SysRolePermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author 黄雪杰 on 2024-07-11 11:44
 */
@Mapper
public interface SysRolePermissionDao {
    
    int insert(@Param("sysRolePermission") SysRolePermission sysRolePermission);
    
    int deleteById(@Param("id") String id);
}
