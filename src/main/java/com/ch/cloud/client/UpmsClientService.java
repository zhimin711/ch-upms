package com.ch.cloud.client;

import com.ch.cloud.client.dto.*;
import com.ch.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * decs:通用用户权限客户端接口
 *
 * @author 01370603
 * @date 2019/5/28
 */

@FeignClient(name = UpmsConstants.NAME)
public interface UpmsClientService {

    @GetMapping("user/{username}/login")
    Result<LoginUserDto> findUserByUsername(@PathVariable String username);

    @GetMapping("user/{username}/info")
    Result<UserDto> findInfo2(@PathVariable String username);

    @GetMapping({"user/{username}/role"})
    Result<RoleDto> findRoleByUsername(@PathVariable String username);

    @GetMapping({"user/{username}/tenants"})
    Result<TenantDto> findTenantsByUserId(@PathVariable String username);

    @PostMapping({"user/roles"})
    Result<RoleDto> findRolesByUserId2(@RequestParam Long userId);

    @GetMapping({"user/{userId:[0-9]+}/roles"})
    Result<RoleDto> findRolesByUserId(@PathVariable Long userId);

    @GetMapping({"role/{roleId:[0-9]+}/menus"})
    Result<PermissionDto> findMenusByRoleId(@PathVariable Long roleId);

    @GetMapping({"role/{roleId:[0-9]+}/permissions"})
    Result<PermissionDto> findPermissionsByRoleId(@PathVariable Long roleId, @RequestParam(value = "types", required = false) String typesStr);

    @GetMapping({"permission/hidden"})
    Result<PermissionDto> findHiddenPermissions();

}
