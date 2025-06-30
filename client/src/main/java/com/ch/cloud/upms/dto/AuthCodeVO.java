package com.ch.cloud.upms.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AuthCodeVO {
    
    private String code;
    
    private String content;
    
    private Date expireTime;
    
    private Integer maxUses;
    
    private Integer usedCount;
    
    private Integer status;
    
    private Date createTime;
} 