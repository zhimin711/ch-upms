package com.ch.cloud.upms.controller.f;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch.Num;
import com.ch.StatusS;
import com.ch.cloud.upms.model.Permission;
import com.ch.cloud.upms.service.IPermissionService;
import com.ch.cloud.upms.service.IRoleService;
import com.ch.cloud.upms.utils.RequestUtils;
import com.ch.cloud.upms.utils.VueRecordUtils;
import com.ch.e.ExceptionUtils;
import com.ch.e.PubError;
import com.ch.pojo.VueRecord;
import com.ch.result.PageResult;
import com.ch.result.Result;
import com.ch.result.ResultUtils;
import com.ch.utils.CommonUtils;
import com.ch.utils.DateUtils;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
