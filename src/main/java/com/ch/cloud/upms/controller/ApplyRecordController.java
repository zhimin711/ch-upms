package com.ch.cloud.upms.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch.cloud.upms.model.ApplyRecord;
import com.ch.cloud.upms.service.IApplyRecordService;
import com.ch.cloud.upms.utils.RequestUtils;
import com.ch.e.ExceptionUtils;
import com.ch.e.PubError;
import com.ch.result.InvokerPage;
import com.ch.result.PageResult;
import com.ch.result.Result;
import com.ch.result.ResultUtils;
import com.ch.s.ApproveStatus;
import com.ch.utils.CommonUtils;
import com.ch.utils.DateUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 业务-申请记录表 前端控制器
 * </p>
 *
 * @author zhimin.ma
 * @since 2021-11-12
 */
@RestController
@RequestMapping("/apply")
public class ApplyRecordController {

    @Autowired
    private IApplyRecordService applyRecordService;

    @ApiOperation(value = "Nacos申请分页查询", notes = "分页查询命名空间")
    @GetMapping(value = {"/nacos/{num:[0-9]+}/{size:[0-9]+}"})
    public PageResult<ApplyRecord> nacosPage(ApplyRecord record,
                                             @PathVariable(value = "num") int pageNum,
                                             @PathVariable(value = "size") int pageSize) {
        return ResultUtils.wrapPage(() -> {
            record.setType("1");
            Page<ApplyRecord> page = applyRecordService.page(new Page<>(pageNum, pageSize), Wrappers.query(record));
            return InvokerPage.build(page.getTotal(), page.getRecords());
        });
    }


    @ApiOperation(value = "审核空间", notes = "审核申请命名空间")
    @PostMapping({"/nacos/{id:[0-9]+}/approve"})
    public Result<Boolean> approveNacos(@RequestBody ApplyRecord record) {
        return ResultUtils.wrapFail(() -> {
            ApplyRecord orig = applyRecordService.getById(record.getId());
            if (orig == null) ExceptionUtils._throw(PubError.NOT_EXISTS, record.getId());
            if (!CommonUtils.isEquals(orig.getType(), "1")) {
                ExceptionUtils._throw(PubError.NOT_ALLOWED, "approve type is not [nacos]!");
            }
            if (ApproveStatus.fromValue(record.getStatus()).availableApprove()) {
                ExceptionUtils._throw(PubError.NOT_ALLOWED, "approve status is not correct!");
            }
            orig.setStatus(record.getStatus());
            orig.setApproveBy(RequestUtils.getHeaderUser());
            orig.setApproveAt(DateUtils.current());
            orig.setUpdateBy(RequestUtils.getHeaderUser());
            orig.setUpdateAt(DateUtils.current());
            return applyRecordService.approveNacos(orig);
        });
    }
}

