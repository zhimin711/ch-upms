package com.ch.cloud.upms.controller;

import com.ch.cloud.upms.model.OPRecord;
import com.ch.cloud.upms.service.IOPRecordService;
import com.ch.result.InvokerPage;
import com.ch.result.PageResult;
import com.ch.result.ResultUtils;
import com.ch.utils.CommonUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 登录日志
 *
 * @author 01370603
 * @date 2018/12/22 22:35
 */

@RestController
@RequestMapping("login/record")
public class LoginRecordController {

    @Resource
    IOPRecordService opRecordService;

    @GetMapping(value = {"{num:[0-9]+}/{size:[0-9]+}"})
    public PageResult<OPRecord> page(OPRecord record,
                                   @PathVariable(value = "num") int pageNum,
                                   @PathVariable(value = "size") int pageSize) {
        return ResultUtils.wrapPage(() -> {
            if(CommonUtils.isEmpty(record.getAuthCode()) || record.getAuthCode().startsWith("LOGIN_")){
                record.setAuthCode("LOGIN_");
            }
            PageInfo<OPRecord> pageInfo = opRecordService.findPageBy(record, pageNum, pageSize);
            return new InvokerPage.Page<>(pageInfo.getTotal(), pageInfo.getList());
        });
    }

}
