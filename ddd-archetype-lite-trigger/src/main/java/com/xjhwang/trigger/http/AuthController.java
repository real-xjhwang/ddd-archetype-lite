package com.xjhwang.trigger.http;

import com.xjhwang.security.model.entity.SignInSubjectEntity;
import com.xjhwang.security.model.entity.SignUpSubjectEntity;
import com.xjhwang.security.service.identify.ISignInService;
import com.xjhwang.security.service.identify.ISignUpService;
import com.xjhwang.trigger.api.IAuthService;
import com.xjhwang.trigger.dto.SignInRequestDto;
import com.xjhwang.trigger.dto.SignUpRequestDto;
import com.xjhwang.types.enums.ResponseCode;
import com.xjhwang.types.exception.ApplicationException;
import com.xjhwang.types.model.Response;
import com.xjhwang.types.util.Assert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    
    @Resource
    private ISignUpService signUpService;
    
    @PostMapping("sign-in")
    @Override
    public Response<String> signIn(@RequestBody SignInRequestDto requestDto) {
        
        Assert.notBlank(requestDto.getPhone(), () -> new ApplicationException(ResponseCode.USERNAME_OR_PASSWORD_INVALID));
        Assert.notBlank(requestDto.getPassword(), () -> new ApplicationException(ResponseCode.USERNAME_OR_PASSWORD_INVALID));
        
        SignInSubjectEntity signInSubjectEntity = SignInSubjectEntity.builder()
            .phone(requestDto.getPhone())
            .password(requestDto.getPassword())
            .build();
        String token = signInService.signIn(signInSubjectEntity);
        return Response.success(token);
    }
    
    @PostMapping("sign-up")
    @Override
    public Response<?> signUp(@RequestBody SignUpRequestDto requestDto) {
        
        Assert.notBlank(requestDto.getUsername(), () -> new ApplicationException(ResponseCode.USERNAME_IS_BLANK));
        Assert.notBlank(requestDto.getPhone(), () -> new ApplicationException(ResponseCode.PHONE_IS_BLANK));
        Assert.notBlank(requestDto.getPassword(), () -> new ApplicationException(ResponseCode.PASSWORD_IS_BLANK));
        
        SignUpSubjectEntity signUpSubjectEntity = SignUpSubjectEntity.builder()
            .username(requestDto.getUsername())
            .password(requestDto.getPassword())
            .phone(requestDto.getPhone())
            .email(requestDto.getEmail())
            .build();
        signUpService.signUp(signUpSubjectEntity);
        return Response.success();
    }
}
