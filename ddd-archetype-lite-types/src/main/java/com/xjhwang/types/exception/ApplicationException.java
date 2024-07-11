package com.xjhwang.types.exception;

import com.xjhwang.types.enums.ResponseCode;
import lombok.Getter;

import java.io.Serializable;

/**
 * 应用内异常
 *
 * @author 黄雪杰 on 2024-06-11 16:36
 */
@Getter
public class ApplicationException extends RuntimeException implements Serializable {
    
    private static final long serialVersionUID = 2970905823330612247L;
    
    private final String code;
    
    private final String info;
    
    public ApplicationException(ResponseCode responseCode, Throwable cause) {
        
        this.code = responseCode.getCode();
        this.info = responseCode.getInfo();
        initCause(cause);
    }
    
    public ApplicationException(Throwable cause) {
        
        this.code = ResponseCode.UNKNOWN_ERROR.getCode();
        this.info = ResponseCode.UNKNOWN_ERROR.getInfo();
        initCause(cause);
    }
    
    public ApplicationException(ResponseCode responseCode) {
        
        this.code = responseCode.getCode();
        this.info = responseCode.getInfo();
    }
}
