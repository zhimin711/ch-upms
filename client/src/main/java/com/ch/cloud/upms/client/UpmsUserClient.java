package com.ch.cloud.upms.client;

import com.ch.cloud.upms.dto.LoginUserDto;
import com.ch.cloud.upms.dto.ProjectRoleDto;
import com.ch.cloud.upms.dto.RoleDto;
import com.ch.cloud.upms.dto.TenantDto;
import com.ch.cloud.upms.dto.UserDto;
import com.ch.cloud.upms.enums.RoleType;
import com.ch.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * decs:通用用户权限客户端接口
 *
 * @author 01370603
 * @since 2019/5/28
 */
@FeignClient(name = "${feign.client.upms:ch-upms}", url = "${feign.client.upmsUrl:}", path = "/c/user", contextId = "upmsUserClient")
public interface UpmsUserClient {

    @GetMapping("info")
    Result<UserDto> info(@RequestParam(required = false) String userId, @RequestParam(required = false) String username);

    @GetMapping("{username}/login")
    Result<LoginUserDto> loginByUsername(@PathVariable String username);

    @GetMapping("{username}/info")
    Result<UserDto> findInfoByUsername(@PathVariable String username);

//    @GetMapping({"{username}/role"})
//    Result<RoleDto> findRoleByUsername(@PathVariable String username);

    @GetMapping({"{username}/roles"})
    Result<RoleDto> findRolesByUsername(@PathVariable String username);

//    @GetMapping({"{userId:[0-9]+}/roles"})
//    Result<RoleDto> findRolesByUserId(@PathVariable Long userId);

    @GetMapping({"{username}/tenants"})
    Result<TenantDto> findTenantsByUsername(@PathVariable String username);

    @GetMapping({"{userId:[0-9]+}/projects"})
    Result<ProjectRoleDto> findProjectsByUserId(@PathVariable String userId);

    @GetMapping("{userId:[0-9]+}/project/{projectId:[0-9]+}/roles")
    List<RoleType> listProjectRoles(@PathVariable String userId, @PathVariable Long projectId, @RequestParam(required = false) String roles);

    @GetMapping("{userId:[0-9]+}/project-role")
    Boolean existsProjectRole(@PathVariable String userId, @RequestParam Long projectId, @RequestParam RoleType role);

}
