package com.ch.cloud.upms.manage.impl;

import cn.hutool.core.util.RandomUtil;
import com.ch.StatusS;
import com.ch.cloud.upms.dto.UserDto;
import com.ch.cloud.upms.manage.IUserManage;
import com.ch.cloud.upms.service.IDepartmentService;
import com.ch.cloud.upms.service.IPositionService;
import com.ch.cloud.upms.service.IRoleService;
import com.ch.cloud.upms.service.ITenantService;
import com.ch.cloud.upms.service.IUserService;
import com.ch.cloud.upms.user.model.Department;
import com.ch.cloud.upms.user.model.Position;
import com.ch.cloud.upms.user.model.Role;
import com.ch.cloud.upms.user.model.Tenant;
import com.ch.cloud.upms.user.model.User;
import com.ch.core.utils.StrUtil;
import com.ch.e.Assert;
import com.ch.e.PubError;
import com.ch.utils.BeanUtilsV2;
import com.ch.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * <p>
 * desc: UserManageImpl
 * </p>
 *
 * @author Zhimin.Ma
 * @since 2022/8/18
 */
@Service
public class UserManageImpl implements IUserManage {
    
    @Autowired
    private IUserService userService;
    
    @Autowired
    private IRoleService roleService;
    
    @Autowired
    private IDepartmentService departmentService;
    
    @Autowired
    private IPositionService positionService;
    
    @Autowired
    private ITenantService tenantService;
    
    @Cacheable(cacheNames = "user", key = "#id")
    @Override
    public UserDto getById(Long id) {
        User user = userService.getById(id);
        return BeanUtilsV2.clone(user, UserDto.class);
    }
    
    @Cacheable(cacheNames = "user", key = "#userId")
    @Override
    public UserDto getByUserId(String userId) {
        User user = userService.getByUserId(userId);
        return BeanUtilsV2.clone(user, UserDto.class);
    }
    
    
    @Cacheable(cacheNames = "user", key = "#username")
    @Override
    public UserDto getByUsername(String username) {
        User user = userService.findByUsername(username);
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
            List<Tenant> list = userService.findTenantsByUsername(username);
            if (!list.isEmpty()) {
                user.setTenantId(list.get(0).getId());
                user.setTenantName(list.get(0).getName());
                hasChange = true;
            }
        }
        if (hasChange) {
            userService.updateById(user);
        }
        return BeanUtilsV2.clone(user, UserDto.class);
    }
    
    @Transactional(rollbackFor = Exception.class)
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
                    .forEach(r -> c.getAndAdd(userService.insertRole(id, r)));
            uRoleIds.stream().filter(r -> !roleIds.contains(r) && !uRoleIds2.contains(r))
                    .forEach(r -> c.getAndAdd(userService.deleteRole(id, r)));
        } else if (!uRoleIds.isEmpty()) {
            uRoleIds.forEach(r -> c.getAndAdd(userService.deleteRole(id, r)));
        }
        return c.get();
    }
    
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveWithAll(User record) {
        if (CommonUtils.isEmpty(record.getPassword())) {
            record.setPassword(RandomUtil.randomString(6));
        }
        record.setUserId(generateUserId());
        boolean c = userService.save(record);
        saveDepartmentPosition(record);
        userService.updateById(record);
        return c;
    }
    
    @Transactional
    @Override
    public boolean updateWithAll(User record) {
        // 不要清除userId，因为缓存key需要使用这个值
        String userId = record.getUserId();
        // 确保userId没有被清除
        record.setUserId(userId);
        String username = record.getUsername();
        record.setUsername(username);
        //不更新密码
        record.setPassword(null);
        saveDepartmentPosition(record);
        return userService.updateById(record);
    }
    
    private void saveDepartmentPosition(User record) {
        Assert.notEmpty(record.getDutyList(), PubError.NON_NULL, "组织职位");
        userService.deleteDepartmentPositionByUserId(record.getId());
        record.getDutyList().forEach(r -> {
            Assert.isFalse(CommonUtils.isEmpty(r.getDepartment()) || CommonUtils.isEmpty(r.getDuty()),
                    PubError.NON_NULL, "组织或职位");
            Department dept = departmentService.getById(StrUtil.lastId(r.getDepartment()));
            String deptId = r.getDepartment();
            String orgId = "";
            if (dept != null && dept.getDeptType() != null && dept.getDeptType().equals(3)) {
                deptId = dept.getParentId();
                orgId = r.getDepartment();
            }
            userService.insertDepartmentPosition(record.getId(), deptId, Long.valueOf(r.getDuty()), orgId);
        });
        if (CommonUtils.isNotEmpty(record.getDepartmentId())) {
            List<String> names = departmentService.findNames(StrUtil.parseIds(record.getDepartmentId()));
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
    
    /**
     * 生成用户ID，确保不以0开头
     * @return 10位数字的用户ID
     */
    private String generateUserId() {
        // 生成第一位数字，确保不为0
        String firstDigit = String.valueOf(RandomUtil.randomInt(1, 10));
        // 生成剩余9位数字
        String remainingDigits = RandomUtil.randomNumbers(9);
        return firstDigit + remainingDigits;
    }
    
}
