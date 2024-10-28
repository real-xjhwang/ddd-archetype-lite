package com.xjhwang.domain.security.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 黄雪杰 on 2024-07-16 13:54
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignInSubjectEntity {
    
    private String phone;
    
    private String password;
}
