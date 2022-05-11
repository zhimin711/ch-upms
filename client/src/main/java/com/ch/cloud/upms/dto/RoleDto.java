package com.ch.cloud.upms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * decs:
 *
 * @author 01370603
 * @date 2019/9/10
 */
@Data
@ApiModel("角色信息")
public class RoleDto implements Serializable {

    @ApiModelProperty(hidden = true)
    private Long id;
    @ApiModelProperty("代码")
    private String code;
    @ApiModelProperty("名称")
    private String name;
}
