package com.ch.cloud.upms.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * desc: ProjectRoleDto
 * </p>
 *
 * @author Zhimin.Ma
 * @since 2022/10/13
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ProjectRoleDto extends ProjectDto{
    
    private String role;
}
