package com.xjhwang.domain.security.service;

import com.xjhwang.domain.security.model.entity.UserEntity;
import com.xjhwang.domain.security.repository.ISecurityRepository;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.UnauthenticatedException;
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
        // 不处理权限，权限交由业务层处理
        return null;
    }
    
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        
        // 电话
        String phone = (String)token.getPrincipal();
        UserEntity userEntity = securityRepository.getUserByPhone(phone);
        if (userEntity == null) {
            throw new UnauthenticatedException("用户不存在或密码错误");
        }
        
        // 加盐
        ByteSource credentialsSalt = ByteSource.Util.bytes(userEntity.getSalt());
        return new SimpleAuthenticationInfo(userEntity, userEntity.getPassword(), credentialsSalt, getName());
    }
}