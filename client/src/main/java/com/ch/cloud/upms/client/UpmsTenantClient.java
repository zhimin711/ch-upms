package com.ch.cloud.upms.client;

import com.ch.cloud.upms.dto.ProjectDto;
import com.ch.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * decs:通用用户权限客户端接口
 *
 * @author 01370603
 * @since 2019/5/28
 */

@FeignClient(name = "${feign.client.upms:ch-upms}", url = "${feign.client.upmsUrl:}", path = "tenant", contextId = "upmsTenantClient")
public interface UpmsTenantClient {

    @GetMapping({"{id:[0-9]+}/projects"})
    Result<ProjectDto> findProjects(@PathVariable Long id);

    @GetMapping({"{id:[0-9]+}/has-admin"})
    Result<Boolean> hasAdmin(@PathVariable Long id, @RequestParam("username") String username);
}
