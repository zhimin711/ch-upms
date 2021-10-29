package com.ch.cloud.client;

import com.ch.cloud.client.dto.PermissionDto;
import com.ch.cloud.client.dto.RoleDto;
import com.ch.cloud.client.dto.UserDto;
import com.ch.cloud.client.dto.TenantDto;
import com.ch.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * decs:通用用户权限客户端接口
 *
 * @author 01370603
 * @date 2019/5/28
 */

@FeignClient(name = UpmsConstants.NAME)
public interface UpmsClientService {

    @GetMapping("user/{username}/info")
    Result<UserDto> findUserByUsername(@PathVariable String username);

    @GetMapping({"user/{username}/role"})
    Result<RoleDto> findRoleByUsername(@PathVariable String username);

    @GetMapping({"user/{username}/tenants"})
    Result<TenantDto> findTenantsByUserId(@PathVariable String username);

    @GetMapping({"user/{userId}/roles"})
    Result<RoleDto> findRolesByUserId(@PathVariable Long userId);

    @GetMapping({"role/{roleId}/menus"})
    Result<PermissionDto> findMenusByRoleId(@PathVariable Long roleId);

    @GetMapping({"role/{roleId}/permissions"})
    Result<PermissionDto> findPermissionsByRoleId(@PathVariable Long roleId);

    @GetMapping({"permission/hidden"})
    Result<PermissionDto> findHiddenPermissions();

}
