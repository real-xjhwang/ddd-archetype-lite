package com.xjhwang.trigger.api;

import com.xjhwang.trigger.dto.SignInRequestDto;
import com.xjhwang.trigger.dto.SignUpRequestDto;
import com.xjhwang.types.model.Response;

/**
 * @author 黄雪杰 on 2024-07-16 11:25
 */
public interface IAuthService {
    
    /**
     * 登录
     *
     * @param requestDto 身份信息
     * @return token
     */
    Response<String> signIn(SignInRequestDto requestDto);
    
    /**
     * 注册
     *
     * @param requestDto 身份信息
     * @return 注册结果
     */
    Response<?> signUp(SignUpRequestDto requestDto);
}
