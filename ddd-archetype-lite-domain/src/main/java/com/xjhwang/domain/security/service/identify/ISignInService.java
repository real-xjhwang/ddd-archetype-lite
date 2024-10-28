package com.xjhwang.domain.security.service.identify;

import com.xjhwang.domain.security.model.entity.SignInSubjectEntity;

/**
 * @author 黄雪杰 on 2024-07-16 13:50
 */
public interface ISignInService {
    
    String signIn(SignInSubjectEntity signInSubjectEntity);
}
