package com.xjhwang.domain.security.service;

import com.xjhwang.domain.security.model.entity.JwtAuthenticationToken;
import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 携带Token时校验
 *
 * @author 黄雪杰 on 2024-07-11 15:55
 */
public class JwtAuthorizingRealm extends AuthorizingRealm {
    
    private final JwtProvider jwtProvider;
    
    public JwtAuthorizingRealm(JwtProvider jwtProvider) {
        
        this.jwtProvider = jwtProvider;
    }
    
    @Override
    public Class<?> getAuthenticationTokenClass() {
        
        return JwtAuthenticationToken.class;
    }
    
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 不处理权限，权限交由业务层处理
        return null;
    }
    
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 获取凭证
        String jwt = (String)token.getCredentials();
        // 解析凭证
        Claims claims = jwtProvider.decode(jwt);
        // 获取用户电话
        String subject = claims.getSubject();
        return new SimpleAuthenticationInfo(subject, token.getCredentials(), getName());
    }
}
