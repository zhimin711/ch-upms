package com.ch.cloud.upms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch.cloud.upms.model.Department;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 部门表 服务类
 * </p>
 *
 * @author zhimin.ma
 * @since 2020-07-02
 */
public interface IDepartmentService extends IService<Department> {

    List<Department> findTreeByPid(String pid, boolean containsParent, Integer deptType);

    Page<Department> findTreePage(Department record, int pageNum, int pageSize);

    String findCascadeId(Long id);

    List<String> findNames(List<Long> ids);
    
    String findCascadeName(List<Long> ids);
}
