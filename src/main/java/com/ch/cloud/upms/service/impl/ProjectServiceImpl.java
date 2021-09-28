package com.ch.cloud.upms.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch.cloud.upms.model.Project;
import com.ch.cloud.upms.mapper.ProjectMapper;
import com.ch.cloud.upms.service.IProjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch.pojo.VueRecord2;
import com.ch.utils.CommonUtils;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public Page<Project> page(Project record, int pageNum, int pageSize) {
        return super.query()
                .like(CommonUtils.isNotEmpty(record.getCode()), "code", record.getCode())
                .like(CommonUtils.isNotEmpty(record.getName()), "name", record.getName())
                .eq(CommonUtils.isNotEmpty(record.getStatus()), "status", record.getStatus())
                .orderByAsc("sort", "id").page(new Page<>(pageNum, pageSize));
    }

    @Override
    public List<VueRecord2> findNamespaces(Long id) {
        return null;
    }

    @Override
    public Integer assignNamespaces(Long id, List<String> namespaceIds) {
        return null;
    }
}
