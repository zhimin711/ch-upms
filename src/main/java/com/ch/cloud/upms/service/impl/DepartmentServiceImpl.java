package com.ch.cloud.upms.service.impl;

import com.ch.cloud.upms.model.Department;
import com.ch.cloud.upms.mapper.DepartmentMapper;
import com.ch.cloud.upms.service.IDepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @author zhimin.ma
 * @since 2020-07-02
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

}
