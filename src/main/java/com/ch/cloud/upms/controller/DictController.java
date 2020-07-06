package com.ch.cloud.upms.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch.Constants;
import com.ch.cloud.upms.model.Dict;
import com.ch.cloud.upms.service.IDictService;
import com.ch.e.PubError;
import com.ch.result.PageResult;
import com.ch.result.Result;
import com.ch.result.ResultUtils;
import com.ch.utils.CommonUtils;
import com.ch.utils.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 数据字典表 前端控制器
 * </p>
 *
 * @author zhimin.ma
 * @since 2020-07-04
 */
@RestController
@RequestMapping("/dict")
public class DictController {
    @Autowired
    private IDictService dictService;

    @GetMapping(value = {"/{num:[0-9]+}/{size:[0-9]+}"})
    public PageResult<Dict> page(Dict record,
                                 @PathVariable(value = "num") int pageNum,
                                 @PathVariable(value = "size") int pageSize) {
        record.setPid(0L);
        Page<Dict> page = dictService.page(new Page<>(pageNum, pageSize), Wrappers.query(record));
        return PageResult.success(page.getTotal(), page.getRecords());
    }

    @PostMapping
    public Result<Boolean> add(@RequestBody Dict record,
                               @RequestHeader(Constants.TOKEN_USER) String username) {
        record.setCreateBy(username);
        return ResultUtils.wrapFail(() -> {
            Dict orig = dictService.findByCode(record.getCode());
            if (orig != null) ExceptionUtils._throw(PubError.EXISTS, "代码已存在");
            return dictService.save(record);
        });
    }

    @PutMapping({"/{id:[0-9]+}"})
    public Result<Boolean> edit(@PathVariable Long id, @RequestBody Dict record,
                                @RequestHeader(Constants.TOKEN_USER) String username) {
        record.setUpdateBy(username);
        return ResultUtils.wrapFail(() -> {
            Dict orig = dictService.findByCode(record.getCode());
            if (orig != null && !CommonUtils.isEquals(id, orig.getId())) ExceptionUtils._throw(PubError.EXISTS, "代码已存在");
            return dictService.updateById(record);
        });
    }

    @DeleteMapping({"/{id:[0-9]+}"})
    public Result<Boolean> delete(@PathVariable Long id) {
        return ResultUtils.wrapFail(() -> dictService.removeById(id));
    }

    @GetMapping({"/{id:[0-9]+}"})
    public Result<Dict> detail(@PathVariable Long id) {
        return ResultUtils.wrapFail(() -> {
            Dict record = dictService.getById(id);
            if (record != null) {
                record.setChildren(dictService.findByPid(id));
            }
            return record;
        });
    }
}

