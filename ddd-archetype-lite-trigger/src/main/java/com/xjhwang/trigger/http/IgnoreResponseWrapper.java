package com.xjhwang.trigger.http;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 忽略响应的包装
 *
 * @author 黄雪杰
 * @since 2025/2/12 10:40
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreResponseWrapper {}
