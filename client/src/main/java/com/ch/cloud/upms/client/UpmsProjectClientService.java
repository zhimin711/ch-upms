package com.ch.cloud.upms.client;

import com.ch.cloud.upms.dto.ProjectDto;
import com.ch.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * decs:通用用户权限客户端接口
 *
 * @author 01370603
 * @date 2019/5/28
 */

@FeignClient(name = "${feign.client.upms:ch-upms}", contextId = "projectClientService", path = "projects")
public interface UpmsProjectClientService {

//    @GetMapping({"{id:[0-9]+}/users"})
//    Result<UserDto> findUsers(@PathVariable Long id);


    @PostMapping
    Result<ProjectDto> findByIds(@RequestBody List<Long> ids);
}
