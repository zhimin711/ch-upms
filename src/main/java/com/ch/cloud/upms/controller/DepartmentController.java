package com.ch.cloud.upms.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch.Constants;
import com.ch.cloud.upms.service.IDepartmentService;
import com.ch.cloud.upms.model.Department;
import com.ch.result.PageResult;
import com.ch.result.Result;
import com.ch.result.ResultUtils;
import com.ch.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 部门表 前端控制器
 * </p>
 *
 * @author zhimin.ma
 * @since 2020-07-02
 */
@RestController
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    private IDepartmentService departmentService;

    @GetMapping(value = {"/{num:[0-9]+}/{size:[0-9]+}"})
    public PageResult<Department> page(Department  record,
                                            @PathVariable(value = "num") int pageNum,
                                            @PathVariable(value = "size") int pageSize) {

        Page<Department> page = departmentService.page(new Page<>(pageNum,pageSize), Wrappers.query(record));
        return PageResult.success(page.getTotal(), page.getRecords());
    }

    @PostMapping
    public Result<Boolean> add(@RequestBody Department record,
                                @RequestHeader(Constants.TOKEN_USER) String username) {
        record.setCreateBy(username);
        return ResultUtils.wrapFail(() -> departmentService.save(record));
    }

    @PutMapping({"/{id:[0-9]+}"})
    public Result<Boolean> edit(@PathVariable Long id, @RequestBody Department record,
                                @RequestHeader(Constants.TOKEN_USER) String username) {
        record.setUpdateBy(username);
        return ResultUtils.wrapFail(() -> departmentService.updateById(record));
    }

    @DeleteMapping({"/delete"})
    public Result<Boolean> delete(Long id) {
        return ResultUtils.wrapFail(() -> departmentService.removeById(id));
    }
}

