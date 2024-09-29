package com.xjhwang.security.service;

import com.alibaba.fastjson.JSON;
import com.xjhwang.security.model.entity.JwtAuthenticationToken;
import com.xjhwang.types.enums.ResponseCode;
import com.xjhwang.types.model.Response;
import com.xjhwang.types.util.IdUtils;
import com.xjhwang.types.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author 黄雪杰 on 2024-07-11 18:12
 */
@Slf4j
public class JwtFilter extends BasicHttpAuthenticationFilter {
    
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        
        if (!isLoginRequest(request, response)) {
            return false;
        }
        boolean allowed = false;
        try {
            allowed = executeLogin(request, response);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return allowed || super.isPermissive(mappedValue);
    }
    
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        
        String token = getAuthzHeader(request);
        return StringUtils.isNotBlank(token);
    }
    
    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        
        String token = getAuthzHeader(request);
        return new JwtAuthenticationToken(IdUtils.getSnowflakeNextIdStr(), token);
    }
    
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.setHeader("Access-Control-Allow-Origin", ((HttpServletRequest)request).getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.setCharacterEncoding("UTF-8");
        
        PrintWriter writer = httpServletResponse.getWriter();
        Response<?> r = Response.<String>builder()
            .code(ResponseCode.UNAUTHORIZED.getCode())
            .info(ResponseCode.UNAUTHORIZED.getInfo())
            .build();
        writer.write(JSON.toJSONString(r));
        return false;
    }
    
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域发送一个option请求
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }
}
