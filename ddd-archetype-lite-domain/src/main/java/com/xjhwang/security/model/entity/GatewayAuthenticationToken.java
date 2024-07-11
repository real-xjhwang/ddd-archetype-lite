package com.xjhwang.security.model.entity;

import lombok.AllArgsConstructor;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * 网关鉴权Token
 *
 * @author 黄雪杰 on 2024-07-11 16:00
 */
@AllArgsConstructor
public class GatewayAuthenticationToken implements AuthenticationToken {
    
    private static final long serialVersionUID = 8801335997925942737L;
    
    /**
     * 请求唯一ID
     */
    private final String uid;
    
    /**
     * token
     */
    private final String jwt;
    
    @Override
    public Object getPrincipal() {
        
        return uid;
    }
    
    @Override
    public Object getCredentials() {
        
        return jwt;
    }
}
