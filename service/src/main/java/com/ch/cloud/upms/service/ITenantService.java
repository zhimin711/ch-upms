package com.ch.cloud.upms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ch.Status;
import com.ch.cloud.upms.model.Tenant;

import java.util.List;

/**
 * <p>
 * 业务-租户表 服务类
 * </p>
 *
 * @author zhimin.ma
 * @since 2021-09-26
 */
public interface ITenantService extends IService<Tenant> {

    Page<Tenant> page(Tenant record, int pageNum, int pageSize);

    List<Tenant> findByDepartmentIdAndNameAndStatus(String prefixDeptId, String name, Status enabled);
    
    List<Tenant> listByLikeDepartmentId(String departmentId);
}
