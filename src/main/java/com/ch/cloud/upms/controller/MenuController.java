package com.ch.cloud.upms.controller;

import com.ch.cloud.upms.model.StMenu;
import com.ch.cloud.upms.pojo.VueRecord;
import com.ch.cloud.upms.service.IMenuService;
import com.ch.cloud.upms.service.IPersistentTokenService;
import com.ch.cloud.upms.service.IRoleService;
import com.ch.cloud.upms.utils.VueRecordUtils;
import com.ch.e.CoreError;
import com.ch.result.PageResult;
import com.ch.result.Result;
import com.ch.result.ResultUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * desc:
 *
 * @author zhimin
 * @date 2018/12/21 10:40 PM
 */
@RestController
@RequestMapping("permission")
public class MenuController {

    @Autowired
    IPersistentTokenService tokenService;
    @Autowired
    IRoleService roleService;
    @Autowired
    IMenuService menuService;

    @PostMapping(value = {"{num}/{size}"})
    public PageResult<StMenu> page(@RequestBody StMenu record,
                                   @PathVariable(value = "num") int pageNum,
                                   @PathVariable(value = "size") int pageSize) {
        PageInfo<StMenu> pageInfo = menuService.findTreePage(record, pageNum, pageSize);
        return new PageResult<>(pageInfo.getList(), pageNum, pageSize, pageInfo.getTotal());
    }

    @PostMapping("save")
    public Result<Integer> add(@RequestBody StMenu record) {
        StMenu r = menuService.findByCode(record.getCode());
        if (r != null) {
            return new Result<>(CoreError.EXISTS, "菜单代码已存在！");
        }
        return ResultUtils.wrapFail(() -> menuService.save(record));
    }

    @PostMapping({"save/{id}"})
    public Result<Integer> edit(@PathVariable int id, @RequestBody StMenu record) {
        return ResultUtils.wrapFail(() -> menuService.updateWithNull(record));
    }

    @PostMapping({"delete"})
    public Result<Integer> delete(Long id) {
        return ResultUtils.wrapFail(() -> menuService.delete(id));
    }


    @GetMapping({"tree/{type}"})
    public Result<VueRecord> tree(@PathVariable String type) {
        return ResultUtils.wrapList(() -> {
            List<StMenu> records = menuService.findTreeByType(type);
            return VueRecordUtils.convertMenusByType(records, type);
        });
    }
}
