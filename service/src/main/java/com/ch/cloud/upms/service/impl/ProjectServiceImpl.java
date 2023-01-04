package com.ch.cloud.upms.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch.cloud.upms.dto.ProjectUserRoleDTO;
import com.ch.cloud.upms.mapper.ProjectMapper;
import com.ch.cloud.upms.mapper2.UserProjectMapper;
import com.ch.cloud.upms.model.Project;
import com.ch.cloud.upms.service.IProjectService;
import com.ch.cloud.upms.enums.RoleType;
import com.ch.cloud.upms.vo.ProjectUsersVO;
import com.ch.utils.CommonUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>
 * 业务-项目表 服务实现类
 * </p>
 *
 * @author zhimin.ma
 * @since 2021-09-23
 */
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements IProjectService {
    
    @Resource
    private UserProjectMapper userProjectMapper;
    
    @Override
    public Page<Project> page(Project record, int pageNum, int pageSize) {
        return super.query().like(CommonUtils.isNotEmpty(record.getCode()), "code", record.getCode())
                .like(CommonUtils.isNotEmpty(record.getName()), "name", record.getName())
                .eq(CommonUtils.isNotEmpty(record.getStatus()), "status", record.getStatus())
                .eq(CommonUtils.isNotEmpty(record.getTenantId()), "tenant_id", record.getTenantId())
                .eq(CommonUtils.isNotEmpty(record.getDepartment()), "department", record.getDepartment())
                .orderByAsc("tenant_id", "sort", "id").page(new Page<>(pageNum, pageSize));
    }
    
    @Override
    public List<ProjectUserRoleDTO> findUsers(Long id) {
        return userProjectMapper.findUsersByProjectId(id);
    }
    
    @Override
    public int assignUsers(Long id, List<String> userIds) {
        List<ProjectUserRoleDTO> list = findUsers(id);
        AtomicInteger c = new AtomicInteger();
        if (!userIds.isEmpty()) {
//            userIds.stream().filter(r -> !list.contains(r)).forEach(r -> c.getAndAdd(userProjectMapper.insert(id, r)));
//            list.stream().filter(r -> !userIds.contains(r)).forEach(r -> c.getAndAdd(userProjectMapper.delete(id, r)));
        } else if (!list.isEmpty()) {
//            list.forEach(r -> c.getAndAdd(userProjectMapper.delete(id, r)));
        }
        return c.get();
    }
    
    //    @Override
    private int assignUsers(Long id, List<String> userIds, RoleType role) {
        List<String> list = userProjectMapper.findUserIdsByProjectIdAndRole(id, role.name());
        AtomicInteger c = new AtomicInteger();
        if (CommonUtils.isNotEmpty(userIds)) {
            userIds.stream().filter(r -> !list.contains(r))
                    .forEach(r -> c.getAndAdd(userProjectMapper.insertFull(id, r, role.name())));
            list.stream().filter(r -> !userIds.contains(r))
                    .forEach(r -> c.getAndAdd(userProjectMapper.deleteFull(id, r, role.name())));
        } else if (!list.isEmpty()) {
            list.forEach(r -> c.getAndAdd(userProjectMapper.deleteFull(id, r, role.name())));
        }
        return c.get();
    }
    
    @Override
    public boolean exists(String userId, Long projectId) {
        int c = userProjectMapper.exists(projectId, userId);
        return c > 0;
    }
    
    @Override
    public Project getWithUserById(Long id) {
        Project record = super.getById(id);
        if (record != null) {
            List<String> devUserIds = userProjectMapper.findUserIdsByProjectIdAndRole(id, RoleType.DEV.name());
            List<String> testUserIds = userProjectMapper.findUserIdsByProjectIdAndRole(id, RoleType.TEST.name());
            record.setDevUserIds(devUserIds);
            record.setTestUserIds(testUserIds);
        }
        return record;
    }
    
    @Override
    public boolean save(Project entity) {
        boolean ok = super.save(entity);
        if (!ok) {
            return false;
        }
        if (CommonUtils.isNotEmpty(entity.getManager())) {
            userProjectMapper.insertFull(entity.getId(), entity.getManager(), RoleType.MGR.name());
        }
        if (!CommonUtils.isEmpty(entity.getDevUserIds())) {
            entity.getDevUserIds()
                    .forEach(uid -> userProjectMapper.insertFull(entity.getId(), uid, RoleType.DEV.name()));
        }
        if (!CommonUtils.isEmpty(entity.getTestUserIds())) {
            entity.getTestUserIds()
                    .forEach(uid -> userProjectMapper.insertFull(entity.getId(), uid, RoleType.TEST.name()));
        }
        return true;
    }
    
    @Override
    @CacheEvict(cacheNames = "project", key = "#record.id")
    public boolean updateById(Project record) {
        return super.updateById(record);
    }
    
    
    @Override
    @CacheEvict(cacheNames = "project", key = "#record.id")
    public boolean updateWithUsersById(Project record) {
        boolean ok = super.updateById(record);
        if (!ok) {
            return false;
        }
        
        if (CommonUtils.isNotEmpty(record.getManager())) {
            userProjectMapper.deleteByProjectIdAndRole(record.getId(), RoleType.MGR.name());
            userProjectMapper.insertFull(record.getId(), record.getManager(), RoleType.MGR.name());
        }
        assignUsers(record.getId(), record.getDevUserIds(), RoleType.DEV);
        assignUsers(record.getId(), record.getTestUserIds(), RoleType.TEST);
        return true;
    }
    
    @Override
    public List<Project> findByTenantId(Long tenantId) {
        if (tenantId == null) {
            return Lists.newArrayList();
        }
        Project record = new Project();
        record.setTenantId(tenantId);
        return super.list(Wrappers.query(record));
    }
    
    @Override
    public Project findByCode(String code) {
        if (CommonUtils.isEmpty(code)) {
            return null;
        }
        Project param = new Project();
        param.setCode(code);
        return super.getOne(Wrappers.query(param));
    }
    
    @Override
    public Integer batchAddUsers(ProjectUsersVO usersVO) {
        usersVO.getProjectIds().forEach(id -> {
            List<ProjectUserRoleDTO> users = userProjectMapper.findUsersByProjectId(id);
            Set<String> devs =
                    CommonUtils.isNotEmpty(usersVO.getDevUserIds()) ? Sets.newHashSet(usersVO.getDevUserIds())
                            : Sets.newHashSet();
            Set<String> tests =
                    CommonUtils.isNotEmpty(usersVO.getTestUserIds()) ? Sets.newHashSet(usersVO.getTestUserIds())
                            : Sets.newHashSet();
            if (!users.isEmpty()) {
                users.forEach(u -> {
                    switch (RoleType.fromName(u.getRole())) {
                        case DEV:
                            devs.remove(u.getUserId());
                            break;
                        case TEST:
                            tests.remove(u.getUserId());
                            break;
                        default:
                            break;
                    }
                });
            }
            if (!devs.isEmpty()) {
                devs.forEach(uid -> userProjectMapper.insertFull(id, uid, RoleType.DEV.name()));
            }
            if (!tests.isEmpty()) {
                tests.forEach(uid -> userProjectMapper.insertFull(id, uid, RoleType.TEST.name()));
            }
        });
        return null;
    }
    
    @Override
    public List<Project> listByLikeDepartment(String department) {
        return query().likeRight("department", department).list();
    }
    
}
