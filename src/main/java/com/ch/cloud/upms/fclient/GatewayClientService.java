package com.ch.cloud.upms.fclient;

import com.ch.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "${ch.gateway}")
public interface GatewayClientService {

    @GetMapping("clean/{role:[0-9]+}/permissions")
    Result<Boolean> cleanRolePermissions(@PathVariable("role") Long roleId);

    @GetMapping("clean/permissions")
    Result<Boolean> cleanPermissions();

}
