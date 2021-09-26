package com.ch.cloud.upms.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch.cloud.upms.model.Tenant;
import com.ch.cloud.upms.mapper.TenantMapper;
import com.ch.cloud.upms.service.ITenantService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch.utils.CommonUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务-租户表 服务实现类
 * </p>
 *
 * @author zhimin.ma
 * @since 2021-09-26
 */
@Service
public class TenantServiceImpl extends ServiceImpl<TenantMapper, Tenant> implements ITenantService {

    @Override
    public Page<Tenant> page(Tenant record, int pageNum, int pageSize) {
        return super.query()
                .likeRight(CommonUtils.isNotEmpty(record.getName()), "name", record.getName())
                .likeRight(CommonUtils.isNotEmpty(record.getDepartmentId()), "department_id", record.getDepartmentId())
                .likeRight(CommonUtils.isNotEmpty(record.getDepartmentName()), "department_name", record.getDepartmentName())
                .eq(CommonUtils.isNotEmpty(record.getStatus()), "status", record.getStatus())
                .orderByAsc("sort", "id").page(new Page<>(pageNum, pageSize));
    }
}
