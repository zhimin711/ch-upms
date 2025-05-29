package com.ch.cloud.upms.service.impl;

import com.ch.cloud.upms.user.mapper2.UserDepartmentPositionMapper;
import com.ch.cloud.upms.user.pojo.DepartmentDuty;
import com.ch.cloud.upms.service.IDepartmentDutyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * desc: IDepartmentDutyServiceImpl
 * </p>
 *
 * @author zhimin
 * @since 2023/1/4
 */
@Service
public class IDepartmentDutyServiceImpl implements IDepartmentDutyService {
    
    @Resource
    private UserDepartmentPositionMapper userDepartmentPositionMapper;
    
    @Override
    public List<DepartmentDuty> listByLikeDepartmentId(String departmentId) {
        return userDepartmentPositionMapper.listByLikeDepartmentId(departmentId);
    }
    
    @Override
    public int update(DepartmentDuty value, DepartmentDuty condition) {
        return userDepartmentPositionMapper.update(value, condition);
    }
}
