package com.xjhwang.infrastructure.persistent.dao;

import com.xjhwang.infrastructure.persistent.po.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author 黄雪杰 on 2024-07-11 14:06
 */
@Mapper
public interface SysRoleDao {
    
    int insert(@Param("sysRole") SysRole sysRole);
    
    int deleteById(@Param("id") String id);
}
