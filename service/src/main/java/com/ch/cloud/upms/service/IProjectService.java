package com.ch.cloud.upms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ch.cloud.upms.dto.ProjectUserRoleDTO;
import com.ch.cloud.upms.model.Project;
import com.ch.cloud.upms.vo.ProjectUsersVO;

import java.util.List;

/**
 * <p>
 * 业务-项目表 服务类
 * </p>
 *
 * @author zhimin.ma
 * @since 2021-09-23
 */
public interface IProjectService extends IService<Project> {
    
    Page<Project> page(Project record, int pageNum, int pageSize);
    
    List<ProjectUserRoleDTO> findUsers(Long id);
    
    int assignUsers(Long id, List<String> userIds);
    
    boolean exists(String userId, Long projectId);
    
    Project getWithUserById(Long id);
    
    List<Project> findByTenantId(Long tenantId);
    
    Project findByCode(String code);
    
    Integer batchAddUsers(ProjectUsersVO projectUsers);
}
