package com.ch.cloud.upms.dto;

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
public class ProjectUserRoleDTO {
    
    private Long projectId;
    
    private String userId;
    
    private String role;
}
