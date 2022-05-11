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
public class LoginUserDto implements Serializable {

    /**
     * 用户名
     */
    @ApiModelProperty(name = "username", value = "登录名", required = true)
    private String username;
    /**
     * 密码
     */
    @ApiModelProperty(name = "password", value = "密码", required = true, position = 1)
    private String password;
}
