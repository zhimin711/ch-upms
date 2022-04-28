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
@FeignClient(name = "${feign.client.upms:ch-upms}", contextId = "permissionClientService", path = "permission")
public interface UpmsPermissionClientService {

    @GetMapping({"hidden"})
    Result<PermissionDto> findHiddenPermissions();

}
