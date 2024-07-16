package com.xjhwang.security.service.identify.impl;

import com.xjhwang.security.model.entity.SignUpSubjectEntity;
import com.xjhwang.security.model.entity.UserEntity;
import com.xjhwang.security.repository.ISecurityRepository;
import com.xjhwang.security.service.identify.ISignUpService;
import com.xjhwang.types.enums.ResponseCode;
import com.xjhwang.types.exception.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.credential.PasswordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 黄雪杰 on 2024-07-16 15:04
 */
@Slf4j
@Service
public class SignUpService implements ISignUpService {
    
    @Resource
    private ISecurityRepository securityRepository;
    
    @Resource
    private PasswordService passwordService;
    
    @Override
    public void signUp(SignUpSubjectEntity signUpSubjectEntity) {
        
        UserEntity userEntity = securityRepository.getUserByUsername(signUpSubjectEntity.getUsername());
        if (userEntity != null) {
            throw new ApplicationException(ResponseCode.USER_ALREADY_EXISTS);
        }
        // 密码加密
        String encryptPassword = passwordService.encryptPassword(signUpSubjectEntity.getPassword());
        userEntity = UserEntity.builder()
            .username(signUpSubjectEntity.getUsername())
            .password(encryptPassword)
            .phone(signUpSubjectEntity.getPhone())
            .email(signUpSubjectEntity.getEmail())
            .build();
        securityRepository.saveSysUser(userEntity);
    }
}
