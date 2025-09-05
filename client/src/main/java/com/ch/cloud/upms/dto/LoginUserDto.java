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
public class LoginUserDto implements Serializable {

    /**
     * 用户名
     */
    @Schema(name = "username", description = "登录名")
    private String username;
    /**
     * 密码
     */
    @Schema(name = "password", description = "密码")
    private String password;
}
