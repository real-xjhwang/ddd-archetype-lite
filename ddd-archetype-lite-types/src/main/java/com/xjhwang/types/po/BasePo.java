package com.xjhwang.types.po;

import lombok.Data;

import java.util.Date;

/**
 * 数据库表基础字段
 *
 * @author 黄雪杰 on 2024-07-11 11:24
 */
@Data
public abstract class BasePo {
    
    /**
     * 主键ID
     */
    private String id;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 创建时间
     */
    private Date updateTime;
    
    /**
     * 创建者
     */
    private String createBy;
    
    /**
     * 更新者
     */
    private String updateBy;
    
    /**
     *
     */
    private Boolean deleted;
}
