package com.ch.cloud.upms.client;

import com.ch.cloud.upms.dto.ProjectDto;
import com.ch.result.PageResult;
import com.ch.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * decs:通用用户权限客户端接口
 *
 * @author 01370603
 * @since 2019/5/28
 */

@FeignClient(name = "${feign.client.upms:ch-upms}", contextId = "projectClientService", path = "projects")
public interface UpmsProjectClientService {

//    @GetMapping({"{id:[0-9]+}/users"})
//    Result<UserDto> findUsers(@PathVariable Long id);


    @GetMapping("page/{num:[0-9]+}/{size:[0-9]+}")
    PageResult<ProjectDto> page(@PathVariable(value = "num") int pageNum,
                                @PathVariable(value = "size") int pageSize,
                                @RequestParam(value = "code", required = false) String code,
                                @RequestParam(value = "name", required = false) String name,
                                @RequestParam(value = "tenantName", required = false) String tenantName);

    @GetMapping("list")
    Result<ProjectDto> list(@RequestParam(value = "name", required = false) String name,
                            @RequestParam(value = "code", required = false) String code,
                            @RequestParam(value = "tenant", required = false) String tenant);

    @GetMapping("info")
    Result<ProjectDto> infoByIdOrCode(@RequestParam(value = "id", required = false) Long id, @RequestParam(value = "code", required = false) String code);

    @PostMapping("info")
    Result<ProjectDto> infoByIds(@RequestBody List<Long> ids);

}
