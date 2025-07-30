package com.ch.cloud.upms.user.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch.Separator;
import com.ch.StatusS;
import com.ch.cloud.upms.dto.UserDto;
import com.ch.cloud.upms.manage.IUserManage;
import com.ch.cloud.upms.mapstrut.MapperUser;
import com.ch.cloud.upms.user.model.Department;
import com.ch.cloud.upms.user.model.Tenant;
import com.ch.cloud.upms.service.IDepartmentService;
import com.ch.cloud.upms.service.ITenantService;
import com.ch.cloud.upms.utils.RequestUtils;
import com.ch.e.ExUtils;
import com.ch.e.PubError;
import com.ch.pojo.VueRecord;
import com.ch.result.InvokerPage;
import com.ch.result.PageResult;
import com.ch.result.Result;
import com.ch.result.ResultUtils;
import com.ch.utils.CommonUtils;
import com.ch.utils.DateUtils;
import com.ch.core.utils.StrUtil;
import com.ch.utils.VueRecordUtils;
import com.google.common.collect.Lists;
import io.swagger.v3.oas.annotations.Operation; // 新增: 引入 OpenAPI 3.0 的 @Operation 注解
import io.swagger.v3.oas.annotations.tags.Tag; // 新增: 引入 OpenAPI 3.0 的 @Tag 注解

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 业务-租户表 前端控制器 available
 * </p>
 *
 * @author zhimin.ma
 * @since 2021-09-26
 */
@RestController
@RequestMapping("/tenant")
@Tag(name = "租户管理", description = "租户管理相关接口") // 修改: 替换 @Api 为 @Tag
public class TenantController {
    
    @Autowired
    private ITenantService tenantService;
    
    @Autowired
    private IDepartmentService departmentService;
    
    @Autowired
    private IUserManage userManage;
    
    @Operation(summary = "分页查询", description = "分页查询业务-租户") // 修改: 替换 @ApiOperation 为 @Operation
    @GetMapping(value = {"{num:[0-9]+}/{size:[0-9]+}"})
    public PageResult<Tenant> page(@RequestParam String name, @RequestParam String status,
            @PathVariable(value = "num") int pageNum, @PathVariable(value = "size") int pageSize) {
        return ResultUtils.wrapPage(() -> {
            Tenant record = new Tenant();
            record.setName(name);
            record.setStatus(status);
            Page<Tenant> page = tenantService.page(record, pageNum, pageSize);
            return InvokerPage.build(page.getTotal(), page.getRecords());
        });
    }
    
    @Operation(summary = "添加", description = "添加业务-租户") // 修改: 替换 @ApiOperation 为 @Operation
    @PostMapping
    public Result<Boolean> add(@RequestBody Tenant record) {
        return ResultUtils.wrapFail(() -> {
            if (CommonUtils.isEmpty(record.getSort())) {
                record.setSort(0);
            }
            if (CommonUtils.isEmpty(record.getStatus())) {
                record.setStatus("1");
            }
            if (CommonUtils.isEmpty(record.getDepartmentId())) {
                ExUtils.throwError(PubError.NON_NULL, "部门不允许为空！");
            }
            checkSaveOrUpdate(record);
            record.setCreateBy(RequestUtils.getHeaderUser());
            record.setCreateAt(DateUtils.current());
            return tenantService.save(record);
        });
    }
    
    private void checkSaveOrUpdate(Tenant record) {
        Tenant r = tenantService.getOne(
                Wrappers.lambdaQuery(record).eq(Tenant::getDepartmentId, record.getDepartmentId()));
        if (r != null && !CommonUtils.isEquals(record.getId(), r.getId())) {
            ExUtils.throwError(PubError.EXISTS, "部门已存在！");
        }
        List<Long> deptIds = StrUtil.parseIds(record.getDepartmentId());
        List<String> names = departmentService.findNames(deptIds);
        record.setDepartmentName(String.join(Separator.OBLIQUE_LINE, names));
        if (CommonUtils.isEmpty(record.getName())) {
            record.setName(record.getDepartmentName());
        }
        if (CommonUtils.isEmpty(record.getManager())) {
            Department dept = departmentService.getById(deptIds.get(deptIds.size() - 1));
            UserDto user = userManage.getByUsername(dept.getLeader());
            record.setManager(Lists.newArrayList(MapperUser.INSTANCE.toUsernameDTO(user)));
        }
    }
    
    @Operation(summary = "修改", description = "修改业务-租户") // 修改: 替换 @ApiOperation 为 @Operation
    @PutMapping({"{id:[0-9]+}"})
    public Result<Boolean> edit(@RequestBody Tenant record) {
        return ResultUtils.wrapFail(() -> {
            checkSaveOrUpdate(record);
            record.setUpdateBy(RequestUtils.getHeaderUser());
            record.setUpdateAt(DateUtils.current());
            return tenantService.updateById(record);
        });
    }
    
    @Operation(summary = "查询", description = "根据ID查询业务-租户") // 修改: 替换 @ApiOperation 为 @Operation
    @GetMapping({"{id:[0-9]+}"})
    public Result<Tenant> find(@PathVariable Long id) {
        return ResultUtils.wrapFail(() -> tenantService.getById(id));
    }
    
    @Operation(summary = "删除", description = "删除业务-租户") // 修改: 替换 @ApiOperation 为 @Operation
    @DeleteMapping({"{id:[0-9]+}"})
    public Result<Boolean> delete(@PathVariable Long id) {
        return ResultUtils.wrapFail(() -> tenantService.removeById(id));
    }
    
    @Operation(summary = "获取可用租户", description = "获取所有可用的租户列表") // 修改: 替换 @ApiOperation 为 @Operation
    @GetMapping({"available"})
    public Result<VueRecord> findAvailable() {
        return ResultUtils.wrapList(() -> {
            Tenant param = new Tenant();
            param.setStatus(StatusS.ENABLED);
            List<Tenant> list = tenantService.list(Wrappers.query(param));
            return VueRecordUtils.covertIdTree(list);
        });
    }
}
