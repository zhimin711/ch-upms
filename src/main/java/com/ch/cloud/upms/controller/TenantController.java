package com.ch.cloud.upms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch.cloud.upms.model.Project;
import com.ch.cloud.upms.model.Tenant;
import com.ch.cloud.upms.service.ITenantService;
import com.ch.result.InvokerPage;
import com.ch.result.PageResult;
import com.ch.result.Result;
import com.ch.result.ResultUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 业务-租户表 前端控制器 available
 * </p>
 *
 * @author zhimin.ma
 * @since 2021-09-26
 */
@Controller
@RequestMapping("/tenant")
public class TenantController {

    @Autowired
    private ITenantService tenantService;

    @ApiOperation(value = "分页查询", notes = "分页查询业务-租户")
    @GetMapping(value = {"{num:[0-9]+}/{size:[0-9]+}"})
    public PageResult<Tenant> page(Tenant record,
                                   @PathVariable(value = "num") int pageNum,
                                   @PathVariable(value = "size") int pageSize) {
        return ResultUtils.wrapPage(() -> {
            Page<Tenant> page = tenantService.page(record, pageNum, pageSize);
            return InvokerPage.build(page.getTotal(),page.getRecords());
        });
    }

    @ApiOperation(value = "添加", notes = "添加业务-租户")
    @PostMapping
    public Result<Boolean> add(@RequestBody Tenant record) {
        return ResultUtils.wrapFail(() -> tenantService.save(record));
    }

    @ApiOperation(value = "修改", notes = "修改业务-租户")
    @PutMapping({"{id:[0-9]+}"})
    public Result<Boolean> edit(@RequestBody Tenant record) {
        return ResultUtils.wrapFail(() -> tenantService.updateById(record));
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

