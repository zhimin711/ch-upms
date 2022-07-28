package com.ch.cloud.upms.manage.impl;

import com.ch.cloud.upms.manage.IDepartmentManage;
import com.ch.cloud.upms.model.Department;
import com.ch.cloud.upms.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * <p>
 * desc: DepartmentManageImpl
 * </p>
 *
 * @author Zhimin.Ma
 * @since 2022/7/28
 */
@Service
@CacheConfig(cacheNames = "dept")
public class DepartmentManageImpl implements IDepartmentManage {

    @Autowired
    private IDepartmentService departmentService;

    @Override
    @Cacheable
    public Department get(Long id){
        return departmentService.getById(id);
    }

    @Override
    @CacheEvict(key = "#record.id")
    public boolean update(Department record) {
        return departmentService.updateById(record);
    }

    @Override
    @CacheEvict(key = "#id")
    public boolean remove(Long id) {
        return departmentService.removeById(id);
    }
}
