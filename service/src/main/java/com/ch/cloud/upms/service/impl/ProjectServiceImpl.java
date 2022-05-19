package com.ch.cloud.upms.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch.cloud.upms.mapper.ProjectMapper;
import com.ch.cloud.upms.mapper2.UserProjectMapper;
import com.ch.cloud.upms.model.Namespace;
import com.ch.cloud.upms.model.Project;
import com.ch.cloud.upms.service.IProjectService;
import com.ch.cloud.upms.enums.RoleType;
import com.ch.utils.CommonUtils;
import com.ch.utils.SQLUtils;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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
        return super.query()
                .like(CommonUtils.isNotEmpty(record.getCode()), "code", record.getCode())
                .like(CommonUtils.isNotEmpty(record.getName()), "name", record.getName())
                .eq(CommonUtils.isNotEmpty(record.getStatus()), "status", record.getStatus())
                .orderByAsc("sort", "id").page(new Page<>(pageNum, pageSize));
    }

    @Override
    public List<Namespace> findNamespaces(Long id) {
        return getBaseMapper().findNamespaces(id);
    }

    @Override
    public int assignNamespaces(Long id, List<Long> namespaceIds) {
        List<Namespace> list = findNamespaces(id);
        List<Long> uNamespaceIds = list.stream().map(Namespace::getId).collect(Collectors.toList());
//        List<Long> uRoleIds2 = roleList.stream().filter(r -> CommonUtils.isEquals(r.getType(), StatusS.DISABLED)).map(Role::getId).collect(Collectors.toList());

        AtomicInteger c = new AtomicInteger();
        if (!namespaceIds.isEmpty()) {//1，2，3 | 3、4、5
            namespaceIds.stream().filter(r -> !uNamespaceIds.contains(r)).forEach(r -> c.getAndAdd(getBaseMapper().insertAssignNamespace(id, r)));
            uNamespaceIds.stream().filter(r -> !namespaceIds.contains(r)).forEach(r -> c.getAndAdd(getBaseMapper().deleteAssignNamespace(id, r)));
        } else if (!uNamespaceIds.isEmpty()) {
            uNamespaceIds.forEach(r -> c.getAndAdd(getBaseMapper().deleteAssignNamespace(id, r)));
        }
        return c.get();
    }

    @Override
    public List<String> findUsers(Long id) {
        return userProjectMapper.findUserIdsByProjectId(id);
    }

    @Override
    public int assignUsers(Long id, List<String> userIds) {
        List<String> list = findUsers(id);
        AtomicInteger c = new AtomicInteger();
        if (!userIds.isEmpty()) {
            userIds.stream().filter(r -> !list.contains(r)).forEach(r -> c.getAndAdd(userProjectMapper.insert(id, r)));
            list.stream().filter(r -> !userIds.contains(r)).forEach(r -> c.getAndAdd(userProjectMapper.delete(id, r)));
        } else if (!list.isEmpty()) {
            list.forEach(r -> c.getAndAdd(userProjectMapper.delete(id, r)));
        }
        return c.get();
    }

    //    @Override
    private int assignUsers(Long id, List<String> userIds, RoleType role) {
        List<String> list = userProjectMapper.findUserIdsByProjectIdAndRole(id, role.name());
        AtomicInteger c = new AtomicInteger();
        if (CommonUtils.isNotEmpty(userIds)) {
            userIds.stream().filter(r -> !list.contains(r)).forEach(r -> c.getAndAdd(userProjectMapper.insertFull(id, r, role.name())));
            list.stream().filter(r -> !userIds.contains(r)).forEach(r -> c.getAndAdd(userProjectMapper.deleteFull(id, r, role.name())));
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
        if (!ok) return false;
        if (CommonUtils.isNotEmpty(entity.getManager())) {
            userProjectMapper.insertFull(entity.getId(), entity.getManager(), RoleType.MRG.name());
        }
        if (!CommonUtils.isEmpty(entity.getDevUserIds())) {
            entity.getDevUserIds().forEach(uid -> userProjectMapper.insertFull(entity.getId(), uid, RoleType.DEV.name()));
        }
        if (!CommonUtils.isEmpty(entity.getTestUserIds())) {
            entity.getTestUserIds().forEach(uid -> userProjectMapper.insertFull(entity.getId(), uid, RoleType.TEST.name()));
        }
        return true;
    }

    @Override
    public boolean updateById(Project record) {
        boolean ok = super.updateById(record);
        if (!ok) return false;

        if (CommonUtils.isNotEmpty(record.getManager())) {
            userProjectMapper.deleteByProjectIdAndRole(record.getId(), RoleType.MRG.name());
            userProjectMapper.insertFull(record.getId(), record.getManager(), RoleType.MRG.name());
        }
        assignUsers(record.getId(), record.getDevUserIds(), RoleType.DEV);
        assignUsers(record.getId(), record.getTestUserIds(), RoleType.TEST);
        return true;
    }

    @Override
    public List<Project> findByNamespaceIdAndName(Long namespaceId, String name) {
        if (CommonUtils.isNotEmpty(name)) {
            name = SQLUtils.likeAny(name);
        }
        return getBaseMapper().findByNamespaceIdAndName(namespaceId, name);
    }

    @Override
    public List<Project> findByTenantId(Long tenantId) {
        if (tenantId == null) return Lists.newArrayList();
        Project record = new Project();
        record.setTenantId(tenantId);
        return super.list(Wrappers.query(record));
    }

    @Override
    public Project findByCode(String code) {
        if (CommonUtils.isEmpty(code)) return null;
        Project param = new Project();
        param.setCode(code);
        return super.getOne(Wrappers.query(param));
    }

}
