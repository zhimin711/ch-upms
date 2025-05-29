package com.ch.cloud.upms.manage.impl;

import com.ch.Separator;
import com.ch.cloud.upms.manage.IDepartmentManage;
import com.ch.cloud.upms.user.model.Department;
import com.ch.cloud.upms.user.model.Project;
import com.ch.cloud.upms.user.model.Tenant;
import com.ch.cloud.upms.user.model.User;
import com.ch.cloud.upms.user.pojo.DepartmentDuty;
import com.ch.cloud.upms.service.IDepartmentDutyService;
import com.ch.cloud.upms.service.IDepartmentService;
import com.ch.cloud.upms.service.IProjectService;
import com.ch.cloud.upms.service.ITenantService;
import com.ch.cloud.upms.service.IUserService;
import com.ch.utils.BeanUtilsV2;
import com.ch.utils.CommonUtils;
import com.ch.utils.StringUtilsV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    
    @Autowired
    private IUserService userService;
    
    @Autowired
    private ITenantService tenantService;
    
    @Autowired
    private IProjectService projectService;
    
    @Autowired
    private IDepartmentDutyService departmentDutyService;
    
    @Override
    @Cacheable
    public Department get(Long id) {
        return departmentService.getById(id);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(key = "#record.id")
    public boolean update(Department record) {
        Department orig = departmentService.getById(record.getId());
        boolean ok = departmentService.updateById(record);
        if (!ok) {
            return false;
        }
        boolean isChangeParent = !CommonUtils.isEquals(orig.getParentId(), record.getParentId());
        if (!CommonUtils.isEquals(orig.getName(), record.getName()) || isChangeParent) {
            List<Long> ids = StringUtilsV2.parseIds(record.getParentId());
            ids.add(record.getId());
            String deptFullName = departmentService.findCascadeName(ids);
            
            String dept0 = StringUtilsV2.linkStrIgnoreZero(Separator.COMMA_SIGN, orig.getParentId(),
                    orig.getId().toString());
            String dept2 = StringUtilsV2.linkStrIgnoreZero(Separator.COMMA_SIGN, record.getParentId(),
                    record.getId().toString());
            
            updateDepartmentUser(isChangeParent, deptFullName, dept0, dept2);
            updateDepartmentTenant(isChangeParent, deptFullName, dept0, dept2);
            updateDepartmentProject(isChangeParent, deptFullName, dept0, dept2);
            
        }
        return true;
    }
    
    private void updateDepartmentProject(boolean isChangeParent, String deptFullName, String dept0, String dept2) {
        List<Project> list = projectService.listByLikeDepartment(dept0);
        if (list.isEmpty()) {
            return;
        }
        // because clean cache must one by one update
        list.forEach(e -> {
            if (CommonUtils.isEquals(e.getDepartment(), dept0)) {
                e.setDepartmentName(deptFullName);
                if (isChangeParent) {
                    e.setDepartment(dept2);
                }
            } else {
                String dept1 = e.getDepartment().replaceFirst(dept0, dept2);
                String fullName1 = departmentService.findCascadeName(StringUtilsV2.parseIds(dept1));
                e.setDepartmentName(fullName1);
                e.setDepartment(dept1);
            }
            projectService.updateById(e);
        });
//        projectService.updateBatchById(list);
    }
    
    private void updateDepartmentTenant(boolean isChangeParent, String deptFullName, String dept0, String dept2) {
        List<Tenant> list = tenantService.listByLikeDepartmentId(dept0);
        if (list.isEmpty()) {
            return;
        }
        list.forEach(e -> {
            if (CommonUtils.isEquals(e.getDepartmentId(), dept0)) {
                e.setDepartmentName(deptFullName);
                if (isChangeParent) {
                    e.setDepartmentId(dept2);
                }
            } else {
                String dept1 = e.getDepartmentId().replaceFirst(dept0, dept2);
                String fullName1 = departmentService.findCascadeName(StringUtilsV2.parseIds(dept1));
                e.setDepartmentName(fullName1);
                e.setDepartmentId(dept1);
            }
        });
        tenantService.updateBatchById(list);
    }
    
    private void updateDepartmentUser(boolean isChangeParent, String deptFullName, String dept0, String dept2) {
        List<User> list = userService.listByLikeDepartmentId(dept0);
        if (list.isEmpty()) {
            return;
        }
        // because clean cache must one by one update
        list.forEach(e -> {
            if (CommonUtils.isEquals(e.getDepartmentId(), dept0)) {
                e.setDepartmentName(deptFullName);
                if (isChangeParent) {
                    e.setDepartmentId(dept2);
                }
            } else {
                String dept1 = e.getDepartmentId().replaceFirst(dept0, dept2);
                String fullName1 = departmentService.findCascadeName(StringUtilsV2.parseIds(dept1));
                e.setDepartmentName(fullName1);
                e.setDepartmentId(dept1);
            }
            userService.updateById(e);
        });
        if (!isChangeParent) {
            return;
        }
        List<DepartmentDuty> list2 = departmentDutyService.listByLikeDepartmentId(dept0);
        if (CommonUtils.isEmpty(list2)) {
            return;
        }
        list2.forEach(e -> {
            DepartmentDuty duty = BeanUtilsV2.clone(e);
            if (CommonUtils.isEquals(e.getDepartment(), dept0)) {
                e.setDepartment(dept2);
            } else {
                String dept1 = e.getDepartment().replaceFirst(dept0, dept2);
                e.setDepartment(dept1);
            }
            if (CommonUtils.isNotEmpty(e.getOrgId())) {
                String orgId = e.getOrgId().replaceFirst(dept0, dept2);
                e.setOrgId(orgId);
            }
            departmentDutyService.update(e, duty);
        });
    }
    
    @Override
    @CacheEvict(key = "#id")
    public boolean remove(Long id) {
        return departmentService.removeById(id);
    }
}
