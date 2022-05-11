package com.ch.cloud.upms.client;

import com.ch.cloud.upms.dto.PermissionDto;
import com.ch.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * decs:通用用户权限客户端接口
 *
 * @author 01370603
 * @date 2019/5/28
 */
@FeignClient(name = "${feign.client.upms:ch-upms}", contextId = "permissionClientService", path = "permission")
public interface UpmsPermissionClientService {

    @GetMapping({"hidden"})
    Result<PermissionDto> findHiddenPermissions();

}
