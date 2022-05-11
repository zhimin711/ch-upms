package com.ch.cloud.upms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * decs:
 *
 * @author 01370603
 * @date 2019/5/28
 */
@Data
@ApiModel("用户信息")
public class UserDto implements Serializable {

    @ApiModelProperty(hidden = true)
    private Long   id;
    /**
     *
     */
    private String userId;
    /**
     * 用户名
     */
    @ApiModelProperty(name = "username", value = "登录名", required = true)
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
    private Long   roleId;
    /**
     *
     */
    private Long   departmentId;
    /**
     *
     */
    private String departmentName;
    /**
     *
     */
    private Long   tenantId;
    /**
     *
     */
    private String tenantName;
}
