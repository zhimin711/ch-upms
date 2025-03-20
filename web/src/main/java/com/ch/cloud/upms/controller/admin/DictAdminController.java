package com.ch.cloud.upms.controller.admin;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch.Status;
import com.ch.cloud.upms.model.Dict;
import com.ch.cloud.upms.service.IDictService;
import com.ch.cloud.upms.utils.RequestUtils;
import com.ch.e.Assert;
import com.ch.e.PubError;
import com.ch.result.PageResult;
import com.ch.result.Result;
import com.ch.result.ResultUtils;
import com.ch.utils.AssertUtils;
import com.ch.utils.CommonUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "数据字典-管理端")
public class DictAdminController {

    @Autowired
    private IDictService dictService;

    @Operation(summary = "分页查询", tags = "1.1")
    @GetMapping(value = {"/{num:[0-9]+}/{size:[0-9]+}"})
    public PageResult<Dict> page(Dict record, @PathVariable(value = "num") int pageNum,
                                 @PathVariable(value = "size") int pageSize) {
        record.setPid(0L);
        Page<Dict> page = dictService.page(new Page<>(pageNum, pageSize), Wrappers.query(record));
        return PageResult.success(page.getTotal(), page.getRecords());
    }

    @Operation(summary = "添加数据字典", description = "添加数据字典", tags = "abc")
    @PostMapping
    public Result<Boolean> add(@RequestBody Dict record) {
        record.setCreateBy(RequestUtils.getHeaderUser());
        return ResultUtils.wrapFail(() -> {
            Dict orig = dictService.findByCode(record.getCode());
            AssertUtils.notNull(orig, PubError.EXISTS, "代码");
            return dictService.save(record);
        });
    }

    @Operation(summary = "更新数据字典", description = "更新数据字典")
    @PutMapping({"/{id:[0-9]+}"})
    public Result<Boolean> edit(@PathVariable Long id, @RequestBody Dict record) {
        record.setUpdateBy(RequestUtils.getHeaderUser());
        return ResultUtils.wrapFail(() -> {
            Dict orig = dictService.findByCode(record.getCode());
            Assert.isTrue(orig != null && !CommonUtils.isEquals(id, orig.getId()),
                    PubError.EXISTS, "数据字典", record.getCode());

            return dictService.updateById(record);
        });
    }

    @Operation(summary = "删除数据字典", description = "删除数据字典")
    @DeleteMapping({"/{id:[0-9]+}"})
    public Result<Boolean> delete(@PathVariable Long id) {
        return ResultUtils.wrapFail(() -> dictService.removeById(id));
    }

    @Operation(summary = "数据字典详情", description = "获取数据字典详情")
    @GetMapping({"/{id:[0-9]+}"})
    public Result<Dict> detail(@PathVariable Long id) {
        return ResultUtils.wrapFail(() -> {
            Dict record = dictService.getById(id);
            if (record != null) {
                record.setChildren(dictService.findByPidAndName(id, null, Status.ALL));
            }
            return record;
        });
    }
}

