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
@Schema(description = "用户DTO")
public class UserDto implements Serializable {

    @Schema(description = "用户ID",hidden = true)
    private Long id;

    /**
     *
     */
    private String userId;

    /**
     * 用户名
     */
    @Schema(name = "username", description = "登录名", requiredMode = Schema.RequiredMode.REQUIRED)
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

    @Schema(description = "用户昵称")
    private String nickname;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "状态")
    private Integer status;

    @Schema(description = "创建时间")
    private java.util.Date createTime;

    @Schema(description = "更新时间")
    private java.util.Date updateTime;
}
