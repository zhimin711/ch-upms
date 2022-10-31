package com.ch.cloud.upms.controller.fclient;

import com.alibaba.fastjson2.JSON;
import com.ch.cloud.upms.client.UpmsUserClientService;
import com.ch.cloud.upms.dto.LoginUserDto;
import com.ch.cloud.upms.dto.ProjectRoleDto;
import com.ch.cloud.upms.dto.RoleDto;
import com.ch.cloud.upms.dto.TenantDto;
import com.ch.cloud.upms.dto.UserDto;
import com.ch.cloud.upms.enums.RoleType;
import com.ch.cloud.upms.manage.IProjectManage;
import com.ch.cloud.upms.manage.IUserManage;
import com.ch.cloud.upms.model.Project;
import com.ch.cloud.upms.model.Role;
import com.ch.cloud.upms.model.Tenant;
import com.ch.cloud.upms.service.IProjectService;
import com.ch.cloud.upms.service.IRoleService;
import com.ch.cloud.upms.service.IUserService;
import com.ch.cloud.web.annotation.OriginalReturn;
import com.ch.e.PubError;
import com.ch.result.Result;
import com.ch.result.ResultUtils;
import com.ch.utils.AssertUtils;
import com.ch.utils.BeanUtilsV2;
import com.ch.utils.CommonUtils;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
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
@RequestMapping("/c/user")
public class UserClientController implements UpmsUserClientService {

    @Autowired
    private IUserService userService;

    @Autowired
    private IUserManage userManage;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IProjectService projectService;

    @Autowired
    private IProjectManage projectManage;

    @Override
    @GetMapping("info")
    public Result<UserDto> info(@RequestParam(required = false) String userId,
                                @RequestParam(required = false) String username) {
        return ResultUtils.wrap(() -> {
            AssertUtils.isTrue(CommonUtils.isEmpty(userId) && CommonUtils.isEmpty(username), PubError.ARGS,
                    "userId and username");
            if (CommonUtils.isNotEmpty(userId)) {
                return userManage.getByUserId(userId);
            }
            return userManage.getByUsername(username);
        });
    }

    @Override
    @GetMapping("{username}/login")
    public Result<LoginUserDto> loginByUsername(@PathVariable String username) {
        return ResultUtils.wrapFail(() -> {
            UserDto user = userManage.getByUsername(username);
            return BeanUtilsV2.clone(user, LoginUserDto.class);
        });
    }

    @Override
    @GetMapping({"{username}/info"})
    public Result<UserDto> findInfoByUsername(@PathVariable String username) {
        return ResultUtils.wrap(() -> userManage.getByUsername(username));
    }

    @Override
    @GetMapping({"roles"})
    public Result<RoleDto> findRolesByUsername(@RequestParam String username) {
        return ResultUtils.wrapList(() -> {
            UserDto user = userManage.getByUsername(username);
            AssertUtils.isNull(user, PubError.NOT_EXISTS, username);
            List<Role> records = roleService.findByUserId(user.getId());
            log.info("current user {} roles: {}", username, JSON.toJSONString(records));
            return records.stream().map(e -> BeanUtilsV2.clone(e, RoleDto.class)).collect(Collectors.toList());
        });
    }

    @Override
    @GetMapping({"{username}/tenants"})
    public Result<TenantDto> findTenantsByUsername(@PathVariable String username) {
        return ResultUtils.wrapList(() -> {
            List<Tenant> tenantList = userService.findTenantsByUsername(username);
            return tenantList.stream().map(e -> {
                TenantDto record = new TenantDto();
                record.setId(e.getId());
                record.setName(e.getName());
                record.setDeptId(e.getDepartmentId());
                return record;
            }).collect(Collectors.toList());
        });
    }

    @GetMapping({"{userId:[0-9]+}/projects"})
    @Override
    public Result<ProjectRoleDto> findProjectsByUserId(@PathVariable String userId) {
        return ResultUtils.wrap(() -> {
            UserDto user = userManage.getByUserId(userId);
            AssertUtils.isNull(user, PubError.NOT_EXISTS, userId);
            List<ProjectRoleDto> list = userService.findProjectRoleByUserId(user.getUsername());
            if (list.isEmpty()) {
                return null;
            }
            list.forEach(e -> {
                Project p = projectManage.getById(e.getId());
                BeanUtils.copyProperties(p, e);
            });
            return list;
        });
    }

    @GetMapping("{userId:[0-9]+}/project/{projectId:[0-9]+}/roles")
    @OriginalReturn
    @Override
    public List<RoleType> listProjectRoles(@PathVariable String userId, @PathVariable Long projectId, @RequestParam(required = false) String roles) {
        UserDto user = userManage.getByUserId(userId);
//        AssertUtils.isNull(user, PubError.NOT_EXISTS, userId);
        if (user == null) return Lists.newArrayList();
        List<String> list = userService.listProjectRoleByUserIdAndProjectId(user.getUsername(), projectId, roles);

        return list.stream().map(RoleType::fromName).collect(Collectors.toList());
    }

    @GetMapping("{userId:[0-9]+}/project-role")
    @OriginalReturn
    @Override
    public Boolean existsProjectRole(@PathVariable String userId, Long projectId, RoleType role) {
        UserDto user = userManage.getByUserId(userId);
        if (user == null) return false;
        return userService.existsProjectRole(user.getUsername(), projectId, role);
    }
}
