package com.ch.cloud.upms.dto;

import lombok.Data;

@Data
public class AuthCodeValidateDTO {
    
    /**
     * 授权码
     */
    private String code;
    
    /**
     * 资源类型
     */
    private String resourceType;
    
    /**
     * 资源ID
     */
    private String resourceId;
    
    /**
     * 操作类型（如read、download等）
     */
    private String action;
} 