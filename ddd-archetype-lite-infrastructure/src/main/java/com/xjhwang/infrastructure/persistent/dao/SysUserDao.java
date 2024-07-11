package com.xjhwang.infrastructure.persistent.dao;

import com.xjhwang.infrastructure.persistent.po.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author 黄雪杰 on 2024-07-11 11:44
 */
@Mapper
public interface SysUserDao {
    
    int insert(@Param("sysUser") SysUser sysUser);
    
    int deleteById(@Param("id") String id);
}
