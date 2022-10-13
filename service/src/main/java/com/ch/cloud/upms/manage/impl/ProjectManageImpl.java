package com.ch.cloud.upms.manage.impl;

import com.ch.cloud.upms.manage.IProjectManage;
import com.ch.cloud.upms.model.Project;
import com.ch.cloud.upms.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * <p>
 * desc: ProjectManageImpl
 * </p>
 *
 * @author Zhimin.Ma
 * @since 2022/10/13
 */
@Service
@CacheConfig(cacheNames = "project")
public class ProjectManageImpl implements IProjectManage {
    
    @Autowired
    private IProjectService projectService;
    
    @Override
    @Cacheable
    public Project getById(Long id) {
        return projectService.getById(id);
    }
}
