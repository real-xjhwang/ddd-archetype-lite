package com.xjhwang.infrastructure.persistent.dao;

import com.xjhwang.infrastructure.persistent.po.SysUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author 黄雪杰 on 2024-07-11 11:44
 */
@Mapper
public interface SysUserRoleDao {
    
    int insert(@Param("sysUserRole") SysUserRole sysUserRole);
    
    int deleteById(@Param("id") String id);
}
