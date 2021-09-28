package com.ch.cloud.upms.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch.Separator;
import com.ch.cloud.upms.model.Department;
import com.ch.cloud.upms.model.Tenant;
import com.ch.cloud.upms.service.IDepartmentService;
import com.ch.cloud.upms.service.ITenantService;
import com.ch.cloud.upms.utils.RequestUtils;
import com.ch.e.ExceptionUtils;
import com.ch.e.PubError;
import com.ch.result.InvokerPage;
import com.ch.result.PageResult;
import com.ch.result.Result;
import com.ch.result.ResultUtils;
import com.ch.utils.CommonUtils;
import com.ch.utils.DateUtils;
import com.ch.utils.StringUtilsV2;
import io.swagger.annotations.ApiOperation;
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
public class TenantController {

    @Autowired
    private ITenantService     tenantService;
    @Autowired
    private IDepartmentService departmentService;

    @ApiOperation(value = "分页查询", notes = "分页查询业务-租户")
    @GetMapping(value = {"{num:[0-9]+}/{size:[0-9]+}"})
    public PageResult<Tenant> page(Tenant record,
                                   @PathVariable(value = "num") int pageNum,
                                   @PathVariable(value = "size") int pageSize) {
        return ResultUtils.wrapPage(() -> {
            Page<Tenant> page = tenantService.page(record, pageNum, pageSize);
            return InvokerPage.build(page.getTotal(), page.getRecords());
        });
    }

    @ApiOperation(value = "添加", notes = "添加业务-租户")
    @PostMapping
    public Result<Boolean> add(@RequestBody Tenant record) {
        return ResultUtils.wrapFail(() -> {
            if (CommonUtils.isEmpty(record.getSort())) record.setSort(0);
            if (CommonUtils.isEmpty(record.getStatus())) record.setStatus("1");
            if (CommonUtils.isEmpty(record.getDepartmentId())) {
                ExceptionUtils._throw(PubError.NON_NULL, "部门不允许为空！");
            }
            checkSaveOrUpdate(record);
            record.setCreateBy(RequestUtils.getHeaderUser());
            record.setCreateAt(DateUtils.current());
            return tenantService.save(record);
        });
    }


    private void checkSaveOrUpdate(Tenant record) {
        Tenant r = tenantService.getOne(Wrappers.lambdaQuery(record).eq(Tenant::getDepartmentId, record.getDepartmentId()));
        if (r == null) {
            return;
        }
        if (!CommonUtils.isEquals(record.getId(), r.getId())) {
            ExceptionUtils._throw(PubError.EXISTS, "部门已存在！");
        }
        List<Long> deptIds = StringUtilsV2.parseIds(record.getDepartmentId());
        List<String> names = departmentService.findNames(deptIds);
        record.setDepartmentName(String.join(Separator.OBLIQUE_LINE, names));
        if (CommonUtils.isEmpty(record.getName())) {
            record.setName(record.getDepartmentName());
        }
        if (CommonUtils.isEmpty(record.getManager())) {
            Department dept = departmentService.getById(deptIds.get(deptIds.size() - 1));
            record.setManager(dept.getLeader());
        }
    }

    @ApiOperation(value = "修改", notes = "修改业务-租户")
    @PutMapping({"{id:[0-9]+}"})
    public Result<Boolean> edit(@RequestBody Tenant record) {
        return ResultUtils.wrapFail(() -> {
            checkSaveOrUpdate(record);
            record.setUpdateBy(RequestUtils.getHeaderUser());
            record.setUpdateAt(DateUtils.current());
            return tenantService.updateById(record);
        });
    }

    @GetMapping({"{id:[0-9]+}"})
    public Result<Tenant> find(@PathVariable Long id) {
        return ResultUtils.wrapFail(() -> tenantService.getById(id));
    }

    @ApiOperation(value = "删除", notes = "删除业务-租户")
    @DeleteMapping({"{id:[0-9]+}"})
    public Result<Boolean> delete(@PathVariable Long id) {
        return ResultUtils.wrapFail(() -> tenantService.removeById(id));
    }
}

