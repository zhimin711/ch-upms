package com.ch.cloud.upms.controller;

import com.ch.Constants;
import com.ch.cloud.upms.dto.TenantDto;
import com.ch.cloud.upms.manage.IUserManage;
import com.ch.cloud.upms.model.Project;
import com.ch.cloud.upms.model.Role;
import com.ch.cloud.upms.model.Tenant;
import com.ch.cloud.upms.model.User;
import com.ch.cloud.upms.mq.sender.GatewayNotifySender;
import com.ch.cloud.upms.pojo.UserInfo;
import com.ch.cloud.upms.service.IUserService;
import com.ch.cloud.upms.utils.RequestUtils;
import com.ch.cloud.upms.vo.TenantChangeVO;
import com.ch.e.Assert;
import com.ch.e.PubError;
import com.ch.pojo.KeyValue;
import com.ch.pojo.VueRecord2;
import com.ch.result.Result;
import com.ch.result.ResultUtils;
import com.ch.utils.AssertUtils;
import com.ch.utils.CharUtils;
import com.ch.utils.CommonUtils;
import com.ch.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/*
 *
 * 用户管理
 *
 * @author 01370603
 * @since 2018/12/22 22:35
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private IUserService userService;
    
    @Autowired
    private IUserManage userManage;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    
    @Autowired
    private GatewayNotifySender gatewayNotifySender;
    
    @PostMapping("changePwd")
    public Result<Integer> changePwd(@RequestBody KeyValue keyValue) {
        return ResultUtils.wrapFail(() -> {
            String username = RequestUtils.getHeaderUser();
            User user = userService.findByUsername(username);
            Assert.notNull(user, PubError.NOT_EXISTS, "用户: " + username);
            boolean ok = encoder.matches(keyValue.getKey(), user.getPassword());
            Assert.isTrue(ok, PubError.USERNAME_OR_PASSWORD, "原密码错误，请输入正确密码!");
            user.setPassword(encoder.encode(keyValue.getValue()));
            user.setUpdateBy(username);
            user.setUpdateAt(DateUtils.current());
            return userService.updatePassword(user);
        });
    }
    
    
    @PostMapping("changeRole")
    public Result<Boolean> changDefaultRole(@RequestBody Role role) {
        return ResultUtils.wrapFail(() -> {
            String username = RequestUtils.getHeaderUser();
            User user = userService.findByUsername(username);
            Assert.notNull(user, PubError.NOT_EXISTS, "用户: " + username);
            boolean exists = userService.existsRole(user.getId(), role.getId());
            Assert.isTrue(exists, PubError.NOT_EXISTS, "角色: " + role.getId());
            User updateUser = new User();
            updateUser.setRoleId(role.getId());
            return userService.updateById(updateUser);
        });
    }
    
    @PostMapping("changeTenant")
    public Result<TenantDto> changTenant(@RequestHeader(Constants.X_TOKEN) String token,
            @RequestBody TenantChangeVO record) {
        return ResultUtils.wrapFail(() -> {
            String username = RequestUtils.getHeaderUser();
            User user = userService.findByUsername(username);
            AssertUtils.isNull(user, PubError.NOT_EXISTS, "用户不存在: " + username);
            List<Tenant> tenants = userService.findTenantsByUsername(username);
            AssertUtils.isEmpty(tenants, PubError.NOT_EXISTS, username + "用户租户");
            List<Long> existsTenantIds = tenants.stream().map(Tenant::getId).collect(Collectors.toList());
            AssertUtils.isFalse(existsTenantIds.contains(record.getId()), PubError.NOT_AUTH, "租户" + record.getId());
            Tenant tenant = tenants.stream().filter(e -> CommonUtils.isEquals(record.getId(), e.getId())).findFirst()
                    .orElse(new Tenant());
            if (record.isSetDefault() && !CommonUtils.isEquals(record.getId(), user.getTenantId())) {
                User updateUser = new User();
                updateUser.setId(user.getId());
                updateUser.setTenantId(record.getId());
                updateUser.setTenantName(tenant.getName());
                userService.updateById(updateUser);
            }
            gatewayNotifySender.cleanNotify(new KeyValue("users", token));
            TenantDto dto = new TenantDto();
            dto.setId(tenant.getId());
            dto.setName(tenant.getName());
            return dto;


        });
    }
    
    @GetMapping("valid")
    public Result<UserInfo> findValid(@RequestParam(value = "name", required = false) String idOrUsernameOrRealName) {
        return ResultUtils.wrapList(() -> {
            List<User> users;
            if (CommonUtils.isEmpty(idOrUsernameOrRealName)) {
                users = userService.findAllValid();
            } else if (CommonUtils.isNumeric(idOrUsernameOrRealName)) {
                users = userService.findByLikeUserId(idOrUsernameOrRealName);
            } else if (CharUtils.containsChinese(idOrUsernameOrRealName)) {
                users = userService.findByLikeRealName(idOrUsernameOrRealName);
            } else {
                users = userService.findByLikeUsername(idOrUsernameOrRealName);
            }
            return users.stream().map(r -> {
                UserInfo info = new UserInfo();
                BeanUtils.copyProperties(r, info);
                return info;
            }).collect(Collectors.toList());
        });
    }
/*
    @GetMapping({"tenants"})
    public Result<TenantDto> findTenants() {
        return ResultUtils.wrapList(() -> {
            String username = RequestUtils.getHeaderUser();
            if (CommonUtils.isEmpty(username)) {
                return null;
            }
            List<Tenant> tenantList = userService.findTenantsByUsername(RequestUtils.getHeaderUser());
            return tenantList.stream().map(e -> {
                TenantDto record = new TenantDto();
                record.setId(e.getId());
                record.setName(e.getName());
                record.setDeptId(e.getDepartmentId());
                return record;
            }).collect(Collectors.toList());
        });
    }*/
    
    @GetMapping({"tenant/{tenantId:[0-9]+}/projects"})
    public Result<VueRecord2> findProjects(@PathVariable Long tenantId) {
        return ResultUtils.wrapList(() -> {
            List<Project> projectList = userService.findProjectsByUsernameAndTenantId(RequestUtils.getHeaderUser(),
                    tenantId);
            return projectList.stream().map(e -> {
                VueRecord2 record = new VueRecord2();
                record.setValue(e.getId() + "");
                record.setLabel(e.getName());
                record.setKey(e.getCode() + "");
                return record;
            }).collect(Collectors.toList());
        });
    }
}
