package com.xjhwang.domain.security.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;

/**
 * @author xjhwang on 2024/7/15 22:20
 */
@Slf4j
public class JwtCredentialsMatcher implements CredentialsMatcher {
    
    private final JwtProvider jwtProvider;
    
    public JwtCredentialsMatcher(JwtProvider jwtProvider) {
        
        this.jwtProvider = jwtProvider;
    }
    
    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
        
        String token = (String)authenticationToken.getCredentials();
        try {
            // 验证token正确性
            jwtProvider.verify(token);
        } catch (Exception e) {
            log.error("Token - 验证不通过", e);
            return false;
        }
        return true;
    }
}
