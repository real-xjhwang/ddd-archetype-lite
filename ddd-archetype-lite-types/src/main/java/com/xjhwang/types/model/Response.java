package com.xjhwang.types.model;

import com.xjhwang.types.enums.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 请求响应
 *
 * @author 黄雪杰 on 2024-06-11 16:49
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> implements Serializable {
    
    private static final long serialVersionUID = -3262606219438715733L;
    
    /**
     * 响应码
     */
    private String code;
    
    /**
     * 描述信息
     */
    private String info;
    
    /**
     * 业务数据
     */
    private T data;
    
    
    /**
     * 请求成功，无响应内容时的默认响应
     *
     * @param <T>  响应内容类型
     * @return 响应实体
     */
    public static <T> Response<T> success() {
        
        return success(null);
    }
    
    /**
     * 请求成功，有响应内容时的默认响应
     *
     * @param data 响应内容
     * @param <T>  响应内容类型
     * @return 响应实体
     */
    public static <T> Response<T> success(T data) {
        
        return Response.<T>builder()
            .code(ResponseCode.SUCCESS.getCode())
            .info(ResponseCode.SUCCESS.getInfo())
            .data(data)
            .build();
    }
}
