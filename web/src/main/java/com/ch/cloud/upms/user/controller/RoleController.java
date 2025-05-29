package com.ch.cloud.upms.user.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch.Num;
import com.ch.Separator;
import com.ch.StatusS;
import com.ch.cloud.upms.user.model.Permission;
import com.ch.cloud.upms.user.model.Role;
import com.ch.cloud.upms.mq.sender.GatewayNotifySender;
import com.ch.cloud.upms.service.IPermissionService;
import com.ch.cloud.upms.service.IRoleService;
import com.ch.cloud.upms.utils.RequestUtils;
import com.ch.e.ExUtils;
import com.ch.e.PubError;
import com.ch.pojo.KeyValue;
import com.ch.result.InvokerPage;
import com.ch.result.PageResult;
import com.ch.result.Result;
import com.ch.result.ResultUtils;
import com.ch.utils.CommonUtils;
import com.ch.utils.DateUtils;
import com.ch.utils.StringUtilsV2;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色管理
 *
 * @author 01370603
 * @since 2018/12/22 22:35
 */
@RestController
@Slf4j
@RequestMapping("/role")
public class RoleController {


    @Autowired
    private IRoleService        roleService;
    @Autowired
    private IPermissionService  permissionService;
    @Autowired
    private GatewayNotifySender gatewayNotifySender;

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
    public Result<Boolean> add(@RequestBody Role record) {
        return ResultUtils.wrapFail(() -> {
            if (CommonUtils.isEmpty(record.getCode())) {
                ExUtils.throwError(PubError.EXISTS, "角色代码不能为空！");
            }
            String code = record.getCode().trim().toUpperCase();
            Role r = roleService.findByCode(code);
            if (r != null) {
                ExUtils.throwError(PubError.EXISTS, "角色代码已存在！");
            }
            if (CommonUtils.isEquals(Num.S0, record.getType())) {
                ExUtils.throwError(PubError.NOT_ALLOWED, "角色类型错误！");
            }
            record.setCode(code);
            record.setCreateBy(RequestUtils.getHeaderUser());
            return roleService.save(record);
        });
    }

    @PutMapping("{id:[0-9]+}")
    public Result<Boolean> edit(@PathVariable Long id, @RequestBody Role record) {
        return ResultUtils.wrapFail(() -> {
            if (CommonUtils.isEquals(Num.S0, record.getType())) {
                ExUtils.throwError(PubError.ARGS, "角色类型错误！");
            }
            Role orig = roleService.getById(id);
            if (CommonUtils.isEquals(StatusS.DISABLED, orig.getType())) {
                ExUtils.throwError(PubError.NOT_ALLOWED, "该角色类型不允许修改！");
            }
            record.setCode(orig.getCode());
            record.setCreateAt(orig.getCreateAt());
            record.setCreateBy(orig.getCreateBy());
            record.setUpdateBy(RequestUtils.getHeaderUser());
            record.setUpdateAt(DateUtils.current());
            return roleService.updateById(record);
        });
    }


    @DeleteMapping({"{id:[0-9]+}"})
    public Result<Boolean> delete(@PathVariable Long id) {
        return ResultUtils.wrapFail(() -> roleService.removeById(id));
    }

    @GetMapping({"{roleId:[0-9]+}/menus"})
    public Result<Permission> findMenusByRoleId(@PathVariable Long roleId) {
        return ResultUtils.wrapList(() -> {
            Long rid = roleId;
            Role role = roleService.getById(roleId);
            if (role == null) {
                ExUtils.throwError(PubError.NOT_EXISTS);
            } else if (CommonUtils.isEquals("SUPER_ADMIN", role.getCode())) {
                rid = 0L;
            }
            return permissionService.findByTypeAndRoleId(Lists.newArrayList("1", "2", "4"), rid);
        });
    }

    @GetMapping({"{roleId:[0-9]+}/permissions"})
    public Result<Permission> findPermissions(@PathVariable Long roleId, @RequestParam(value = "types", required = false) String typesStr) {
        return ResultUtils.wrapList(() -> {
            Long rid = roleId;
            Role role = roleService.getById(roleId);
            String typesStr2 = CommonUtils.isEmpty(typesStr) ? "3,4" : typesStr;

            if (role == null) {
                ExUtils.throwError(PubError.NOT_EXISTS);
            } else if (CommonUtils.isEquals(StatusS.DISABLED, role.getStatus())) {
                ExUtils.throwError(PubError.INVALID, "角色：" + roleId);
            } else if (CommonUtils.isEquals("SUPER_ADMIN", role.getCode())) {
                rid = 0L;
            }
            List<String> types = StringUtilsV2.splitStrAndDeDuplication(Separator.COMMA_SIGN, typesStr2);
            if (types.isEmpty()) {
                types.add("3");
            }
            return permissionService.findByTypeAndRoleId(types, rid);
        });
    }

    @PostMapping({"{roleId:[0-9]+}/permissions"})
    public Result<Integer> editPermissions(@PathVariable Long roleId, @RequestBody Long[] permissionIds) {
        return ResultUtils.wrap(() -> {
            int c = permissionService.updateRolePermissions(roleId, Lists.newArrayList(permissionIds));
            if (c > 0) {
                gatewayNotifySender.cleanNotify(new KeyValue("permissions", roleId + ""));
            }
            return c;
        });
    }

    @PutMapping({"{roleId:[0-9]+}/permissions"})
    public Result<Integer> editAuthPermissions(@PathVariable Long roleId, @RequestBody List<Long> permissionIds) {
        return ResultUtils.wrap(() -> {
            List<Long> ids = permissionIds;
            if (CommonUtils.isNotEmpty(permissionIds)) {//过滤非授权权限
                List<Permission> list = permissionService.query().in("id", permissionIds).eq("type", "4").select("id").list();
                if (list.isEmpty()) {
                    ids = Lists.newArrayList();
                } else {
                    ids = list.stream().map(Permission::getId).collect(Collectors.toList());
                }
            }
            int c = permissionService.updateRoleAuthPermissions(roleId, ids);
            if (c > 0) {
                gatewayNotifySender.cleanNotify(new KeyValue("permissions", roleId + ""));
            }
            return c;
        });
    }
}
