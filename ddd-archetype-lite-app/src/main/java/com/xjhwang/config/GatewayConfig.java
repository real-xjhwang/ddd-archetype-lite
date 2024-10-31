package com.xjhwang.config;

import com.xjhwang.domain.security.service.*;
import com.xjhwang.infrastructure.persistent.repository.SecurityRepository;
import com.xjhwang.types.util.CollectionUtils;
import com.xjhwang.types.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 黄雪杰 on 2024-07-11 10:55
 */
@Slf4j
@Configuration
public class GatewayConfig {
    
    @Bean("shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("defaultWebSecurityManager") SecurityManager securityManager,
        @Qualifier("defaultShiroFilterChainDefinition") DefaultShiroFilterChainDefinition defaultShiroFilterChainDefinition) {
        
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/v1/auth/sign-in");
        shiroFilterFactoryBean.setUnauthorizedUrl("/v1/error/unauthorized");
        
        Map<String, Filter> filters = MapUtils.builder(new HashMap<String, Filter>())
            .put("jwt", new JwtFilter())
            .build();
        shiroFilterFactoryBean.setFilters(filters);
        shiroFilterFactoryBean.setFilterChainDefinitionMap(defaultShiroFilterChainDefinition.getFilterChainMap());
        return shiroFilterFactoryBean;
    }
    
    @Bean("defaultWebSecurityManager")
    public DefaultWebSecurityManager defaultWebSecurityManager(@Qualifier("multiRealmAuthenticator") MultiRealmAuthenticator multiRealmAuthenticator,
        @Qualifier("jwtAuthorizingRealm") JwtAuthorizingRealm jwtAuthorizingRealm,
        @Qualifier("shiroAuthorizingRealm") ShiroAuthorizingRealm shiroAuthorizingRealm) {
        
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setAuthenticator(multiRealmAuthenticator);
        defaultWebSecurityManager.setRealms(CollectionUtils.newArrayList(jwtAuthorizingRealm, shiroAuthorizingRealm));
        // 关闭ShiroDao
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        // 不需要将Shiro Session中的东西存到任何地方
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        defaultWebSecurityManager.setSubjectDAO(subjectDAO);
        
        ThreadContext.bind(defaultWebSecurityManager);
        SecurityUtils.setSecurityManager(defaultWebSecurityManager);
        return defaultWebSecurityManager;
    }
    
    @Bean("defaultShiroFilterChainDefinition")
    public DefaultShiroFilterChainDefinition defaultShiroFilterChainDefinition() {
        
        DefaultShiroFilterChainDefinition defaultShiroFilterChainDefinition = new DefaultShiroFilterChainDefinition();
        // 登录接口不需要认证
        defaultShiroFilterChainDefinition.addPathDefinition("/v1/auth/sign-in", "anon");
        // 注册接口不需要认证
        defaultShiroFilterChainDefinition.addPathDefinition("/v1/auth/sign-up", "anon");
        // 异常分发接口不需要认证
        defaultShiroFilterChainDefinition.addPathDefinition("/v1/error/**", "anon");
        // 其余接口都需要认证
        defaultShiroFilterChainDefinition.addPathDefinition("/**", "jwt,authc");
        return defaultShiroFilterChainDefinition;
    }
    
    @Bean("multiRealmAuthenticator")
    public MultiRealmAuthenticator multiRealmAuthenticator() {
        
        MultiRealmAuthenticator multiRealmAuthenticator = new MultiRealmAuthenticator();
        // 设置多 Realm 的认证策略，默认 AtLeastOneSuccessfulStrategy
        FirstSuccessfulStrategy firstSuccessfulStrategy = new FirstSuccessfulStrategy();
        multiRealmAuthenticator.setAuthenticationStrategy(firstSuccessfulStrategy);
        return multiRealmAuthenticator;
    }
    
    @Bean("jwtAuthorizingRealm")
    public JwtAuthorizingRealm jwtAuthorizingRealm(@Qualifier("jwtCredentialsMatcher") JwtCredentialsMatcher jwtCredentialsMatcher,
        @Qualifier("jwtProvider") JwtProvider jwtProvider,
        @Qualifier("securityRepository") SecurityRepository securityRepository) {
        
        JwtAuthorizingRealm jwtAuthorizingRealm = new JwtAuthorizingRealm(jwtProvider, securityRepository);
        jwtAuthorizingRealm.setCredentialsMatcher(jwtCredentialsMatcher);
        return jwtAuthorizingRealm;
    }
    
    @Bean
    public ShiroAuthorizingRealm shiroAuthorizingRealm(@Qualifier("hashedCredentialsMatcher") HashedCredentialsMatcher hashedCredentialsMatcher,
        @Qualifier("securityRepository") SecurityRepository securityRepository) {
        
        ShiroAuthorizingRealm shiroAuthorizingRealm = new ShiroAuthorizingRealm(securityRepository);
        shiroAuthorizingRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return shiroAuthorizingRealm;
    }
    
    @Bean("jwtCredentialsMatcher")
    public JwtCredentialsMatcher jwtCredentialsMatcher(@Qualifier("jwtProvider") JwtProvider jwtProvider) {
        
        return new JwtCredentialsMatcher(jwtProvider);
    }
    
    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        
        return new LifecycleBeanPostProcessor();
    }
    
    /**
     * 对 Spring Bean 开启 Shiro 注解的支持
     *
     * @param securityManager SecurityManager
     * @return AuthorizationAttributeSourceAdvisor
     */
    @Bean("authorizationAttributeSourceAdvisor")
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("defaultWebSecurityManager") SecurityManager securityManager) {
        
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
    
    @Bean("hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        hashedCredentialsMatcher.setHashIterations(2);
        return hashedCredentialsMatcher;
    }
}
