package com.ch.cloud.upms.user.controller;

import com.ch.Constants;
import com.ch.cloud.sso.client.SsoPasswordClient;
import com.ch.cloud.upms.dto.TenantDto;
import com.ch.cloud.upms.mapstrut.MapperTenant;
import com.ch.cloud.upms.mq.sender.GatewayNotifySender;
import com.ch.cloud.upms.service.IUserService;
import com.ch.cloud.upms.user.manager.UserQueryManager;
import com.ch.cloud.upms.user.model.Project;
import com.ch.cloud.upms.user.model.Role;
import com.ch.cloud.upms.user.model.Tenant;
import com.ch.cloud.upms.user.model.User;
import com.ch.cloud.upms.user.pojo.UserInfo;
import com.ch.cloud.upms.user.vo.TenantChangeVO;
import com.ch.cloud.upms.utils.RequestUtils;
import com.ch.e.Assert;
import com.ch.e.ExUtils;
import com.ch.e.PubError;
import com.ch.pojo.KeyValue;
import com.ch.pojo.VueRecord2;
import com.ch.result.Result;
import com.ch.result.ResultUtils;
import com.ch.toolkit.ContextUtil;
import com.ch.utils.CommonUtils;
import com.ch.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    private UserQueryManager userQueryManager;
    
    @Autowired
    private SsoPasswordClient ssoPasswordClient;
    
    @Autowired
    private GatewayNotifySender gatewayNotifySender;
    
    @PostMapping("changePwd")
    public Result<Integer> changePwd(@RequestBody KeyValue keyValue) {
        return ResultUtils.wrapFail(() -> {
            String username = RequestUtils.getHeaderUser();
            User user = userService.findByUsername(username);
            if (user == null) {
                ExUtils.throwError(PubError.NOT_EXISTS);
            }
            Result<Boolean> res = ssoPasswordClient.matchEncrypt(keyValue.getKey(), user.getPassword());
            if (!res.isSuccess() || !res.get()) {
                ExUtils.throwError(PubError.USERNAME_OR_PASSWORD, "原密码错误，请输入正确密码!");
            }
            Result<String> res1 = ssoPasswordClient.encrypt(keyValue.getValue());
            user.setPassword(res1.get());
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
            if (user == null) {
                ExUtils.throwError(PubError.NOT_EXISTS, "用户不存在: " + username);
            }
            boolean exists = userService.existsRole(user.getId(), role.getId());
            if (!exists) {
                ExUtils.throwError(PubError.NOT_EXISTS, role.getId());
            }
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
            Assert.notNull(user, PubError.NOT_EXISTS, "用户不存在: " + username);
            List<Tenant> tenants = userService.findTenantsByUsername(username);
            Assert.notEmpty(tenants, PubError.NOT_EXISTS, username + "用户租户");
            List<Long> existsTenantIds = tenants.stream().map(Tenant::getId).collect(Collectors.toList());
            Assert.isTrue(existsTenantIds.contains(record.getId()), PubError.NOT_AUTH, "租户" + record.getId());
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
            return MapperTenant.INSTANCE.toClientDto(tenant);
        });
    }
    
    @GetMapping("valid")
    public Result<UserInfo> findValid(@RequestParam(value = "name", required = false) String idOrUsernameOrRealName) {
        return ResultUtils.wrapList(() -> userQueryManager.findAllValid(idOrUsernameOrRealName));
    }
    
    
    @GetMapping({"tenants"})
    public Result<TenantDto> findTenants() {
        return ResultUtils.wrapList(() -> {
            String username = ContextUtil.getUsername();
            if (CommonUtils.isEmpty(username)) {
                return null;
            }
            List<Tenant> tenantList = userService.findTenantsByUsername(username);
            return tenantList.stream().map(MapperTenant.INSTANCE::toClientDto).collect(Collectors.toList());
        });
    }
    
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
