package com.ch.cloud.upms.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch.StatusS;
import com.ch.cloud.upms.mapper2.UserProjectMapper;
import com.ch.cloud.upms.model.Namespace;
import com.ch.cloud.upms.model.Project;
import com.ch.cloud.upms.mapper.ProjectMapper;
import com.ch.cloud.upms.model.Role;
import com.ch.cloud.upms.service.IProjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch.pojo.VueRecord2;
import com.ch.utils.CommonUtils;
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
            list.stream().filter(r -> !userIds.contains(r)).forEach(r -> c.getAndAdd(userProjectMapper.insert(id, r)));
            userIds.stream().filter(r -> !list.contains(r)).forEach(r -> c.getAndAdd(userProjectMapper.delete(id, r)));
        } else if (!list.isEmpty()) {
            list.forEach(r -> c.getAndAdd(userProjectMapper.delete(id, r)));
        }
        return c.get();
    }

    @Override
    public boolean exists(String userId, Long projectId) {
        int c = userProjectMapper.exists(projectId, userId);
        return c > 0;
    }
}
