package com.ch.cloud.upms.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch.Constants;
import com.ch.NumS;
import com.ch.StatusS;
import com.ch.cloud.upms.model.Permission;
import com.ch.cloud.upms.model.Role;
import com.ch.cloud.upms.service.IPermissionService;
import com.ch.cloud.upms.service.IRoleService;
import com.ch.e.PubError;
import com.ch.result.InvokerPage;
import com.ch.result.PageResult;
import com.ch.result.Result;
import com.ch.result.ResultUtils;
import com.ch.utils.CommonUtils;
import com.ch.utils.DateUtils;
import com.ch.e.ExceptionUtils;
import com.ch.utils.StringExtUtils;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色管理
 *
 * @author 01370603
 * @since 2018/12/22 22:35
 */
@RestController
@RequestMapping("/role")
public class RoleController {


    @Autowired
    private IRoleService roleService;
    @Autowired
    private IPermissionService permissionService;

    @GetMapping(value = {"{num:[0-9]+}/{size:[0-9]+}"})
    public PageResult<Role> page(Role record,
                                 @PathVariable(value = "num") int pageNum,
                                 @PathVariable(value = "size") int pageSize) {
        return ResultUtils.wrapPage(() -> {
            Page<Role> page = roleService.page(record, pageNum, pageSize);
            return new InvokerPage.Page<>(page.getTotal(), page.getRecords());
        });
    }

    @PostMapping
    public Result<Boolean> add(@RequestBody Role record,
                               @RequestHeader(Constants.TOKEN_USER) String username) {
        return ResultUtils.wrapFail(() -> {
            if (CommonUtils.isEmpty(record.getCode())) {
                ExceptionUtils._throw(PubError.EXISTS, "角色代码不能为空！");
            }
            String code = record.getCode().trim().toUpperCase();
            Role r = roleService.findByCode(code);
            if (r != null) {
                ExceptionUtils._throw(PubError.EXISTS, "角色代码已存在！");
            }
            if (CommonUtils.isEquals(NumS._0, record.getType())) {
                ExceptionUtils._throw(PubError.NOT_ALLOWED, "角色类型错误！");
            }
            record.setCode(code);
            record.setCreateBy(username);
            return roleService.save(record);
        });
    }

    @PutMapping("{id:[0-9]+}")
    public Result<Boolean> edit(@PathVariable Long id, @RequestBody Role record,
                                @RequestHeader(Constants.TOKEN_USER) String username) {
        return ResultUtils.wrapFail(() -> {
            if (CommonUtils.isEquals(NumS._0, record.getType())) {
                ExceptionUtils._throw(PubError.ARGS, "角色类型错误！");
            }
            Role orig = roleService.getById(id);
            if (CommonUtils.isEquals(StatusS.DISABLED, orig.getType())) {
                ExceptionUtils._throw(PubError.NOT_ALLOWED, "该角色类型不允许修改！");
            }
            record.setCode(orig.getCode());
            record.setCreateAt(orig.getCreateAt());
            record.setCreateBy(orig.getCreateBy());
            record.setUpdateBy(username);
            record.setUpdateAt(DateUtils.current());
            return roleService.updateById(record);
        });
    }


    @DeleteMapping({"/{id:[0-9]+}"})
    public Result<Boolean> delete(@PathVariable Long id) {
        return ResultUtils.wrapFail(() -> roleService.removeById(id));
    }

    @GetMapping({"{roleId}/menus"})
    public Result<Permission> findMenusByRoleId(@PathVariable Long roleId) {
        return ResultUtils.wrapList(() -> {
            Long rid = roleId;
            Role role = roleService.getById(roleId);
            if (role == null) {
                ExceptionUtils._throw(PubError.NOT_EXISTS);
            } else if (CommonUtils.isEquals("SUPER_ADMIN", role.getCode())) {
                rid = 0L;
            }
            return permissionService.findByTypeAndRoleId(Lists.newArrayList("1", "2", "4"), rid);
        });
    }

    @GetMapping({"{roleId}/permissions"})
    public Result<Permission> findPermissions(@PathVariable Long roleId, @RequestParam(value = "types", required = false) String typesStr) {
        return ResultUtils.wrapList(() -> {
            Long rid = roleId;
            Role role = roleService.getById(roleId);
            if (role == null) {
                ExceptionUtils._throw(PubError.NOT_EXISTS);
            } else if (CommonUtils.isEquals("SUPER_ADMIN", role.getCode())) {
                rid = 0L;
            }
            List<String> types = StringExtUtils.splitStrAndDeDuplication(Constants.SEPARATOR_2, typesStr);
            if (types.isEmpty()) {
                types.add("3");
            }
            return permissionService.findByTypeAndRoleId(types, rid);
        });
    }

    @PostMapping({"{roleId}/permissions"})
    public Result<Integer> editPermissions(@PathVariable Long roleId, @RequestBody Long[] permissionIds) {
        return ResultUtils.wrap(() -> permissionService.updateRolePermissions(roleId, Lists.newArrayList(permissionIds)));
    }
}
