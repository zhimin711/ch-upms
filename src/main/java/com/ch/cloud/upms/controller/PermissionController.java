package com.ch.cloud.upms.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch.Constants;
import com.ch.Num;
import com.ch.StatusS;
import com.ch.cloud.upms.model.Permission;
import com.ch.cloud.upms.service.IPermissionService;
import com.ch.cloud.upms.service.IRoleService;
import com.ch.cloud.upms.utils.VueRecordUtils;
import com.ch.e.PubError;
import com.ch.pojo.VueRecord;
import com.ch.result.PageResult;
import com.ch.result.Result;
import com.ch.result.ResultUtils;
import com.ch.utils.CommonUtils;
import com.ch.utils.DateUtils;
import com.ch.e.ExceptionUtils;
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
public class PermissionController {

    @Autowired
    IRoleService roleService;
    @Autowired
    IPermissionService permissionService;

    @GetMapping(value = {"{num:[0-9]+}/{size:[0-9]+}"})
    public PageResult<Permission> page(Permission record,
                                       @PathVariable(value = "num") int pageNum,
                                       @PathVariable(value = "size") int pageSize) {
        Page<Permission> pageInfo = permissionService.findTreePage2(record, pageNum, pageSize);
        return PageResult.success(pageInfo.getTotal(), pageInfo.getRecords());
    }

    @PostMapping
    public Result<Boolean> add(@RequestBody Permission record,
                               @RequestHeader(Constants.TOKEN_USER) String username) {
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
            record.setCreateBy(username);
            return permissionService.save(record);
        });
    }

    @PutMapping({"{id:[0-9]+}"})
    public Result<Integer> edit(@PathVariable Long id, @RequestBody Permission record,
                                @RequestHeader(Constants.TOKEN_USER) String username) {
        return ResultUtils.wrapFail(() -> {

            if (CommonUtils.isEmpty(record.getParentId())) {
                record.setParentId("0");
                record.setParentName(null);
            } else {
                record.setParentName(permissionService.findNameByParseLastId(record.getParentId()));
            }
            if (CommonUtils.isEquals("3", record.getType()) || CommonUtils.isEquals("5", record.getType())) {
                record.setCode(record.getCode().toUpperCase());
            }
            if (CommonUtils.isNotEmpty(record.getUrl())) {
                record.setUrl(record.getUrl().trim());
            }
            record.setUpdateBy(username);
            record.setUpdateAt(DateUtils.current());
            Permission r = permissionService.findByCode(record.getCode());
            Permission orig = permissionService.getById(id);
            if (orig == null) {
                throw ExceptionUtils.create(PubError.NOT_EXISTS, "权限不存在！");
            }
            if (r != null && !CommonUtils.isEquals(id, r.getId())) {
                ExceptionUtils._throw(PubError.NOT_EXISTS, "权限代码已存在！");
            }
            if (!CommonUtils.isEquals("2", record.getType())) {
//                record.setCode(record.getCode().toUpperCase());
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
            return permissionService.updateWithNull(record);
        });
    }

    @DeleteMapping({"{id:[0-9]+}"})
    public Result<Boolean> delete(@PathVariable Long id) {
        return ResultUtils.wrapFail(() -> permissionService.delete(id));
    }


    @ApiOperation(value = "获取权限树", notes = "0.全部 1.目录 2.菜单 3.按钮、链接")
    @GetMapping({"tree/{type:[0-9]+}"})
    public Result<VueRecord> tree(@PathVariable String type) {
        return ResultUtils.wrapList(() -> {
            List<Permission> records = permissionService.findTreeByType(type);
            return VueRecordUtils.convertParentsByType(records, type);
        });
    }

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
}
