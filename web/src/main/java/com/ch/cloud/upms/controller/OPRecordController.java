package com.ch.cloud.upms.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch.cloud.upms.model.OPRecord;
import com.ch.cloud.upms.service.IOPRecordService;
import com.ch.result.InvokerPage;
import com.ch.result.PageResult;
import com.ch.result.ResultUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 日志表-操作记录 前端控制器
 * </p>
 *
 * @author zhimin.ma
 * @since 2020-03-25
 */
@RestController
@RequestMapping("/op/record")
@Tag(name = "操作日志", description = "操作日志")
public class OPRecordController {
    
    @Resource
    private IOPRecordService opRecordService;
    
    @GetMapping(value = {"{num:[0-9]+}/{size:[0-9]+}"})
    public PageResult<OPRecord> page(OPRecord record, @PathVariable(value = "num") int pageNum,
            @PathVariable(value = "size") int pageSize) {
        return ResultUtils.wrapPage(() -> {
            Page<OPRecord> page = opRecordService.page(record, pageNum, pageSize);
            return new InvokerPage.Page<>(page.getTotal(), page.getRecords());
        });
    }
}