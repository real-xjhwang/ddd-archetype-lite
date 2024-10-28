package com.xjhwang.domain.security.repository;

import com.xjhwang.domain.security.model.entity.UserEntity;

/**
 * 安全领域仓储
 *
 * @author 黄雪杰 on 2024-07-11 13:58
 */
public interface ISecurityRepository {
    
    /**
     * 根据username获取用户
     *
     * @param username 用户名
     * @return 用户信息
     */
    UserEntity getUserByUsername(String username);
    
    /**
     * 保存用户
     *
     * @param userEntity 用户信息
     */
    void saveSysUser(UserEntity userEntity);
    
    /**
     * 根据 ID 获取用户
     *
     * @param id 主键
     * @return 用户信息
     */
    UserEntity getUserById(String id);
    
    /**
     * 根据 phone 查询用户
     *
     * @param phone 电话
     * @return 用户信息
     */
    UserEntity getUserByPhone(String phone);
}
