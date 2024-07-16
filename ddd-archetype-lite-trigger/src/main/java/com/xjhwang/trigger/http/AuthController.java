package com.xjhwang.trigger.http;

import com.xjhwang.security.model.entity.SignInSubjectEntity;
import com.xjhwang.security.service.identify.ISignInService;
import com.xjhwang.trigger.api.IAuthService;
import com.xjhwang.trigger.dto.SignInRequestDto;
import com.xjhwang.types.enums.ResponseCode;
import com.xjhwang.types.exception.ApplicationException;
import com.xjhwang.types.model.Response;
import com.xjhwang.types.util.Assert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 黄雪杰 on 2024-07-16 11:27
 */
@Slf4j
@RequestMapping("/v1/auth/")
@RestController
public class AuthController implements IAuthService {
    
    @Resource
    private ISignInService signInService;
    
    @Override
    public Response<String> signIn(SignInRequestDto requestDto) {
        
        Assert.notBlank(requestDto.getUsername(), () -> new ApplicationException(ResponseCode.USERNAME_OR_PASSWORD_INVALID));
        Assert.notBlank(requestDto.getPassword(), () -> new ApplicationException(ResponseCode.USERNAME_OR_PASSWORD_INVALID));
        
        SignInSubjectEntity signInSubjectEntity = SignInSubjectEntity.builder()
            .username(requestDto.getUsername())
            .password(requestDto.getPassword())
            .build();
        String token = signInService.signIn(signInSubjectEntity);
        return Response.<String>builder()
            .code(ResponseCode.SUCCESS.getCode())
            .info(ResponseCode.SUCCESS.getInfo())
            .data(token)
            .build();
    }
}
