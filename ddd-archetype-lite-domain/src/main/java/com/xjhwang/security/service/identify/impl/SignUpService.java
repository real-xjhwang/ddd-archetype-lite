package com.xjhwang.security.service.identify.impl;

import com.xjhwang.security.model.entity.SignUpSubjectEntity;
import com.xjhwang.security.model.entity.UserEntity;
import com.xjhwang.security.repository.ISecurityRepository;
import com.xjhwang.security.service.identify.ISignUpService;
import com.xjhwang.types.enums.ResponseCode;
import com.xjhwang.types.exception.ApplicationException;
import com.xjhwang.types.util.IdUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
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
    
    @Override
    public void signUp(SignUpSubjectEntity signUpSubjectEntity) {
        
        UserEntity userEntity = securityRepository.getUserByPhone(signUpSubjectEntity.getPhone());
        if (userEntity != null) {
            throw new ApplicationException(ResponseCode.USER_ALREADY_EXISTS);
        }
        // 加盐
        String salt = IdUtils.simpleUUID();
        // 加密
        String encodedPassword = new SimpleHash("md5", signUpSubjectEntity.getPassword(), ByteSource.Util.bytes(salt), 2).toHex();
        
        userEntity = UserEntity.builder()
            .username(signUpSubjectEntity.getUsername())
            .password(encodedPassword)
            .salt(salt)
            .phone(signUpSubjectEntity.getPhone())
            .email(signUpSubjectEntity.getEmail())
            .build();
        securityRepository.saveSysUser(userEntity);
    }
}
