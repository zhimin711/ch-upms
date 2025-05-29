package com.ch.cloud.upms.log.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch.cloud.upms.log.dto.LogQueryDTO;
import com.ch.cloud.upms.log.model.OPRecord;
import com.ch.cloud.upms.service.IOPRecordService;
import com.ch.result.InvokerPage;
import com.ch.result.PageResult;
import com.ch.result.ResultUtils;
import com.ch.utils.CommonUtils;
import io.swagger.v3.oas.annotations.Operation; // 新增: 引入 OpenAPI 3.0 的 @Operation 注解
import io.swagger.v3.oas.annotations.tags.Tag; // 新增: 引入 OpenAPI 3.0 的 @Tag 注解
import org.springframework.validation.annotation.Validated;
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
@Tag(name = "操作日志", description = "操作日志相关接口") // 修改: 替换 @Api 为 @Tag
public class OPRecordController {
    
    @Resource
    private IOPRecordService opRecordService;
    
    @Operation(summary = "分页查询操作日志", description = "根据条件分页查询操作日志列表")
    @GetMapping(value = {"{num:[0-9]+}/{size:[0-9]+}"})
    public PageResult<OPRecord> page(@Validated LogQueryDTO record, @PathVariable(value = "num") int pageNum,
            @PathVariable(value = "size") int pageSize) {
        return ResultUtils.wrapPage(() -> {
            Page<OPRecord> page = opRecordService.lambdaQuery()
                    .select(OPRecord::getId, OPRecord::getUrl, OPRecord::getMethod, OPRecord::getAuthCode,
                            OPRecord::getStatus, OPRecord::getOperator, OPRecord::getRequestIp,
                            OPRecord::getRequestTime, OPRecord::getResponseTime, OPRecord::getErrorMessage)
                    .notLikeRight(OPRecord::getAuthCode, "LOGIN_")
                    .likeRight(CommonUtils.isNotEmpty(record.getAuthCode()), OPRecord::getAuthCode, record.getAuthCode())
                    .likeRight(CommonUtils.isNotEmpty(record.getUrl()), OPRecord::getUrl, record.getUrl())
                    .between(OPRecord::getRequestTime,record.getStartTime().getTime(), record.getEndTime().getTime())
                    .page(new Page<>(pageNum, pageSize))
            ;
            return new InvokerPage.Page<>(page.getTotal(), page.getRecords());
        });
    }
}