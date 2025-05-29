package com.ch.cloud.upms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ch.cloud.upms.dto.ProjectRoleDto;
import com.ch.cloud.upms.enums.RoleType;
import com.ch.cloud.upms.user.model.Project;
import com.ch.cloud.upms.user.model.Tenant;
import com.ch.cloud.upms.user.model.User;
import com.ch.cloud.upms.user.pojo.DepartmentDuty;

import java.util.List;

/**
 * <p>
 * 后台用户表 服务类
 * </p>
 *
 * @author zhimin.ma
 * @since 2020-03-25
 */
public interface IUserService extends IService<User> {
    
    User findByUsername(String username);
    
    int updatePassword(User user);
    
    List<User> findByLikeUserId(String userId);
    
    List<User> findByLikeRealName(String realName);
    
    List<User> findByLikeUsername(String username);
    
    List<User> findAllValid();
    
    Page<User> page(User record, int pageNum, int pageSize);
    
    List<DepartmentDuty> findDepartmentDuty(Long id);
    
    List<Tenant> findTenantsByUsername(String username);
    
    List<Project> findProjectsByUsernameAndTenantId(String username, Long tenantId);
    
    List<Long> findProjectIdsByUserId(String username);
    
    List<ProjectRoleDto> findProjectRoleByUserId(String username);
    
    boolean existsRole(Long userId, Long roleId);
    
    User getByUserId(String userId);
    
    List<ProjectRoleDto> listProjectRoleByUserIdAndProjectId(String username, Long projectId);
    
    Boolean existsProjectRole(String username, Long projectId, RoleType role);
    
    List<String> listProjectRoleByUserIdAndProjectId(String username, Long projectId, String roles);
    
    List<User> listByLikeDepartmentId(String departmentId);
    
    int insertRole(Long userId, Long roleId);
    
    int deleteRole(Long userId, Long roleId);
    
    int deleteDepartmentPositionByUserId(Long id);
    
    int insertDepartmentPosition(Long userId, String deptId, Long positionId, String orgId);
}
