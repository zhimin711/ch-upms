package com.ch.cloud.upms.user.controller;

import com.ch.cloud.upms.user.pojo.PermissionDto;
import com.ch.result.Result;
import com.ch.result.ResultUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * desc:
 *
 * @author zhimin
 * @since 2025/07/01 10:40 PM
 */
@RestController
@RequestMapping("/interface")
@Tag(name = "开放接口管理", description = "开放接口管理")
public class OpenApiController {
    
    
    @Operation(summary = "获取项目模块分组", description = "获取项目模块")
    @GetMapping({"modules"})
    public Result<PermissionDto> modules(@RequestParam Long projectId) {
        return ResultUtils.wrap(() -> {
        
        });
    }
    @Operation(summary = "获取开放接口列表", description = "获取开放列表")
    @GetMapping({"interfaces"})
    public Result<PermissionDto> interfaces(@RequestParam(required = false) Long moduleId) {
        return ResultUtils.wrap(() -> {
        
        });
    }
    
    
    @Operation(summary = "获取开放接口详情", description = "获取开放接口详情")
    @GetMapping({"detail"})
    public Result<PermissionDto> detail(@RequestParam(required = false) Long interfaceId) {
        return ResultUtils.wrap(() -> {
        
        });
    }
    
    
}
