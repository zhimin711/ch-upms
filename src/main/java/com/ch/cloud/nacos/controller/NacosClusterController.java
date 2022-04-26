package com.ch.cloud.nacos.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch.cloud.nacos.client.NacosClusterClient;
import com.ch.cloud.nacos.domain.NacosCluster;
import com.ch.cloud.nacos.service.INacosClusterService;
import com.ch.cloud.upms.model.Namespace;
import com.ch.cloud.upms.pojo.NamespaceType;
import com.ch.cloud.upms.service.INamespaceService;
import com.ch.e.ExceptionUtils;
import com.ch.e.PubError;
import com.ch.pojo.VueRecord;
import com.ch.result.PageResult;
import com.ch.result.Result;
import com.ch.result.ResultUtils;
import com.ch.utils.CommonUtils;
import com.ch.utils.VueRecordUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @Autowired
    private NacosClusterClient   nacosClusterClient;

    @Autowired
    private INamespaceService namespaceService;

    @ApiOperation(value = "分页查询", notes = "分页查询nacos集群")
    @GetMapping(value = {"{num:[0-9]+}/{size:[0-9]+}"})
    public PageResult<NacosCluster> page(NacosCluster record,
                                         @PathVariable(value = "num") int pageNum,
                                         @PathVariable(value = "size") int pageSize) {
        Page<NacosCluster> page = nacosClusterService.page(new Page<>(pageNum, pageSize), Wrappers.query(record));
        return PageResult.success(page.getTotal(), page.getRecords());
    }

    @ApiOperation(value = "添加", notes = "添加nacos集群")
    @PostMapping
    public Result<Boolean> add(@RequestBody NacosCluster record) {
        return ResultUtils.wrapFail(() -> nacosClusterService.save(record));
    }

    @ApiOperation(value = "修改", notes = "修改nacos集群")
    @PutMapping({"{id:[0-9]+}"})
    public Result<Boolean> edit(@RequestBody NacosCluster record) {
        return ResultUtils.wrapFail(() -> nacosClusterService.updateById(record));
    }

    @GetMapping({"{id:[0-9]+}"})
    public Result<NacosCluster> find(@PathVariable Long id) {
        Result<NacosCluster> result = ResultUtils.wrapFail(() -> {
            NacosCluster cluster = nacosClusterService.getById(id);
            ExceptionUtils.assertEmpty(cluster, PubError.CONFIG, "nacos cluster");
            return cluster;
        });
        if (result.isSuccess()) {
            Object info = nacosClusterClient.fetchNodes(result.get().getUrl());
            result.putExtra("nodes", info);
        }
        return result;
    }

    @ApiOperation(value = "删除", notes = "删除nacos集群")
    //@DeleteMapping({"{id:[0-9]+}"})
    public Result<Boolean> delete(@PathVariable Long id) {
        return ResultUtils.wrapFail(() -> nacosClusterService.removeById(id));
    }

    @GetMapping({"{id:[0-9]+}/namespaces"})
    public Result<VueRecord> findNamespaces(@PathVariable Long id, @RequestParam(name = "s", required = false) String name) {
        return ResultUtils.wrapList(() -> {
            Wrapper<Namespace> wrapper = Wrappers.lambdaQuery(Namespace.class).eq(Namespace::getType, NamespaceType.NACOS.getCode()).eq(Namespace::getClusterId, id).like(CommonUtils.isNotEmpty(name), Namespace::getName, name);
            List<Namespace> list = namespaceService.list(wrapper);
            return VueRecordUtils.covertIdTree(list);
        });
    }
}
