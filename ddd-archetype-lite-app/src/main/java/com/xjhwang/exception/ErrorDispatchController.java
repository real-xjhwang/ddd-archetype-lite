package com.xjhwang.exception;

import com.xjhwang.types.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xjhwang on 2024/7/15 22:06
 */
@Slf4j
@RequestMapping("/v1/error/")
@RestController
public class ErrorDispatchController {
    
    @RequestMapping("unauthorized")
    public Response<?> unauthorized(HttpServletRequest request) {
        
        Exception exception = (Exception)request.getAttribute("exception");
        throw new ShiroException(exception.getMessage(), exception);
    }
}
