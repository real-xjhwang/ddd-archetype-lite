package com.xjhwang.domain.security.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.AuthenticationStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import java.util.Collection;

/**
 * @author xjhwang on 2024/9/29 23:21
 */
@Slf4j
public class MultiRealmAuthenticator extends ModularRealmAuthenticator {
    
    @Override
    protected AuthenticationInfo doMultiRealmAuthentication(Collection<Realm> realms, AuthenticationToken token) {
        
        AuthenticationStrategy authenticationStrategy = getAuthenticationStrategy();
        AuthenticationInfo aggregate = authenticationStrategy.beforeAllAttempts(realms, token);
        
        AuthenticationException throwsException = null;
        for (Realm realm : realms) {
            aggregate = authenticationStrategy.beforeAttempt(realm, token, aggregate);
            if (realm.supports(token)) {
                AuthenticationInfo info = null;
                try {
                    info = realm.getAuthenticationInfo(token);
                } catch (AuthenticationException e) {
                    throwsException = e;
                    log.error(e.getMessage(), e);
                }
                
                aggregate = authenticationStrategy.afterAttempt(realm, token, info, aggregate, throwsException);
            }
        }
        
        if (throwsException != null) {
            throw throwsException;
        }
        return authenticationStrategy.afterAllAttempts(token, aggregate);
    }
}