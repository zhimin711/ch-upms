package com.ch.cloud.nacos.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch.cloud.nacos.domain.NacosCluster;
import com.ch.cloud.nacos.service.INacosClusterService;
import com.ch.cloud.upms.model.Dict;
import com.ch.result.InvokerPage;
import com.ch.result.PageResult;
import com.ch.result.Result;
import com.ch.result.ResultUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * desc:nacos集群Controller
 *
 * @author zhimin
 * @date 2022/4/23 22:44
 */
@RestController
@RequestMapping("/nacos/cluster")
public class NacosClusterController {

    @Autowired
    private INacosClusterService nacosClusterService;

    @ApiOperation(value = "分页查询" , notes = "分页查询nacos集群")
    @GetMapping(value = {"{num:[0-9]+}/{size:[0-9]+}"})
    public PageResult<NacosCluster> page(NacosCluster record,
                                         @PathVariable(value = "num") int pageNum,
                                         @PathVariable(value = "size") int pageSize) {
        Page<NacosCluster> page = nacosClusterService.page(new Page<>(pageNum, pageSize), Wrappers.query(record));
        return PageResult.success(page.getTotal(), page.getRecords());
    }

    @ApiOperation(value = "添加" , notes = "添加nacos集群")
    @PostMapping
    public Result<Boolean> add(@RequestBody NacosCluster record) {
        return ResultUtils.wrapFail(() -> nacosClusterService.save(record));
    }

    @ApiOperation(value = "修改" , notes = "修改nacos集群")
    @PutMapping({"{id:[0-9]+}"})
    public Result<Boolean> edit(@RequestBody NacosCluster record) {
        return ResultUtils.wrapFail(() -> nacosClusterService.updateById(record));
    }

    //@GetMapping({"{id:[0-9]+}"})
    public Result<NacosCluster> find(@PathVariable Long id) {
        return ResultUtils.wrapFail(() -> nacosClusterService.getById(id));
    }

    @ApiOperation(value = "删除" , notes = "删除nacos集群")
    //@DeleteMapping({"{id:[0-9]+}"})
    public Result<Boolean> delete(@PathVariable Long id) {
        return ResultUtils.wrapFail(() -> nacosClusterService.removeById(id));
    }
}
