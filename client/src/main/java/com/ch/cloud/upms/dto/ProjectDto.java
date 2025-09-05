package com.ch.cloud.upms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 描述：
 *
 * @author Zhimin.Ma
 * @since 2022/4/28
 */
@Data
@Schema(description = "项目DTO")
public class ProjectDto {
    
    @Schema(description = "项目ID")
    private Long id;
    
    /**
     * 代码
     */
    private String code;
    
    @Schema(description = "项目名称")
    private String name;
    
    
    /**
     * 租户ID
     */
    @Schema(description = "租户ID")
    private Long tenantId;
    
    /**
     * 租户名称
     */
    @Schema(description = "租户名称")
    private String tenantName;
    
    /**
     * 负责人
     */
    @Schema(description = "负责人")
    private String manager;
    
    @Schema(description = "项目描述")
    private String description;
}
