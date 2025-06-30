package com.ch.cloud.upms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "授权码撤销请求DTO")
public class AuthCodeRevokeDTO {
    
    /**
     * 授权码
     */
    @Schema(description = "授权码", example = "abc123xyz")
    private String code;
} 