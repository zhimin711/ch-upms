package com.ch.cloud.upms.controller;

import com.ch.cloud.upms.model.StRole;
import com.ch.cloud.upms.service.IRoleService;
import com.ch.e.CoreError;
import com.ch.result.InvokerPage;
import com.ch.result.PageResult;
import com.ch.result.Result;
import com.ch.result.ResultUtils;
import com.ch.utils.CommonUtils;
import com.github.pagehelper.PageInfo;
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

    @PostMapping(value = {"{num}/{size}"})
    public PageResult<StRole> page(@RequestBody StRole record,
                                   @PathVariable(value = "num") int pageNum,
                                   @PathVariable(value = "size") int pageSize) {
        return ResultUtils.wrapPage(() -> {
            PageInfo<StRole> pageInfo = roleService.findPage(record, pageNum, pageSize);
            return new InvokerPage.Page<>(pageInfo.getTotal(), pageInfo.getList());
        });
    }

    @PostMapping("save")
    public Result<Integer> add(@RequestBody StRole record) {
        StRole r = roleService.findByCode(record.getCode());
        if (r != null) {
            return new Result<>(CoreError.EXISTS, "角色代码已存在！");
        } else if (CommonUtils.isEquals("0", record.getType())) {
            return new Result<>(CoreError.NOT_ALLOWED, "角色类型错误！");
        }
        return ResultUtils.wrapFail(() -> roleService.save(record));
    }

    @PostMapping({"save/{id}"})
    public Result<Integer> edit(@PathVariable int id, @RequestBody StRole record) {
        if (CommonUtils.isEquals("0", record.getType())) {
            return new Result<>(CoreError.NOT_ALLOWED, "角色类型错误！");
        }
        return ResultUtils.wrapFail(() -> roleService.update(record));
    }

    @PostMapping({"delete"})
    public Result<Integer> delete(Long id) {
        return ResultUtils.wrapFail(() -> roleService.delete(id));
    }

}
