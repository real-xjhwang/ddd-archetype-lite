package com.xjhwang.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 黄雪杰 on 2024-07-11 10:55
 */
@Slf4j
@Configuration
public class ShiroConfig {
    
    @Bean
    public Realm realm() {
        
        return new JdbcRealm();
    }
}
