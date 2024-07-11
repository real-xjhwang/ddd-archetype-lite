package com.xjhwang.security.service.filter;

import cn.hutool.http.server.HttpServerRequest;
import com.xjhwang.security.model.entity.GatewayAuthenticationToken;
import com.xjhwang.types.util.IdUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author 黄雪杰 on 2024-07-11 18:12
 */
@Slf4j
public class JwtFilter extends AccessControlFilter {
    
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        
        return false;
    }
    
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        
        HttpServerRequest httpServerRequest = (HttpServerRequest)request;
        String token = httpServerRequest.getHeader("Authorization");
        try {
            getSubject(request, response).login(new GatewayAuthenticationToken(IdUtils.getSnowflakeNextIdStr(), token));
        } catch (Exception e) {
            log.error("认证失败", e);
            return false;
        }
        return true;
    }
}
