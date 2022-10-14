package com.ch.cloud.upms.dto;

import lombok.Data;

/**
 * 描述：
 *
 * @author Zhimin.Ma
 * @since 2022/4/28
 */
@Data
public class ProjectDto {


    private Long   id;
    /**
     * 代码
     */
    private String code;
    /**
     * 名称
     */
    private String name;
    /**
     * 租户名称
     */
    private String tenantName;
    /**
     * 负责人
     */
    private String manager;
    
}
