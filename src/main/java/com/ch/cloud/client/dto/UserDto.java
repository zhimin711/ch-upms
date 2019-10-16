package com.ch.cloud.client.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * decs:
 *
 * @author 01370603
 * @date 2019/5/28
 */
@Data
@ApiModel("用户信息")
public class UserDto {

    @ApiModelProperty(hidden = true)
    private Long id;
    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String username;
    /**
     * 密码
     */
    @ApiModelProperty("密码")
    private String password;
}
