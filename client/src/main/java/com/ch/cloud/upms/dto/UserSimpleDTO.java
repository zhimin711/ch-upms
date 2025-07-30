package com.ch.cloud.upms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * desc:
 *
 * @author zhimin
 * @since 2025/7/28
 */
@Data
@Schema(description = "用户简要信息DTO")
public class UserSimpleDTO implements Serializable {

    @Schema(description = "用户ID")
    private String userId;

    @Schema(description = "登录名")
    private String username;

    @Schema(description = "用户姓名")
    private String realName;

    @Schema(description = "头像")
    private String avatar;
}
