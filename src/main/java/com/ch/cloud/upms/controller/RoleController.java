package com.ch.cloud.upms.controller;

import com.ch.cloud.upms.model.StPermission;
import com.ch.cloud.upms.model.StRole;
import com.ch.cloud.upms.service.IPermissionService;
import com.ch.cloud.upms.service.IRoleService;
import com.ch.e.PubError;
import com.ch.result.InvokerPage;
import com.ch.result.PageResult;
import com.ch.result.Result;
import com.ch.result.ResultUtils;
import com.ch.utils.CommonUtils;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 角色管理
 *
 * @author 01370603
 * @date 2018/12/22 22:35
 */

@RestController
@RequestMapping("role")
public class RoleController {


    @Autowired
    private IRoleService roleService;
    @Autowired
    private IPermissionService permissionService;

    @GetMapping(value = {"{num}/{size}"})
    public PageResult<StRole> page(StRole record,
                                   @PathVariable(value = "num") int pageNum,
                                   @PathVariable(value = "size") int pageSize) {
        return ResultUtils.wrapPage(() -> {
            PageInfo<StRole> pageInfo = roleService.findPage(record, pageNum, pageSize);
            return new InvokerPage.Page<>(pageInfo.getTotal(), pageInfo.getList());
        });
    }

    @PostMapping
    public Result<Integer> add(@RequestBody StRole record) {
        StRole r = roleService.findByCode(record.getCode());
        if (r != null) {
            return Result.error(PubError.EXISTS);
        } else if (CommonUtils.isEquals("0", record.getType())) {
            return Result.error(PubError.NOT_ALLOWED, "角色类型错误！");
        }
        return ResultUtils.wrapFail(() -> roleService.save(record));
    }

    @PutMapping("{id}")
    public Result<Integer> edit(@PathVariable Long id, @RequestBody StRole record) {
        if (CommonUtils.isEquals("0", record.getType())) {
            return Result.error(PubError.NOT_ALLOWED, "角色类型错误！");
        }
        return ResultUtils.wrapFail(() -> roleService.update(record));
    }

    @DeleteMapping("{id}")
    public Result<Integer> delete(@PathVariable Long id) {
        return ResultUtils.wrapFail(() -> roleService.delete(id));
    }


    @GetMapping({"{roleId}/permissions"})
    public Result<StPermission> findPermissionByRoleId(@PathVariable Long roleId) {
        return ResultUtils.wrapList(() -> permissionService.findByTypeAndRoleId(null, roleId));
    }


}
