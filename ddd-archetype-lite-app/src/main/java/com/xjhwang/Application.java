package com.xjhwang;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author 黄雪杰 on 2024-05-07 18:02
 */
@Slf4j
@EnableScheduling
@SpringBootApplication
public class Application {
    
    public static void main(String[] args) {
        
        SpringApplication.run(Application.class, args);
    }
}
