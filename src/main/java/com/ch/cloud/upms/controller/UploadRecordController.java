package com.ch.cloud.upms.controller;

import com.ch.cloud.upms.model.BtUploadFile;
import com.ch.cloud.upms.service.IUploadFileService;
import com.ch.result.InvokerPage;
import com.ch.result.PageResult;
import com.ch.result.Result;
import com.ch.result.ResultUtils;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * 角色管理
 *
 * @author 01370603
 * @date 2018/12/22 22:35
 */

@RestController
@RequestMapping("upload/record")
public class UploadRecordController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    IUploadFileService uploadFileService;

    @Value("${path.upload:}")
    private String uploadPath;


    @PostMapping(value = {"{num}/{size}"})
    public PageResult<BtUploadFile> page(@RequestBody BtUploadFile record,
                                         @PathVariable(value = "num") int pageNum,
                                         @PathVariable(value = "size") int pageSize) {
        return ResultUtils.wrapPage(() -> {
            PageInfo<BtUploadFile> pageInfo = uploadFileService.findPage(record, pageNum, pageSize);
            return new InvokerPage.Page<>(pageInfo.getTotal(), pageInfo.getList());
        });
    }

    //    @PostMapping("save")
    public Result<Integer> add(@RequestBody BtUploadFile record) {
        return ResultUtils.wrapFail(() -> uploadFileService.save(record));
    }

    @PostMapping({"save/{id}"})
    public Result<Integer> edit(@PathVariable int id, @RequestBody BtUploadFile record) {

        return ResultUtils.wrapFail(() -> uploadFileService.update(record));
    }

    //    @PostMapping({"delete"})
    public Result<Integer> delete(Long id) {
        return ResultUtils.wrapFail(() -> uploadFileService.delete(id));
    }

}
