package com.ch.cloud.client.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * decs:
 *
 * @author 01370603
 * @date 2019/9/10
 */
@Data
@ApiModel("权限信息")
public class PermissionDto {

    @ApiModelProperty(hidden = true)
    private Long id;

    /**
     * 上级菜单ID
     */
    private String parentId;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单代码
     */
    private String code;

    /**
     * 图标代码
     */
    private String icon;

    /**
     * 类型(1.目录 2.菜单页 3.按钮)
     */
    private String type;
    /**
     * （页面或请求）地址
     */
    private String url;
    /**
     * 重定向地址
     */
    private String redirect;
    /**
     * 请求方法
     */
    private String method;

    /**
     * 菜单顺序
     */
    private Integer sort;
    /**
     * 是否隐藏
     */
    private boolean hidden;
}
