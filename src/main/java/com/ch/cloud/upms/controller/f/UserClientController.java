package com.ch.cloud.upms.controller.f;

import com.ch.cloud.client.dto.LoginUserDto;
import com.ch.cloud.client.dto.UserDto;
import com.ch.cloud.upms.model.Role;
import com.ch.cloud.upms.model.Tenant;
import com.ch.cloud.upms.model.User;
import com.ch.cloud.client.dto.TenantDto;
import com.ch.cloud.upms.service.IRoleService;
import com.ch.cloud.upms.service.IUserService;
import com.ch.result.Result;
import com.ch.result.ResultUtils;
import com.ch.utils.BeanUtilsV2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/*
 *
 * 用户管理
 *
 * @author 01370603
 * @date 2018/12/22 22:35
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserClientController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;

    @GetMapping("{username}/login")
    public Result<LoginUserDto> findByUsername(@PathVariable String username) {
        return ResultUtils.wrapFail(() -> {
            User user = userService.findByUsername(username);
            return BeanUtilsV2.clone(user, LoginUserDto.class);
        });
    }

    @GetMapping({"{username}/info"})
    public Result<UserDto> findInfo(@PathVariable String username) {
        return ResultUtils.wrap(() -> {
            User user = userService.getDefaultInfo(username);
            return BeanUtilsV2.clone(user, UserDto.class);
        });
    }

    @PostMapping({"roles"})
    public Result<Role> findRolesByUserId(@RequestParam Long userId) {
        return ResultUtils.wrapList(() -> roleService.findByUserId(userId));
    }

    @GetMapping({"{username}/tenants"})
    public Result<TenantDto> findTenants(@PathVariable String username) {
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

}
