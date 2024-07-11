package com.xjhwang.infrastructure.persistent.dao;

import com.xjhwang.infrastructure.persistent.po.SysPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author 黄雪杰 on 2024-07-11 14:21
 */
@Mapper
public interface SysPermissionDao {
    
    int insert(@Param("sysPermission") SysPermission sysPermission);
    
    int deleteById(@Param("id") String id);
}
