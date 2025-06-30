package com.ch.cloud.upms.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AuthCodePermissionDTO {
    
    private String code;
    
    private Date expireTime;
    
    private Integer maxUses;
    
    private Integer usedCount;
    
    private Integer status;
    
    private List<PermissionDto> permissions;
} 