package com.xjhwang.security.service.filter;

import cn.hutool.http.server.HttpServerRequest;
import com.alibaba.fastjson.JSON;
import com.xjhwang.security.model.entity.GatewayAuthenticationToken;
import com.xjhwang.types.enums.ResponseCode;
import com.xjhwang.types.model.Response;
import com.xjhwang.types.util.IdUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author 黄雪杰 on 2024-07-11 18:12
 */
@Slf4j
public class JwtFilter extends AuthenticatingFilter {
    
    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        
        HttpServerRequest httpServerRequest = (HttpServerRequest)request;
        String token = httpServerRequest.getHeader("Authorization");
        return new GatewayAuthenticationToken(IdUtils.getSnowflakeNextIdStr(), token);
    }
    
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        
        return super.isAccessAllowed(request, response, mappedValue);
    }
    
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        
        PrintWriter writer = httpServletResponse.getWriter();
        Response<String> body = Response.<String>builder()
            .code(ResponseCode.UNAUTHORIZED.getCode())
            .info(ResponseCode.UNAUTHORIZED.getInfo())
            .build();
        writer.write(JSON.toJSONString(body));
        writer.flush();
        writer.close();
        return false;
    }
}
