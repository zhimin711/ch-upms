package com.ch.cloud.upms.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch.Num;
import com.ch.StatusS;
import com.ch.cloud.upms.fclient.GatewayClientService;
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
import com.ch.utils.StringUtilsV2;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
//@Slf4j
@Api("系统权限管理")
public class PermissionController {

    @Autowired
    IRoleService       roleService;
    @Autowired
    IPermissionService permissionService;

    @Autowired
    private GatewayClientService gatewayClientService;

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
                        List<Long> ids2 = StringUtilsV2.parseIds(e.getParentId());
                        ids.add(ids2.get(0));
                    }
                });
            }
        }
        Permission param = new Permission();
        param.setParentId("0");
        param.setStatus(record.getStatus());
        Page<Permission> pageInfo = permissionService.page(new Page<>(pageNum, pageSize), Wrappers.query(param).in(!ids.isEmpty(), "id", ids));
        return PageResult.success(pageInfo.getTotal(), pageInfo.getRecords());
    }

    @PostMapping
    public Result<Boolean> add(@RequestBody Permission record) {
        return ResultUtils.wrapFail(() -> {
            if (CommonUtils.isEmpty(record.getCode()) || CommonUtils.isEmpty(record.getName())) {
                ExceptionUtils._throw(PubError.NON_NULL, "权限代码或名称不能为空");
            }
            if (CommonUtils.isEquals("3", record.getType()) || CommonUtils.isEquals("5", record.getType())) {
                record.setCode(record.getCode().toUpperCase());
            }
            Permission r = permissionService.findByCode(record.getCode());
            if (r != null) {
                ExceptionUtils._throw(PubError.EXISTS, "权限代码已存在！");
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
            record.setUpdateBy(RequestUtils.getHeaderUser());
            record.setUpdateAt(DateUtils.current());
            Permission r = permissionService.findByCode(record.getCode());
            Permission orig = permissionService.getById(id);
            if (orig == null) {
                throw ExceptionUtils.create(PubError.NOT_EXISTS, "权限不存在！");
            }
            if (r != null && !CommonUtils.isEquals(id, r.getId())) {
                ExceptionUtils._throw(PubError.NOT_EXISTS, "权限代码已存在！");
            }
            record.setChildren(null);
            if (!CommonUtils.isEquals(record.getParentId(), orig.getParentId()) && Lists.newArrayList(Num.S1, Num.S2).contains(orig.getType())) {
                String pid = id + "";
                if (!CommonUtils.isEquals(Num.S0, orig.getParentId())) {
                    pid = orig.getParentId() + "," + id;
                }
                List<Permission> children = permissionService.findByPid(pid);
                if (CommonUtils.isEquals(Num.S1, orig.getType()) && !children.isEmpty()) {
                    ExceptionUtils._throw(PubError.NOT_EXISTS, "权限目录存在子菜单不允许调整上级！");
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
                gatewayClientService.cleanPermissions();
            }
            return c;
        });
    }

    @DeleteMapping({"{id:[0-9]+}"})
    public Result<Boolean> delete(@PathVariable Long id) {
        return ResultUtils.wrapFail(() -> permissionService.delete(id));
    }


    @GetMapping({"{id:[0-9]+}/children"})
    public Result<Permission> getChildren(Permission record) {
        return ResultUtils.wrapList(() -> {
            if (CommonUtils.isEmpty(record.getParentId())) {
                Permission orig = permissionService.getById(record.getId());
                if (orig == null) return null;
                record.setParentId(orig.getParentId());
            }
            return permissionService.findChildren(record);
        });
    }


    @ApiOperation(value = "获取权限树", notes = "0.全部 1.目录 2.菜单 3.按钮、链接 4.可以授权接口 9.可授权权限")
    @GetMapping({"tree/{type:[0-9]+}"})
    public Result<VueRecord> tree(@PathVariable String type) {
        return ResultUtils.wrapList(() -> {
            List<Permission> records = permissionService.findTreeByType(type);
            return VueRecordUtils.convertTreeByType(records, type);
        });
    }

}
