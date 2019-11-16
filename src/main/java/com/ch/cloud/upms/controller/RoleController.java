package com.ch.cloud.upms.controller;

import com.ch.Constants;
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
import com.ch.utils.DateUtils;
import com.ch.utils.ExceptionUtils;
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
    public Result<Integer> add(@RequestBody StRole record,
                               @RequestHeader(Constants.TOKEN_USER) String username) {
        return ResultUtils.wrapFail(() -> {
            StRole r = roleService.findByCode(record.getCode());
            if (r != null) {
                ExceptionUtils._throw(PubError.EXISTS, "角色代码已存在！");
            }
            if (CommonUtils.isEquals("0", record.getType())) {
                ExceptionUtils._throw(PubError.NOT_ALLOWED, "角色类型错误！");
            }
            record.setCreateBy(username);
            return roleService.save(record);
        });
    }

    @PutMapping("{id}")
    public Result<Integer> edit(@PathVariable Long id, @RequestBody StRole record,
                                @RequestHeader(Constants.TOKEN_USER) String username) {
        return ResultUtils.wrapFail(() -> {
            if (CommonUtils.isEquals("0", record.getType())) {
                ExceptionUtils._throw(PubError.NOT_ALLOWED, "角色类型错误！");
            }
            record.setUpdateBy(username);
            record.setUpdateAt(DateUtils.current());
            return roleService.update(record);
        });
    }

    @DeleteMapping("{id}")
    public Result<Integer> delete(@PathVariable Long id) {
        return ResultUtils.wrapFail(() -> roleService.delete(id));
    }

    @GetMapping({"{roleId}/menus"})
    public Result<StPermission> findMenusByRoleId(@PathVariable Long roleId) {
        return ResultUtils.wrapList(() -> permissionService.findByTypeAndRoleId(Lists.newArrayList("1", "2"), roleId));
    }

    @GetMapping({"{roleId}/permissions"})
    public Result<StPermission> findPermissions(@PathVariable Long roleId) {
        return ResultUtils.wrapList(() -> permissionService.findByTypeAndRoleId(Lists.newArrayList("3", "4"), roleId));
    }

    @PostMapping({"{roleId}/permissions"})
    public Result<Integer> editPermissions(@PathVariable Long roleId, @RequestBody Long[] permissionIds) {
        return ResultUtils.wrap(() -> permissionService.updateRolePermissions(roleId, Lists.newArrayList(permissionIds)));
    }
}
