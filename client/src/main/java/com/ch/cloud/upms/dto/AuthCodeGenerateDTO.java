package com.ch.cloud.upms.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AuthCodeGenerateDTO {
    
    
    /**
     * 授权人
     */
    private String authUser;
    
    /**
     * 授权内容（JSON字符串或对象）
     */
    private String content;
    
    /**
     * 过期时间
     */
    private Date expireTime;
    
    /**
     * 最大使用次数
     */
    private Integer maxUses;
} 