package com.ch.cloud.upms.controller;

import com.alibaba.fastjson.JSONObject;
import com.ch.Constants;
import com.ch.NumS;
import com.ch.cloud.client.UpmsClientService;
import com.ch.cloud.client.dto.PermissionDto;
import com.ch.cloud.client.dto.RoleDto;
import com.ch.cloud.client.dto.UserDto;
import com.ch.cloud.upms.model.StMenu;
import com.ch.cloud.upms.model.StPermission;
import com.ch.cloud.upms.model.StRole;
import com.ch.cloud.upms.model.StUser;
import com.ch.cloud.upms.pojo.Menu;
import com.ch.cloud.upms.service.IMenuService;
import com.ch.cloud.upms.service.IPermissionService;
import com.ch.cloud.upms.service.IRoleService;
import com.ch.cloud.upms.service.IUserService;
import com.ch.result.Result;
import com.ch.result.ResultUtils;
import com.ch.utils.CommonUtils;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * desc:
 *
 * @author zhimin
 * @date 2019/4/22 8:42 PM
 */
@RestController
public class ClientController implements UpmsClientService {


    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IMenuService menuService;
    @Autowired
    private IPermissionService permissionService;


    @Override
    @GetMapping({"user/{username}/info"})
    public Result<UserDto> findUserByUsername(@PathVariable String username) {
        return ResultUtils.wrapFail(() -> {
            StUser user = userService.findByUsername(username);
            if (user == null) return null;
            UserDto dto = new UserDto();
            dto.setId(user.getId());
            dto.setUsername(username);
            dto.setPassword(user.getPassword());
            return dto;
        });
    }

    @GetMapping({"user/{id}/role"})
    public Result<RoleDto> findRoleByUserId(@PathVariable Long id) {
        return ResultUtils.wrapList(() -> {
            List<StRole> list = roleService.findByUserId(id);
            if (list.isEmpty()) return Lists.newArrayList();
            return list.stream().map(r -> {
                RoleDto dto = new RoleDto();
                BeanUtils.copyProperties(r, dto);
                return dto;
            }).collect(Collectors.toList());
        });
    }

    @Override
    public Result<String> findRoleCodeByUserId(Long userId) {
        return ResultUtils.wrapList(() -> {
            List<StRole> list = roleService.findByUserId(userId);
            if (list.isEmpty()) return Lists.newArrayList();
            return list.stream().map(StRole::getCode).collect(Collectors.toList());
        });
    }


    @GetMapping({"user/{userId}/permission"})
    public Result<PermissionDto> findPermissionByUserId(@PathVariable Long userId) {
        //TODO get permission by user id
        StUser user = userService.find(userId);
        StRole role = roleService.findDefault(user.getUsername());
        return this.findPermissionByRoleId(role.getId());
    }

    @GetMapping({"role/{roleId}/menu"})
    @Override
    public Result<PermissionDto> findMenuByRoleId(@PathVariable Long roleId) {
        return ResultUtils.wrapList(() -> this.findPermissionByTypeAndRoleId(Lists.newArrayList("1", "2"), roleId));
    }

    @GetMapping({"role/{roleId}/permission"})
    @Override
    public Result<PermissionDto> findPermissionByRoleId(@PathVariable Long roleId) {
        return ResultUtils.wrapList(() -> this.findPermissionByTypeAndRoleId(Lists.newArrayList("3", "4"), roleId));
    }

    private List<PermissionDto> findPermissionByTypeAndRoleId(List<String> types, Long roleId) {
        List<StPermission> permissions = permissionService.findByTypeAndRoleId(types, roleId);
        if (permissions.isEmpty()) return Lists.newArrayList();
        return permissions.stream().map(r -> {
            PermissionDto dto = new PermissionDto();
            BeanUtils.copyProperties(r, dto);
            dto.setPid(r.getParentId());
            if (dto.getSort() == null) dto.setSort(0);
            return dto;
        }).collect(Collectors.toList());
    }

    @GetMapping({"user/{userId}/permissionUrl"})
    @Override
    public Result<String> findPermissionUrlByUserId(@PathVariable Long userId) {
        return ResultUtils.wrapList(() -> {
            StUser user = userService.find(userId);
            StRole role = roleService.findDefault(user.getUsername());
            List<StMenu> permissions = menuService.findPermissionByRoleId(role.getId());
            if (permissions.isEmpty()) return Lists.newArrayList();
            return permissions.stream().map(StMenu::getUrl).collect(Collectors.toList());
        });
    }


    @GetMapping({"user/info"})
    public Result<?> getAuthInfo(@RequestHeader(Constants.TOKEN_USER) String username) {
        return ResultUtils.wrapFail(() -> {
            JSONObject info = new JSONObject();
            StUser user = userService.findByUsername(username);

            info.put("user", covertUserVo(user));
            StRole role = roleService.findDefault(username);
            if (role == null) return info;
            List<Menu> menus;
            List<StMenu> routers;
            List<StMenu> permissions;
            if (CommonUtils.isEquals(role.getType(), NumS._0)) {
                menus = menuService.findMenuTreeByRoleId(0L);
                routers = menuService.findRouterByRoleId(0L);
                permissions = menuService.findPermissionByRoleId(0L);
            } else {
                menus = menuService.findMenuTreeByRoleId(role.getId());
                routers = menuService.findRouterByRoleId(role.getId());
                permissions = menuService.findPermissionByRoleId(role.getId());
            }
            info.put("menus", menus);
            info.put("routers", covertMenuVo(routers));
            info.put("permissions", covertMenuVo(permissions));
            return info;
        });
    }

    private JSONObject covertUserVo(StUser user) {
        if (user == null) return new JSONObject();
        JSONObject info = new JSONObject();
        info.put("name", user.getUsername());
        info.put("realName", user.getRealName());
        info.put("lastLoginTime", user.getLastLoginAt());
        return info;
    }

    private List<JSONObject> covertMenuVo(List<StMenu> menus) {
        if (CommonUtils.isEmpty(menus)) return Lists.newArrayList();
        return menus.stream().map(r -> {
            JSONObject info = new JSONObject();
            info.put("code", r.getCode());
            info.put("name", r.getName());
            info.put("url", r.getUrl());
            return info;
        }).collect(Collectors.toList());
    }


    @PostMapping("menu")
    public Result<Menu> menu(@RequestHeader(Constants.TOKEN_USER) String username) {
        logger.debug("fetch menu by username: {}", username);
        return ResultUtils.wrapList(() -> {
            StRole role = roleService.findDefault(username);
            List<Menu> menus = Lists.newArrayList();
            if (role == null) return menus;
            if (CommonUtils.isEquals(role.getType(), NumS._0)) {
                menus = menuService.findMenuTreeByRoleId(0L);
            } else {
                menus = menuService.findMenuTreeByRoleId(role.getId());
            }
            return menus;
        });
    }

}
