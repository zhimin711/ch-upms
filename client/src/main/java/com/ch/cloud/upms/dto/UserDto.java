package com.ch.cloud.upms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * decs:
 *
 * @author 01370603
 * @since 2019/5/28
 */
@Data
@Schema(description = "用户信息")
public class UserDto implements Serializable {
    
    @Schema(hidden = true)
    private Long id;
    
    /**
     *
     */
    private String userId;
    
    /**
     * 用户名
     */
    @Schema(name = "username", description = "登录名", required = true)
    private String username;
    
    /**
     *
     */
    private String realName;
    
    /**
     *
     */
    private String avatar;
    
    /**
     *
     */
    private Long roleId;
    
    /**
     *
     */
    private Long departmentId;
    
    /**
     *
     */
    private String departmentName;
    
    /**
     *
     */
    private Long tenantId;
    
    /**
     *
     */
    private String tenantName;
}
