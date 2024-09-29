package com.xjhwang.security.service;

import com.xjhwang.security.model.entity.UserEntity;
import com.xjhwang.security.repository.ISecurityRepository;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

/**
 * @author xjhwang on 2024/9/29 23:21
 */
public class ShiroAuthorizingRealm extends AuthorizingRealm {
    
    private final ISecurityRepository securityRepository;
    
    public ShiroAuthorizingRealm(ISecurityRepository securityRepository) {
        
        this.securityRepository = securityRepository;
    }
    
    @Override
    public boolean supports(AuthenticationToken token) {
        
        return token instanceof UsernamePasswordToken;
    }
    
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 只能是不处理权限
        return null;
    }
    
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        
        // 电话
        String phone = (String)token.getPrincipal();
        UserEntity userEntity = securityRepository.getUserByPhone(phone);
        if (userEntity == null) {
            throw new AuthenticationException("用户不存在");
        }
        
        // 加盐
        ByteSource credentialsSalt = ByteSource.Util.bytes(userEntity.getSalt());
        return new SimpleAuthenticationInfo(userEntity, userEntity.getPassword(), credentialsSalt, getName());
    }
}