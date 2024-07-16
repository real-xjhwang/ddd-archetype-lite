package com.xjhwang.security.service.identify;

import com.xjhwang.security.model.entity.SignUpSubjectEntity;

/**
 * @author 黄雪杰 on 2024-07-16 15:02
 */
public interface ISignUpService {
    
    void signUp(SignUpSubjectEntity signUpSubjectEntity);
}
