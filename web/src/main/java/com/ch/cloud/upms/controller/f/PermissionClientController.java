package com.ch.cloud.upms.controller.f;

import com.ch.Num;
import com.ch.StatusS;
import com.ch.cloud.upms.model.Permission;
import com.ch.cloud.upms.service.IPermissionService;
import com.ch.cloud.upms.service.IRoleService;
import com.ch.result.Result;
import com.ch.result.ResultUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * desc:
 *
 * @author zhimin
 * @since 2018/12/21 10:40 PM
 */
@RestController
@RequestMapping("/permission")
//@Slf4j
@Api("系统权限管理")
public class PermissionClientController {

    @Autowired
    IRoleService       roleService;
    @Autowired
    IPermissionService permissionService;

    @GetMapping({"hidden"})
    public Result<Permission> hidden() {
        return ResultUtils.wrapList(() -> {
            Permission record = new Permission();
            record.setType(Num.S5);
            record.setHidden(true);
            record.setStatus(StatusS.ENABLED);
            return permissionService.find(record);
        });
    }

    @GetMapping({"whitelist"})
    public Result<Permission> whitelist() {
        return ResultUtils.wrapList(() -> {
            Permission record = new Permission();
            record.setType(Num.S5);
            record.setHidden(false);
            record.setStatus(StatusS.ENABLED);
            return permissionService.find(record);
        });
    }

    @GetMapping({"cookie"})
    public Result<Permission> cookie() {
        return ResultUtils.wrapList(() -> {
            Permission record = new Permission();
//            record.setType(Num.S5);
            record.setEnableCookie(true);
            record.setStatus(StatusS.ENABLED);
            return permissionService.find(record);
        });
    }
}
