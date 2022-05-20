package com.ch.cloud.upms.client;

import com.ch.cloud.upms.dto.ProjectDto;
import com.ch.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * decs:通用用户权限客户端接口
 *
 * @author 01370603
 * @since 2019/5/28
 */

@FeignClient(name = "${feign.client.upms:ch-upms}", contextId = "tenantClientService", path = "tenant")
public interface UpmsTenantClientService {

    @GetMapping({"{id:[0-9]+}/projects"})
    Result<ProjectDto> findProjects(@PathVariable Long id);


}
