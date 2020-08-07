package com.ch.cloud.upms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch.Constants;
import com.ch.cloud.upms.model.Position;
import com.ch.cloud.upms.service.IPositionService;
import com.ch.pojo.VueRecord;
import com.ch.result.PageResult;
import com.ch.result.Result;
import com.ch.result.ResultUtils;
import com.ch.utils.CommonUtils;
import com.ch.utils.VueRecordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 职位信息表 前端控制器
 * </p>
 *
 * @author zhimin.ma
 * @since 2020-07-04
 */
@RestController
@RequestMapping("/position")
public class PositionController {
    @Autowired
    private IPositionService positionService;

    @GetMapping(value = {"/{num:[0-9]+}/{size:[0-9]+}"})
    public PageResult<Position> page(Position record,
                                     @PathVariable(value = "num") int pageNum,
                                     @PathVariable(value = "size") int pageSize) {

        Page<Position> page = positionService.page(new Page<>(pageNum, pageSize), Wrappers.query(record));
        return PageResult.success(page.getTotal(), page.getRecords());
    }

    @PostMapping
    public Result<Boolean> add(@RequestBody Position record,
                               @RequestHeader(Constants.TOKEN_USER) String username) {
        record.setCreateBy(username);
        return ResultUtils.wrapFail(() -> positionService.save(record));
    }

    @PutMapping({"/{id:[0-9]+}"})
    public Result<Boolean> edit(@PathVariable Long id, @RequestBody Position record,
                                @RequestHeader(Constants.TOKEN_USER) String username) {
        record.setUpdateBy(username);
        return ResultUtils.wrapFail(() -> positionService.updateById(record));
    }

    @DeleteMapping({"/{id:[0-9]+}"})
    public Result<Boolean> delete(@PathVariable Long id) {
        return ResultUtils.wrapFail(() -> positionService.removeById(id));
    }


    @GetMapping({"search"})
    public Result<VueRecord> searchList(@RequestParam(value = "name", required = false) String s) {
        return ResultUtils.wrapList(() -> {
            Position record = new Position();

            LambdaQueryWrapper<Position> query = Wrappers.lambdaQuery(record).eq(Position::getStatus, Constants.ENABLED).like(CommonUtils.isNotEmpty(s), Position::getName, s);

            List<Position> records = positionService.list(query);

            return VueRecordUtils.covertIdTree(records);
        });
    }
}

