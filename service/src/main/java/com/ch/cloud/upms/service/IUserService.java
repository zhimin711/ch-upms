package com.ch.cloud.upms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch.cloud.upms.dto.ProjectRoleDto;
import com.ch.cloud.upms.enums.RoleType;
import com.ch.cloud.upms.model.Project;
import com.ch.cloud.upms.model.Tenant;
import com.ch.cloud.upms.model.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ch.cloud.upms.pojo.DepartmentDuty;
import org.springframework.transaction.annotation.Transactional;

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

    int assignRole(Long id, List<Long> roleIds);

    List<User> findByLikeUserId(String userId);

    List<User> findByLikeRealName(String realName);

    List<User> findByLikeUsername(String username);

    List<User> findAllValid();

    boolean saveWithAll(User record);

    boolean updateWithAll(User record);

    Page<User> page(User record, int pageNum, int pageSize);

    List<DepartmentDuty> findDepartmentDuty(Long id);

    List<Tenant> findTenantsByUsername(String username);

    List<Project> findProjectsByUsernameAndTenantId(String username, Long tenantId);
    
    List<Long> findProjectIdsByUserId(String username);
    
    List<ProjectRoleDto> findProjectRoleByUserId(String username);
    
    User getDefaultInfo(String username);

    boolean existsRole(Long userId, Long roleId);
    
    User getByUserId(String userId);
    
    List<ProjectRoleDto> listProjectRoleByUserIdAndProjectId(String username, Long projectId);

    Boolean existsProjectRole(String username, Long projectId, RoleType role);
}
