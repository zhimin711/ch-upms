package com.ch.cloud.upms.controller.f;

import com.ch.cloud.upms.model.Role;
import com.ch.cloud.upms.model.Tenant;
import com.ch.cloud.upms.model.User;
import com.ch.cloud.client.dto.TenantDto;
import com.ch.cloud.upms.service.IRoleService;
import com.ch.cloud.upms.service.IUserService;
import com.ch.result.Result;
import com.ch.result.ResultUtils;
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

    @GetMapping("{username}/info")
    public Result<User> findByUsername(@PathVariable String username) {
        return ResultUtils.wrapFail(() -> userService.findByUsername(username));
    }

    @GetMapping({"{username}/role"})
    public Result<Role> findRolesByUsername(@PathVariable String username) {
        return ResultUtils.wrap(() -> roleService.getCurrent(username));
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
