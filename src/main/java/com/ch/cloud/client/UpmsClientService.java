package com.ch.cloud.client;

import com.ch.cloud.client.dto.UserDto;
import com.ch.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * decs:通用用户权限客户端接口
 *
 * @author 01370603
 * @date 2019/5/28
 */

public interface UpmsClientService {

    @GetMapping("user/{username}/info")
    Result<UserDto> findUserByUsername(@PathVariable("username") String username);

    @GetMapping("user/{id}/role")
    Result<String> findRoleByUserId(@PathVariable("id") Long userId);

    @GetMapping("user/{id}/permission")
    Result<String> findPermissionByUserId(@PathVariable("id") Long userId);
}
