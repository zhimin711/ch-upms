package com.ch.cloud.upms.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch.Separator;
import com.ch.StatusS;
import com.ch.cloud.upms.enums.DepartmentType;
import com.ch.cloud.upms.manage.IDepartmentManage;
import com.ch.cloud.upms.mapper.UserMapper;
import com.ch.cloud.upms.mapper2.UserDepartmentPositionMapper;
import com.ch.cloud.upms.mapper2.UserProjectMapper;
import com.ch.cloud.upms.mapper2.UserRoleMapper;
import com.ch.cloud.upms.model.*;
import com.ch.cloud.upms.pojo.DepartmentDuty;
import com.ch.cloud.upms.service.*;
import com.ch.e.ExceptionUtils;
import com.ch.e.PubError;
import com.ch.utils.*;
import com.google.common.collect.Lists;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * <p>
 * 后台用户表 服务实现类
 * </p>
 *
 * @author zhimin.ma
 * @since 2020-03-25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    
    @Resource
    private IRoleService roleService;
    
    @Resource
    private IDepartmentService departmentService;
    
    @Resource
    private IDepartmentManage departmentManage;
    
    @Resource
    private IPositionService positionService;
    
    @Resource
    private ITenantService tenantService;
    
    @Resource
    private UserProjectMapper userProjectMapper;
    
    @Resource
    private UserRoleMapper userRoleMapper;
    
    @Resource
    private UserDepartmentPositionMapper userDepartmentPositionMapper;
    
    @Override
    public User findByUsername(String username) {
        if (CommonUtils.isEmpty(username)) {
            return null;
        }
        User record = new User();
        record.setUsername(username);
        return super.getOne(Wrappers.query(record));
    }
    
    
    @Caching(evict = {@CacheEvict(cacheNames = "user", key = "#record.id"),
            @CacheEvict(cacheNames = "user", key = "#record.userId"),
            @CacheEvict(cacheNames = "user", key = "#record.username")})
    @Override
    public int updatePassword(User record) {
        if (record != null && CommonUtils.isNotEmpty(record.getId(), record.getPassword())) {
            User r = new User();
            r.setId(record.getId());
            r.setPassword(record.getPassword());
            r.setUpdateBy(record.getUpdateBy());
            r.setUpdateAt(DateUtils.current());
            boolean isUpdated = super.updateById(r);
            if (isUpdated) {
                return 1;
            }
        }
        return 0;
    }
    
    @Override
    public int assignRole(Long id, List<Long> roleIds) {
        List<Role> roleList = roleService.findByUserId(id);
        
        List<Long> uRoleIds = roleList.stream().filter(r -> !CommonUtils.isEquals(r.getType(), StatusS.DISABLED))
                .map(Role::getId).collect(Collectors.toList());
        List<Long> uRoleIds2 = roleList.stream().filter(r -> CommonUtils.isEquals(r.getType(), StatusS.DISABLED))
                .map(Role::getId).collect(Collectors.toList());
        
        AtomicInteger c = new AtomicInteger();
        if (!roleIds.isEmpty()) {
            roleIds.stream().filter(r -> !uRoleIds.contains(r) && !uRoleIds2.contains(r))
                    .forEach(r -> c.getAndAdd(getBaseMapper().insertAssignRole(id, r)));
            uRoleIds.stream().filter(r -> !roleIds.contains(r) && !uRoleIds2.contains(r))
                    .forEach(r -> c.getAndAdd(getBaseMapper().deleteAssignRole(id, r)));
        } else if (!uRoleIds.isEmpty()) {
            uRoleIds.forEach(r -> c.getAndAdd(getBaseMapper().deleteAssignRole(id, r)));
        }
        return c.get();
    }
    
    @Override
    public List<User> findByLikeUserId(String userId) {
        if (CommonUtils.isEmpty(userId)) {
            return Lists.newArrayList();
        }
        return super.query().like("user_id", userId).eq("status", StatusS.ENABLED).list();
    }
    
    @Override
    public List<User> findByLikeRealName(String realName) {
        return super.query().like("real_name", realName).eq("status", StatusS.ENABLED).list();
    }
    
    @Override
    public List<User> findByLikeUsername(String username) {
        return super.query().likeRight("username", username).eq("status", StatusS.ENABLED).list();
    }
    
    @Override
    public List<User> findAllValid() {
        return super.query().eq("status", StatusS.ENABLED).list();
    }
    
    @Transactional
    @Override
    public boolean saveWithAll(User record) {
        if (CommonUtils.isEmpty(record.getPassword())) {
            record.setPassword(EncryptUtils.generate());
        }
        record.setUserId(RandomUtil.randomNumbers(10));
        boolean c = super.save(record);
        saveDepartmentPosition(record);
        super.updateById(record);
        return c;
    }
    
    @Caching(evict = {@CacheEvict(cacheNames = "user", key = "#record.id"),
            @CacheEvict(cacheNames = "user", key = "#record.userId"),
            @CacheEvict(cacheNames = "user", key = "#record.username")})
    @Transactional
    @Override
    public boolean updateWithAll(User record) {
        record.setUserId(null);
        record.setUsername(null);
        //不更新密码
        record.setPassword(null);
        saveDepartmentPosition(record);
        return super.updateById(record);
    }
    
    @Caching(evict = {@CacheEvict(cacheNames = "user", key = "#record.id"),
            @CacheEvict(cacheNames = "user", key = "#record.userId"),
            @CacheEvict(cacheNames = "user", key = "#record.username")})
    @Override
    public boolean updateById(User entity) {
        return super.updateById(entity);
    }
    
    private void saveDepartmentPosition(User record) {
        if (CommonUtils.isEmpty(record.getDutyList())) {
            ExceptionUtils._throw(PubError.NON_NULL, "组织职位不能为空！");
        }
        userDepartmentPositionMapper.deleteDepartmentPositionByUserId(record.getId());
        record.getDutyList().forEach(r -> {
            if (CommonUtils.isEmpty(r.getDepartment()) || CommonUtils.isEmpty(r.getDuty())) {
                ExceptionUtils._throw(PubError.NON_NULL, "组织或职位不能为空！");
            }
            Department dept = departmentService.getById(StringUtilsV2.lastId(r.getDepartment()));
            String deptId = r.getDepartment();
            String orgId = "";
            if (dept != null && dept.getDeptType() != null && dept.getDeptType().equals(3)) {
                deptId = dept.getParentId();
                orgId = r.getDepartment();
            }
            userDepartmentPositionMapper.insertDepartmentPosition(record.getId(), deptId, Long.valueOf(r.getDuty()),
                    orgId);
        });
        if (CommonUtils.isNotEmpty(record.getDepartmentId())) {
            List<String> names = departmentService.findNames(StringUtilsV2.parseIds(record.getDepartmentId()));
            record.setDepartmentName(String.join(",", names));
        }
        if (CommonUtils.isNotEmpty(record.getPositionId())) {
            Position pos = positionService.getById(record.getPositionId());
            if (pos != null) {
                record.setPositionName(pos.getName());
            }
        }
        if (CommonUtils.isNotEmpty(record.getTenantId())) {
            Tenant tenant = tenantService.getById(record.getTenantId());
            if (tenant != null) {
                record.setTenantName(tenant.getName());
            }
        }
    }
    
    @Override
    public Page<User> page(User record, int pageNum, int pageSize) {
        if (CommonUtils.isNumeric(record.getDepartment())) {
            Department dept = departmentManage.get(Long.valueOf(record.getDepartment()));
            if (dept != null) {
                String key = dept.getParentId();
                String key2 = StringUtilsV2.linkStrIgnoreZero(Separator.COMMA_SIGN, dept.getParentId(),
                        dept.getId().toString());
                if (DepartmentType.isTeam(dept.getDeptType())) {
                    record.setDepartmentId(key);
                    record.setDepartment(key2);
                } else {
                    record.setDepartmentId(key2);
                    record.setDepartment(null);
                }
            }
        }
        Page<User> pager = getBaseMapper().pageBy(new Page<>(pageNum, pageSize), record);
        if (pager.getTotal() > 0) {
            pager.getRecords().forEach(r -> {
                if (CommonUtils.isNotEmpty(r.getDepartmentId(), record.getDepartmentId()) && !r.getDepartmentId()
                        .startsWith(record.getDepartmentId())) {
                    String[] deptIds = r.getDepartment().split("\\|");
                    r.setDepartmentName("");
                    for (String deptId : deptIds) {
                        //                        userDepartmentPositionMapper.findDepartmentPositionByUserIdLikeDepartmentId(r.getId(), deptId);
                        Department dept = departmentManage.get(StringUtilsV2.lastId(deptId));
                        String pn = StringUtilsV2.linkStr(Separator.COMMA_SIGN, dept.getParentName(), dept.getName());
                        r.setDepartmentName(StringUtilsV2.linkStr(Separator.VERTICAL_LINE, r.getDepartmentName(), pn));
                    }
                    r.setDepartment("1");
                } else {
                    r.setDepartment("0");
                }
                
            });
        }
        return pager;
    }
    
    @Override
    public List<DepartmentDuty> findDepartmentDuty(Long id) {
        List<DepartmentDuty> records = userDepartmentPositionMapper.findDepartmentPositionByUserId(id);
        records.forEach(e -> {
            List<String> names = departmentService.findNames(
                    StringUtilsV2.parseIds(CommonUtils.isEmpty(e.getOrgId()) ? e.getDepartment() : e.getOrgId()));
            e.setDepartmentName(String.join(",", names));
            e.setDutyName(positionService.getById(e.getDuty()).getName());
        });
        return records;
    }
    
    @Override
    public List<Tenant> findTenantsByUsername(String username) {
        return getBaseMapper().findTenantsByUsername(username);
    }
    
    @Override
    public List<Project> findProjectsByUsernameAndTenantId(String userId, Long tenantId) {
        return userProjectMapper.findProjectsByUserIdAndTenantId(userId, tenantId);
    }
    @Override
    public List<Long> findProjectIdsByUserId(String username) {
        return userProjectMapper.findProjectIdsByUserId(username);
    }
    
    @Override
    public User getDefaultInfo(String username) {
        User user = this.findByUsername(username);
        if (user == null) {
            return null;
        }
        boolean hasChange = false;
        if (CommonUtils.isEmpty(user.getRoleId())) {
            List<Role> roles = roleService.findByUserId(user.getId());
            if (!roles.isEmpty()) {
                user.setRoleId(roles.get(0).getId());
                hasChange = true;
            }
        }
        if (CommonUtils.isEmpty(user.getTenantId())) {
            List<Tenant> list = getBaseMapper().findTenantsByUsername(username);
            if (!list.isEmpty()) {
                user.setTenantId(list.get(0).getId());
                user.setTenantName(list.get(0).getName());
                hasChange = true;
            }
        }
        if (hasChange) {
            super.updateById(user);
        }
        return user;
    }
    
    @Override
    public boolean existsRole(Long userId, Long roleId) {
        return userRoleMapper.exists(userId, roleId);
    }
    
    @Override
    public User getByUserId(String userId) {
        if (CommonUtils.isEmpty(userId)) {
            return null;
        }
        User record = new User();
        record.setUserId(userId);
        return super.getOne(Wrappers.query(record));
    }
}
