package com.ch.cloud.upms.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch.StatusS;
import com.ch.cloud.upms.dto.ProjectRoleDto;
import com.ch.cloud.upms.enums.RoleType;
import com.ch.cloud.upms.user.mapper.UserMapper;
import com.ch.cloud.upms.user.mapper2.UserDepartmentPositionMapper;
import com.ch.cloud.upms.user.mapper2.UserProjectMapper;
import com.ch.cloud.upms.user.mapper2.UserRoleMapper;
import com.ch.cloud.upms.user.model.Project;
import com.ch.cloud.upms.user.model.Tenant;
import com.ch.cloud.upms.user.model.User;
import com.ch.cloud.upms.user.pojo.DepartmentDuty;
import com.ch.cloud.upms.service.IUserService;
import com.ch.utils.CommonUtils;
import com.ch.utils.DateUtils;
import com.google.common.collect.Lists;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 后台用户表 服务实现类
 * </p>
 *
 * @author zhimin.ma
 * @since 2020-03-25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    
    @Resource
    private UserProjectMapper userProjectMapper;
    
    @Resource
    private UserRoleMapper userRoleMapper;
    
    @Resource
    private UserDepartmentPositionMapper userDepartmentPositionMapper;
    
    @Override
    public User findByUsername(String username) {
        if (CommonUtils.isEmpty(username)) {
            return null;
        }
        User record = new User();
        record.setUsername(username);
        return super.getOne(Wrappers.query(record));
    }
    
    
    @Caching(evict = {@CacheEvict(cacheNames = "user", key = "#record.id"),
            @CacheEvict(cacheNames = "user", key = "#record.userId"),
            @CacheEvict(cacheNames = "user", key = "#record.username")})
    @Override
    public int updatePassword(User record) {
        if (record != null && CommonUtils.isNotEmpty(record.getId(), record.getPassword())) {
            User r = new User();
            r.setId(record.getId());
            r.setPassword(record.getPassword());
            r.setUpdateBy(record.getUpdateBy());
            r.setUpdateAt(DateUtils.current());
            boolean isUpdated = super.updateById(r);
            if (isUpdated) {
                return 1;
            }
        }
        return 0;
    }
    
    @Override
    public List<User> findByLikeUserId(String userId) {
        if (CommonUtils.isEmpty(userId)) {
            return Lists.newArrayList();
        }
        return super.query().like("user_id", userId).eq("status", StatusS.ENABLED).list();
    }
    
    @Override
    public List<User> findByLikeRealName(String realName) {
        return super.query().like("real_name", realName).eq("status", StatusS.ENABLED).list();
    }
    
    @Override
    public List<User> findByLikeUsername(String username) {
        return super.query().likeRight("username", username).eq("status", StatusS.ENABLED).list();
    }
    
    @Override
    public List<User> findAllValid() {
        return super.query().eq("status", StatusS.ENABLED).list();
    }
    
    
    @Caching(evict = {@CacheEvict(cacheNames = "user", key = "#record.id"),
            @CacheEvict(cacheNames = "user", key = "#record.userId"),
            @CacheEvict(cacheNames = "user", key = "#record.username")})
    @Override
    public boolean updateById(User record) {
        return super.updateById(record);
    }
    
    
    @Override
    public Page<User> page(User record, int pageNum, int pageSize) {
        return getBaseMapper().pageBy(new Page<>(pageNum, pageSize), record);
    }
    
    @Override
    public List<DepartmentDuty> findDepartmentDuty(Long id) {
        return userDepartmentPositionMapper.findDepartmentPositionByUserId(id);
    }
    
    @Override
    public List<Tenant> findTenantsByUsername(String username) {
        return getBaseMapper().findTenantsByUsername(username);
    }
    
    @Override
    public List<Project> findProjectsByUsernameAndTenantId(String userId, Long tenantId) {
        return userProjectMapper.findProjectsByUserIdAndTenantId(userId, tenantId);
    }
    
    @Override
    public List<Long> findProjectIdsByUserId(String username) {
        return userProjectMapper.findProjectIdsByUserId(username);
    }
    
    @Override
    public List<ProjectRoleDto> findProjectRoleByUserId(String username) {
        return userProjectMapper.findProjectRoleByUserId(username);
    }
    
    @Override
    public boolean existsRole(Long userId, Long roleId) {
        return userRoleMapper.exists(userId, roleId);
    }
    
    @Override
    public User getByUserId(String userId) {
        if (CommonUtils.isEmpty(userId)) {
            return null;
        }
        User record = new User();
        record.setUserId(userId);
        return super.getOne(Wrappers.query(record));
    }
    
    @Override
    public List<ProjectRoleDto> listProjectRoleByUserIdAndProjectId(String username, Long projectId) {
        return userProjectMapper.listByUsernameAndProjectId(username, projectId);
    }
    
    @Override
    public Boolean existsProjectRole(String username, Long projectId, RoleType role) {
        return userProjectMapper.countProjectRole(username, projectId, role.name()) > 0;
    }
    
    @Override
    public List<String> listProjectRoleByUserIdAndProjectId(String username, Long projectId, String roles) {
        return userProjectMapper.listProjectRole(username, projectId, roles);
    }
    
    @Override
    public List<User> listByLikeDepartmentId(String dept) {
        return list(Wrappers.query(new User()).likeRight("department_id", dept));
    }
    
    @Override
    public int insertRole(Long userId, Long roleId) {
        return userRoleMapper.insert(userId, roleId);
    }
    
    @Override
    public int deleteRole(Long userId, Long roleId) {
        return userRoleMapper.delete(userId, roleId);
    }
    
    @Override
    public int deleteDepartmentPositionByUserId(Long id) {
        return userDepartmentPositionMapper.deleteDepartmentPositionByUserId(id);
    }
    
    @Override
    public int insertDepartmentPosition(Long userId, String deptId, Long positionId, String orgId) {
        return userDepartmentPositionMapper.insertDepartmentPosition(userId, deptId, positionId, orgId);
    }
}
