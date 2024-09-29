package com.xjhwang.security.service.identify.impl;

import com.xjhwang.security.model.entity.SignInSubjectEntity;
import com.xjhwang.security.service.JwtProvider;
import com.xjhwang.security.service.identify.ISignInService;
import com.xjhwang.types.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 黄雪杰 on 2024-07-16 13:57
 */
@Slf4j
@Service
public class SignInService implements ISignInService {
    
    @Resource
    private JwtProvider jwtProvider;
    
    @Override
    public String signIn(SignInSubjectEntity signInSubjectEntity) {
        
        // 获取当前用户主体
        Subject subject = SecurityUtils.getSubject();
        // 封装 Token
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(signInSubjectEntity.getPhone(), signInSubjectEntity.getPassword());
        // 登录
        subject.login(usernamePasswordToken);
        // 签发
        return jwtProvider.encode(signInSubjectEntity.getPhone(), 10 * 60 * 1000, MapUtils.newHashMap());
    }
}
