package com.ch.cloud.upms.client;

import com.ch.cloud.upms.dto.PermissionDto;
import com.ch.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * decs:通用用户权限客户端接口
 *
 * @author 01370603
 * @date 2019/5/28
 */
@FeignClient(name = "${feign.client.upms:ch-upms}", contextId = "roleClientService", path = "role")
public interface UpmsRoleClientService {

    @GetMapping({"{roleId:[0-9]+}/menus"})
    Result<PermissionDto> findMenusByRoleId(@PathVariable Long roleId);

    @GetMapping({"{roleId:[0-9]+}/permissions"})
    Result<PermissionDto> findPermissionsByRoleId(@PathVariable Long roleId, @RequestParam(value = "types", required = false) String typesStr);


}
