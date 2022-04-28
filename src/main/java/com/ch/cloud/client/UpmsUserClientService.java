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
@FeignClient(name = "${feign.client.upms:ch-upms}", contextId = "userClientService", path = "user")
public interface UpmsUserClientService {

    @GetMapping("{username}/login")
    Result<LoginUserDto> findUserByUsername(@PathVariable String username);

    @GetMapping("{username}/info")
    Result<UserDto> findInfo2(@PathVariable String username);

    @GetMapping({"{username}/role"})
    Result<RoleDto> findRoleByUsername(@PathVariable String username);

    @GetMapping({"{username}/tenants"})
    Result<TenantDto> findTenantsByUserId(@PathVariable String username);

    @PostMapping({"roles"})
    Result<RoleDto> findRolesByUserId2(@RequestParam Long userId);

    @GetMapping({"{userId:[0-9]+}/roles"})
    Result<RoleDto> findRolesByUserId(@PathVariable Long userId);

    @GetMapping({"{userId:[0-9]+}/projects"})
    Result<RoleDto> findProjectsByUserId(@PathVariable Long userId);

}
