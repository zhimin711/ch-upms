package com.ch.cloud.upms.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch.Status;
import com.ch.cloud.upms.manage.IDictManage;
import com.ch.cloud.upms.model.Dict;
import com.ch.cloud.upms.service.IDictService;
import com.ch.cloud.upms.utils.RequestUtils;
import com.ch.e.ExceptionUtils;
import com.ch.e.PubError;
import com.ch.pojo.VueRecord;
import com.ch.result.PageResult;
import com.ch.result.Result;
import com.ch.result.ResultUtils;
import com.ch.utils.CommonUtils;
import com.ch.utils.VueRecordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private IDictManage dictManage;

    @GetMapping({"/list"})
    public Result<VueRecord> search(@RequestParam(value = "s", required = false) String name) {
        return ResultUtils.wrapList(() -> VueRecordUtils.covertCodeTree(dictService.findByPidAndName(0L, name, Status.ENABLED)));
    }

    @GetMapping({"/data/{code}"})
    public Result<VueRecord> search2(@PathVariable String code, @RequestParam(value = "s", required = false) String name) {
        return ResultUtils.wrapList(() -> {
            Dict dict = dictManage.findByCode(code);
            if (dict == null || CommonUtils.isEmpty(dict.getChildren())) return null;
            return VueRecordUtils.covertCodeTree(dict.getChildren().stream()
                    .filter(e -> CommonUtils.isEmpty(name) || e.getName().contains(name))
                    .collect(Collectors.toList()));
        });
    }
}

