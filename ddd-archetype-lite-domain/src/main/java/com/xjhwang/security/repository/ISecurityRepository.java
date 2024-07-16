package com.xjhwang.security.repository;

import com.xjhwang.security.model.entity.UserEntity;

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
}
