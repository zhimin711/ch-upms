package com.ch.cloud.upms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * desc: UsernameDTO
 * </p>
 *
 * @author zhimin
 * @since 2025/7/30 09:33
 */
@Data
@Schema(description = "用户名信息DTO")
public class UsernameDTO {
    
    @Schema(description = "登录名")
    private String username;
    
    @Schema(description = "用户姓名")
    private String realName;
}
