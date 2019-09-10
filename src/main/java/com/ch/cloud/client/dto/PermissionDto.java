package com.ch.cloud.client.dto;

import lombok.Data;

/**
 * decs:
 *
 * @author 01370603
 * @date 2019/9/10
 */
@Data
public class PermissionDto {


    private Long id;

    /**
     * 上级菜单ID
     */
    private String pid;

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

    private String type;
}
