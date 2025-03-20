package com.ch.cloud.upms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 描述：
 *
 * @author Zhimin.Ma
 * @since 2022/4/28
 */
@Data
@Schema(description = "项目信息")
public class ProjectDto {

    @Schema(description = "主键")
    private Long id;
    /**
     * 代码
     */
    @Schema(description = "代码")
    private String code;
    /**
     * 名称
     */
    @Schema(description = "名称")
    private String name;
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
    
}