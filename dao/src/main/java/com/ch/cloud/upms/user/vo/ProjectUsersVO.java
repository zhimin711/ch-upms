package com.ch.cloud.upms.user.vo;

import lombok.Data;

import java.util.List;

/**
 * <p>
 * desc: ProjectUsersVO
 * </p>
 *
 * @author zhimin
 * @since 2023/1/3
 */
@Data
public class ProjectUsersVO {
    
    private List<Long> projectIds;
    
    private List<String> devUserIds;
    
    private List<String> testUserIds;
}
