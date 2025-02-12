package com.xjhwang.config;

import com.xjhwang.trigger.http.IgnoreResponseWrapper;
import com.xjhwang.types.model.Response;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Method;

/**
 * 全局响应包装
 *
 * @author 黄雪杰
 * @since 2025/2/12 10:30
 */
@ControllerAdvice(basePackages = "com.xjhwang.trigger.http")
public class GlobalResponseWrapper implements ResponseBodyAdvice<Object> {
    
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        
        return !ignoreWrapResponse(returnType);
    }
    
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
        Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        
        if (body instanceof Response) {
            return body;
        }
        return Response.success(body);
    }
    
    private boolean ignoreWrapResponse(MethodParameter returnType) {
        
        if (returnType.getContainingClass().isAnnotationPresent(IgnoreResponseWrapper.class)) {
            return true;
        }
        Method method = returnType.getMethod();
        return method != null && method.isAnnotationPresent(IgnoreResponseWrapper.class);
    }
}
