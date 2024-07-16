package com.xjhwang.security.service.identify.impl;

import com.xjhwang.security.model.entity.SignInSubjectEntity;
import com.xjhwang.security.model.entity.UserEntity;
import com.xjhwang.security.repository.ISecurityRepository;
import com.xjhwang.security.service.JwtProvider;
import com.xjhwang.security.service.identify.ISignInService;
import com.xjhwang.types.enums.ResponseCode;
import com.xjhwang.types.exception.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.credential.PasswordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 黄雪杰 on 2024-07-16 13:57
 */
@Slf4j
@Service
public class SignInService implements ISignInService {
    
    @Resource
    private ISecurityRepository securityRepository;
    
    @Resource
    private JwtProvider jwtProvider;
    
    @Resource
    private PasswordService passwordService;
    
    @Override
    public String signIn(SignInSubjectEntity signInSubjectEntity) {
        
        String username = signInSubjectEntity.getUsername();
        String password = signInSubjectEntity.getPassword();
        
        UserEntity userEntity = securityRepository.getUserByUsername(username);
        if (userEntity == null) {
            throw new ApplicationException(ResponseCode.USERNAME_OR_PASSWORD_INVALID);
        }
        // 比对密码
        if (!passwordService.passwordsMatch(password, userEntity.getPassword())) {
            throw new ApplicationException(ResponseCode.USERNAME_OR_PASSWORD_INVALID);
        }
        return jwtProvider.encode(username, 10 * 60 * 1000, null);
    }
}
