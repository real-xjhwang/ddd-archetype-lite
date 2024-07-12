package com.xjhwang.config;

import com.xjhwang.security.service.filter.JwtFilter;
import com.xjhwang.security.service.realm.GatewayAuthorizingRealm;
import com.xjhwang.types.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("defaultWebSecurityManager") SecurityManager securityManager) {
        
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        
        Map<String, Filter> filters = MapUtils.builder(new HashMap<String, Filter>())
            .put("anon", new AnonymousFilter())
            .put("jwt", new JwtFilter())
            .build();
        shiroFilterFactoryBean.setFilters(filters);
        return shiroFilterFactoryBean;
    }
    
    @Bean("defaultWebSecurityManager")
    public DefaultWebSecurityManager defaultWebSecurityManager(@Qualifier("gatewayAuthorizingRealm") GatewayAuthorizingRealm gatewayAuthorizingRealm) {
        
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(gatewayAuthorizingRealm);
        // 关闭ShiroDao
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        // 不需要将Shiro Session中的东西存到任何地方
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        defaultWebSecurityManager.setSubjectDAO(subjectDAO);
        return defaultWebSecurityManager;
    }
    
    @Bean("defaultShiroFilterChainDefinition")
    public DefaultShiroFilterChainDefinition defaultShiroFilterChainDefinition() {
        
        DefaultShiroFilterChainDefinition defaultShiroFilterChainDefinition = new DefaultShiroFilterChainDefinition();
        // 登录接口不需要认证
        defaultShiroFilterChainDefinition.addPathDefinition("/api/v1/sign-in", "anon");
        // 注册接口不需要认证
        defaultShiroFilterChainDefinition.addPathDefinition("/api/v1/sign-up", "anon");
        // 其余接口都需要认证
        defaultShiroFilterChainDefinition.addPathDefinition("/**", "jwt");
        return defaultShiroFilterChainDefinition;
    }
    
    /**
     * Shiro与Spring AOP整合时需要特殊配置，否则接口上的Shiro注解不生效
     *
     * @return 代理配置
     */
    @Bean("defaultAdvisorAutoProxyCreator")
    @ConditionalOnMissingBean(DefaultAdvisorAutoProxyCreator.class)
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        
        DefaultAdvisorAutoProxyCreator defaultAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAutoProxyCreator.setProxyTargetClass(true);
        return defaultAutoProxyCreator;
    }
    
    @Bean("gatewayAuthorizingRealm")
    public GatewayAuthorizingRealm gatewayAuthorizingRealm() {
        
        GatewayAuthorizingRealm gatewayAuthorizingRealm = new GatewayAuthorizingRealm();
        gatewayAuthorizingRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return gatewayAuthorizingRealm;
    }
    
    private HashedCredentialsMatcher hashedCredentialsMatcher() {
        
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("SHA-256");
        hashedCredentialsMatcher.setHashIterations(6);
        return hashedCredentialsMatcher;
    }
}
