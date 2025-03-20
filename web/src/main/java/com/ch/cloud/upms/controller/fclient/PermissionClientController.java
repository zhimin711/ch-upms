package com.ch.cloud.upms.controller.fclient;

import com.ch.Num;
import com.ch.StatusS;
import com.ch.cloud.upms.client.UpmsPermissionClientService;
import com.ch.cloud.upms.dto.PermissionDto;
import com.ch.cloud.upms.mapstrut.MapperPermission;
import com.ch.cloud.upms.model.Permission;
import com.ch.cloud.upms.service.IPermissionService;
import com.ch.cloud.upms.service.IRoleService;
import com.ch.result.Result;
import com.ch.result.ResultUtils;
import com.ch.utils.BeanUtilsV2;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * desc:
 *
 * @author zhimin
 * @since 2018/12/21 10:40 PM
 */
@RestController
@RequestMapping("/permission")
//@Slf4j
@Tag(name = "系统权限管理", description = "系统权限管理相关接口")
public class PermissionClientController implements UpmsPermissionClientService {

    @Autowired
    IRoleService       roleService;
    @Autowired
    IPermissionService permissionService;

    @Operation(summary = "获取隐藏权限", description = "获取隐藏的权限列表")
    @GetMapping({"hidden"})
    public Result<PermissionDto> hidden() {
        return ResultUtils.wrapList(() -> {
            Permission record = new Permission();
            record.setType(Num.S5);
            record.setHidden(true);
            record.setStatus(StatusS.ENABLED);
            List<Permission> list = permissionService.find(record);
            return list.stream().map(MapperPermission.INSTANCE::toClientDto).collect(Collectors.toList());
        });
    }

    @Operation(summary = "获取白名单权限", description = "获取白名单的权限列表")
    @GetMapping({"whitelist"})
    public Result<PermissionDto> whitelist() {
        return ResultUtils.wrapList(() -> {
            Permission record = new Permission();
            record.setType(Num.S5);
            record.setHidden(false);
            record.setStatus(StatusS.ENABLED);
            List<Permission> list = permissionService.find(record);

            return list.stream().map(MapperPermission.INSTANCE::toClientDto).collect(Collectors.toList());
        });
    }

    @Operation(summary = "获取Cookie权限", description = "获取Cookie权限列表")
    @GetMapping({"cookie"})
    public Result<PermissionDto> cookie() {
        return ResultUtils.wrapList(() -> {
            Permission record = new Permission();
//            record.setType(Num.S5);
            record.setEnableCookie(true);
            record.setStatus(StatusS.ENABLED);
            List<Permission> list = permissionService.find(record);
            return list.stream().map(MapperPermission.INSTANCE::toClientDto).collect(Collectors.toList());
        });
    }
}
