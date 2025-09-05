package com.ch.cloud.upms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "授权码校验请求DTO")
public class AuthCodeValidateDTO {
    
    /**
     * 授权码
     */
    @Schema(description = "授权码", example = "abc123xyz")
    private String code;
    
    /**
     * 资源类型
     */
    @Schema(description = "资源类型", example = "article")
    private String resourceType;
    
    /**
     * 资源ID
     */
    @Schema(description = "资源ID", example = "123")
    private String resourceId;
    
    /**
     * 操作类型（如read、download等）
     */
    @Schema(description = "操作类型", example = "read")
    private String action;
} 