package com.ch.cloud.upms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * decs:
 *
 * @author 01370603
 * @since 2019/9/10
 */
@Data
@Schema(description = "权限信息")
public class PermissionDto implements Serializable {

    @Schema(hidden = true)
    private Long id;

    /**
     * 上级菜单ID
     */
    @Schema(description = "上级菜单ID")
    private String parentId;

    /**
     * 菜单名称
     */
    @Schema(description = "菜单名称")
    private String name;

    /**
     * 菜单代码
     */
    @Schema(description = "菜单代码")
    private String code;

    /**
     * 图标代码
     */
    @Schema(description = "图标代码")
    private String icon;

    /**
     * 类型(1.目录 2.菜单页 3.按钮)
     */
    @Schema(description = "类型(1.目录 2.菜单页 3.按钮)")
    private String type;

    /**
     * （页面或请求）地址
     */
    @Schema(description = "（页面或请求）地址")
    private String url;

    /**
     * 重定向地址
     */
    @Schema(description = "重定向地址")
    private String redirect;

    /**
     * 请求方法
     */
    @Schema(description = "请求方法")
    private String method;

    /**
     * 菜单顺序
     */
    @Schema(description = "菜单顺序")
    private Integer sort;

    /**
     * 是否显示
     */
    @Schema(description = "是否隐藏")
    private Boolean hidden;
}