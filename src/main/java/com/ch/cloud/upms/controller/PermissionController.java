package com.ch.cloud.upms.controller;

import com.ch.cloud.upms.model.StPermission;
import com.ch.cloud.upms.service.IPermissionService;
import com.ch.cloud.upms.service.IRoleService;
import com.ch.cloud.upms.utils.VueRecordUtils;
import com.ch.e.PubError;
import com.ch.pojo.VueRecord;
import com.ch.result.PageResult;
import com.ch.result.Result;
import com.ch.result.ResultUtils;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * desc:
 *
 * @author zhimin
 * @date 2018/12/21 10:40 PM
 */
@RestController
@RequestMapping("permission")
//@Slf4j
@Api("系统权限管理")
public class PermissionController {

    @Autowired
    IRoleService roleService;
    @Autowired(required = false)
    IPermissionService permissionService;

    @GetMapping(value = {"{num}/{size}"})
    public PageResult<StPermission> page(StPermission record,
                                   @PathVariable(value = "num") int pageNum,
                                   @PathVariable(value = "size") int pageSize) {
        PageInfo<StPermission> pageInfo = permissionService.findTreePage(record, pageNum, pageSize);
        return PageResult.success(pageInfo.getTotal(), pageInfo.getList());
    }

    @PostMapping("")
    public Result<Integer> add(@RequestBody StPermission record) {
        StPermission r = permissionService.findByCode(record.getCode());
        if (r != null) {
            return Result.error(PubError.EXISTS);
        }
        return ResultUtils.wrapFail(() -> permissionService.save(record));
    }

    @PutMapping({"{id}"})
    public Result<Integer> edit(@PathVariable int id, @RequestBody StPermission record) {
        return ResultUtils.wrapFail(() -> permissionService.updateWithNull(record));
    }

    @DeleteMapping({"delete"})
    public Result<Integer> delete(Long id) {
        return ResultUtils.wrapFail(() -> permissionService.delete(id));
    }



    @ApiOperation(value = "获取权限树",notes = "0.全部 1.目录 2.菜单")
    @GetMapping({"tree/{type}"})
    public Result<VueRecord> tree(@PathVariable String type) {
        return ResultUtils.wrapList(() -> {
            List<StPermission> records = permissionService.findTreeByType(type);
            return VueRecordUtils.convertParentsByType(records, type);
        });
    }
/*
    @GetMapping({"routes"})
    public Result<VueRecord> routes() {
        return ResultUtils.wrapList(() -> {
            List<StMenu> records = permissionService.findTreeByType(null);
            return VueRecordUtils.convertMenusByType(records, "1");
        });
    }*/
}
