package com.ch.cloud.upms.user.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch.Num;
import com.ch.cloud.upms.mq.sender.GatewayNotifySender;
import com.ch.cloud.upms.service.IPermissionService;
import com.ch.cloud.upms.service.IRoleService;
import com.ch.cloud.upms.user.model.Permission;
import com.ch.cloud.upms.utils.RequestUtils;
import com.ch.cloud.upms.utils.VueRecordUtils;
import com.ch.e.ExUtils;
import com.ch.e.PubError;
import com.ch.pojo.KeyValue;
import com.ch.pojo.VueRecord2;
import com.ch.result.PageResult;
import com.ch.result.Result;
import com.ch.result.ResultUtils;
import com.ch.toolkit.ContextUtil;
import com.ch.utils.CommonUtils;
import com.ch.utils.DateUtils;
import com.ch.core.utils.StrUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * desc:
 *
 * @author zhimin
 * @since 2018/12/21 10:40 PM
 */
@RestController
@RequestMapping("/permission")
@Tag(name = "系统权限管理", description = "系统权限管理相关接口")
public class PermissionController {

    @Autowired
    IRoleService       roleService;
    @Autowired
    IPermissionService permissionService;

    @Autowired
    private GatewayNotifySender gatewayNotifySender;

    @Operation(summary = "分页查询权限", description = "根据条件分页查询权限列表")
    @GetMapping(value = {"{num:[0-9]+}/{size:[0-9]+}"})
    public PageResult<Permission> page(Permission record,
                                       @PathVariable(value = "num") int pageNum,
                                       @PathVariable(value = "size") int pageSize) {
        Set<Long> ids = Sets.newHashSet();
        if (CommonUtils.isNotEmpty(record.getName(), record.getCode())) {
            List<Permission> list = permissionService.list(Wrappers.query(new Permission())
                    .like(CommonUtils.isNotEmpty(record.getCode()), "code", record.getCode())
                    .like(CommonUtils.isNotEmpty(record.getName()), "name", record.getName())
                    .eq(CommonUtils.isNotEmpty(record.getStatus()), "status", record.getStatus()));
            if (CommonUtils.isNotEmpty(list)) {
                list.forEach(e -> {
                    if (CommonUtils.isEquals(e.getParentId(), "0")) {
                        ids.add(e.getId());
                    } else {
                        List<Long> ids2 = StrUtil.parseIds(e.getParentId());
                        ids.add(ids2.get(0));
                    }
                });
            }
        }
        Permission param = new Permission();
        param.setParentId("0");
        param.setStatus(record.getStatus());
        Page<Permission> pageInfo = permissionService.page(new Page<>(pageNum, pageSize), Wrappers.query(param)
                .in(!ids.isEmpty(), "id", ids).orderByAsc("sort", "id"));
        existsChildren(pageInfo.getRecords());
        return PageResult.success(pageInfo.getTotal(), pageInfo.getRecords());
    }

    private void existsChildren(List<Permission> records) {
        if (CommonUtils.isEmpty(records)) {
            return;
        }
        records.forEach(e -> {
            Permission p = new Permission();
            p.setParentId(e.getParentId());
            p.setId(e.getId());
            e.setHasChildren(CommonUtils.isNotEmpty(permissionService.findChildren(p)));
        });
    }

    @Operation(summary = "新增权限", description = "新增一个权限记录")
    @PostMapping
    public Result<Boolean> add(@RequestBody Permission record) {
        return ResultUtils.wrapFail(() -> {
            if (CommonUtils.isEmpty(record.getCode()) || CommonUtils.isEmpty(record.getName())) {
                ExUtils.throwError(PubError.NON_NULL, "权限代码或名称不能为空");
            }
            if (CommonUtils.isEquals("3", record.getType()) || CommonUtils.isEquals("5", record.getType())) {
                record.setCode(record.getCode().toUpperCase());
            }
            Permission r = permissionService.findByCode(record.getCode());
            if (r != null) {
                ExUtils.throwError(PubError.EXISTS, "权限代码已存在！");
            }
            if (CommonUtils.isEmpty(record.getParentId())) {
                record.setParentId("0");
            } else {
                record.setParentName(permissionService.findNameByParseLastId(record.getParentId()));
            }
            if (CommonUtils.isNotEmpty(record.getUrl())) {
                record.setUrl(record.getUrl().trim());
            }
            record.setCreateBy(RequestUtils.getHeaderUser());
            return permissionService.save(record);
        });
    }

    @Operation(summary = "更新权限", description = "根据ID更新权限记录")
    @PutMapping({"{id:[0-9]+}"})
    public Result<Integer> edit(@PathVariable Long id, @RequestBody Permission record) {
        return ResultUtils.wrapFail(() -> {

            if (CommonUtils.isEmpty(record.getParentId())) {
                record.setParentId("0");
                record.setParentName(null);
            } else {
                record.setParentName(permissionService.findNameByParseLastId(record.getParentId()));
            }
            if (CommonUtils.isEquals("5", record.getType())) {
                record.setCode(record.getCode().toUpperCase());
            }
            if (CommonUtils.isNotEmpty(record.getUrl())) {
                record.setUrl(record.getUrl().trim());
            }
            record.setUpdateBy(ContextUtil.getUsername());
            record.setUpdateAt(DateUtils.current());
            Permission r = permissionService.findByCode(record.getCode());
            Permission orig = permissionService.getById(id);
            if (orig == null) {
                ExUtils.throwError(PubError.NOT_EXISTS, "权限不存在！");
            }
            if (r != null && !CommonUtils.isEquals(id, r.getId())) {
                ExUtils.throwError(PubError.NOT_EXISTS, "权限代码已存在！");
            }
            record.setChildren(null);
            if (!CommonUtils.isEquals(record.getParentId(), orig.getParentId()) && Lists.newArrayList(Num.S1, Num.S2).contains(orig.getType())) {
                String pid = id + "";
                if (!CommonUtils.isEquals(Num.S0, orig.getParentId())) {
                    pid = orig.getParentId() + "," + id;
                }
                List<Permission> children = permissionService.findByPid(pid);
                if (CommonUtils.isEquals(Num.S1, orig.getType()) && !children.isEmpty()) {
                    ExUtils.throwError(PubError.NOT_EXISTS, "权限目录存在子菜单不允许调整上级！");
                }
                record.setChildren(children);
            }
            int c = permissionService.updateWithNull(record);

            boolean isNeedClean = false;
            if (!CommonUtils.isEquals(record.getStatus(), orig.getStatus())) {
                isNeedClean = true;
            } else if (!CommonUtils.isEquals(record.getType(), orig.getType())) {
                isNeedClean = true;
            } else if (!CommonUtils.isEquals(record.getUrl(), orig.getUrl()) && !Lists.newArrayList(Num.S1, Num.S2).contains(orig.getType())) {
                isNeedClean = true;
            } else if (!CommonUtils.isEquals(record.getMethod(), orig.getMethod())) {
                isNeedClean = true;
            }
            if (c > 0 && isNeedClean) {
                gatewayNotifySender.cleanNotify(new KeyValue("permissions", "all"));
            }
            return c;
        });
    }

    @Operation(summary = "删除权限", description = "根据ID删除权限记录")
    @DeleteMapping({"{id:[0-9]+}"})
    public Result<Boolean> delete(@PathVariable Long id) {
        return ResultUtils.wrapFail(() -> permissionService.delete(id));
    }
    
    @Operation(summary = "获取子权限", description = "根据父ID获取子权限列表")
    @GetMapping({"{id:[0-9]+}/children"})
    public Result<Permission> getChildren(Permission record) {
        return ResultUtils.wrapList(() -> {
            if (CommonUtils.isEmpty(record.getParentId())) {
                Permission orig = permissionService.getById(record.getId());
                if (orig == null) return null;
                record.setParentId(orig.getParentId());
            }
            List<Permission> list = permissionService.findChildren(record);
            existsChildren(list);
            return list;
        });
    }


    @Operation(summary = "获取权限树", description = "根据类型获取权限树结构")
    @GetMapping({"tree/{type:[0-9]+}"})
    public Result<VueRecord2> tree(@PathVariable String type) {
        return ResultUtils.wrapList(() -> {
            List<Permission> records = permissionService.findTreeByType(type);
            return VueRecordUtils.convertTreeByType(records, type);
        });
    }

}
