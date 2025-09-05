package com.ch.cloud.upms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * desc: ProjectUserRoleDTO
 * </p>
 *
 * @author zhimin
 * @since 2023/1/3
 */
@Data
@Schema(description = "项目用户角色DTO")
public class ProjectUserRoleDTO {
    
    @Schema(description = "项目ID")
    private Long projectId;
    
    @Schema(description = "用户ID")
    private String userId;
    
    @Schema(description = "用户真实姓名")
    private String realName;
    
    @Schema(description = "角色名称")
    private String role;
}
