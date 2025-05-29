package com.ch.cloud.upms.client;

import com.ch.cloud.upms.dto.PermissionDto;
import com.ch.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * decs:通用用户权限客户端接口
 *
 * @author 01370603
 * @since 2019/5/28
 */
@FeignClient(name = "${feign.client.upms:ch-upms}", url = "${feign.client.upmsUrl:}", path = "permission", contextId = "upmsPermissionClient")
public interface UpmsPermissionClient {

    @GetMapping({"hidden"})
    Result<PermissionDto> hidden();

    @GetMapping({"whitelist"})
    Result<PermissionDto> whitelist();

    @GetMapping({"cookie"})
    Result<PermissionDto> cookie();
}
