package com.xjhwang.types.common;

import cn.hutool.core.text.StrPool;
import lombok.experimental.UtilityClass;

/**
 * 常量
 *
 * @author 黄雪杰 on 2024-06-11 16:40
 */
@UtilityClass
public class Constants {
    
    /**
     * 字符串常量
     */
    public static class StringPool implements StrPool {}
    
    /**
     * Redis缓存key
     */
    public static class RedisKey {}
}
