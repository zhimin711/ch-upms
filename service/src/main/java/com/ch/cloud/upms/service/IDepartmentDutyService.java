package com.ch.cloud.upms.service;

import com.ch.cloud.upms.pojo.DepartmentDuty;

import java.util.List;

/**
 * <p>
 * desc: IDepartmentDutyService
 * </p>
 *
 * @author zhimin
 * @since 2023/1/4
 */
public interface IDepartmentDutyService {
    
    List<DepartmentDuty> listByLikeDepartmentId(String departmentId);
    
    int update(DepartmentDuty value, DepartmentDuty condition);
}
