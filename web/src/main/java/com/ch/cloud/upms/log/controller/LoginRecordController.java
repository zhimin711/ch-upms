package com.ch.cloud.upms.log.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch.cloud.upms.log.dto.LogQueryDTO;
import com.ch.cloud.upms.log.model.OPRecord;
import com.ch.cloud.upms.service.IOPRecordService;
import com.ch.result.InvokerPage;
import com.ch.result.PageResult;
import com.ch.result.ResultUtils;
import com.ch.utils.CommonUtils;
import com.ch.utils.DateUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 登录日志
 *
 * @author 01370603
 * @since 2018/12/22 22:35
 */


@RestController
@RequestMapping("/login/record")
public class LoginRecordController {
    
    @Resource
    IOPRecordService opRecordService;
    
    @GetMapping(value = {"{num:[0-9]+}/{size:[0-9]+}"})
    public PageResult<OPRecord> page(LogQueryDTO record, @PathVariable(value = "num") int pageNum,
            @PathVariable(value = "size") int pageSize) {
        return ResultUtils.wrapPage(() -> {
            if (CommonUtils.isEmpty(record.getAuthCode()) || !record.getAuthCode().startsWith("LOGIN_")) {
                record.setAuthCode("LOGIN_");
            }
            Page<OPRecord> page = opRecordService.lambdaQuery()
                    .select(OPRecord::getId, OPRecord::getUrl, OPRecord::getMethod, OPRecord::getAuthCode,
                            OPRecord::getStatus, OPRecord::getOperator, OPRecord::getRequestIp,
                            OPRecord::getRequestTime, OPRecord::getResponseTime, OPRecord::getErrorMessage)
                    .likeRight(OPRecord::getAuthCode, record.getAuthCode())
                    .likeRight(CommonUtils.isNotEmpty(record.getUrl()), OPRecord::getUrl, record.getUrl())
                    .between(OPRecord::getRequestTime, record.getStartTime().getTime(),
                            DateUtils.endDayTime(record.getEndTime()).getTime())
                    .orderByDesc(OPRecord::getRequestTime, OPRecord::getId).page(new Page<>(pageNum, pageSize));
            return new InvokerPage.Page<>(page.getTotal(), page.getRecords());
        });
    }
    
}
