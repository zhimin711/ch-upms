package com.ch.cloud.nacos.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch.cloud.nacos.NacosAPI;
import com.ch.cloud.nacos.client.NacosNamespacesClient;
import com.ch.cloud.nacos.domain.NacosCluster;
import com.ch.cloud.nacos.service.INacosClusterService;
import com.ch.cloud.upms.model.ApplyRecord;
import com.ch.cloud.upms.model.Namespace;
import com.ch.cloud.upms.model.Project;
import com.ch.cloud.upms.pojo.NacosNamespace;
import com.ch.cloud.upms.pojo.NamespaceDto;
import com.ch.cloud.upms.pojo.NamespaceType;
import com.ch.cloud.upms.pojo.UserProjectNamespaceDto;
import com.ch.cloud.upms.service.IApplyRecordService;
import com.ch.cloud.upms.service.INamespaceService;
import com.ch.cloud.upms.service.IProjectService;
import com.ch.cloud.upms.utils.RequestUtils;
import com.ch.e.ExceptionUtils;
import com.ch.e.PubError;
import com.ch.pojo.VueRecord;
import com.ch.result.InvokerPage;
import com.ch.result.PageResult;
import com.ch.result.Result;
import com.ch.result.ResultUtils;
import com.ch.s.ApproveStatus;
import com.ch.toolkit.UUIDGenerator;
import com.ch.utils.BeanUtilsV2;
import com.ch.utils.CommonUtils;
import com.ch.utils.DateUtils;
import com.ch.utils.VueRecordUtils;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * <p>
 * 关系-命名空间表 前端控制器
 * </p>
 *
 * @author zhimin.ma
 * @since 2021-09-28
 */
@RestController
@RequestMapping("/nacos/namespaces")
public class NacosNamespacesController {

    @Autowired
    private INamespaceService    namespaceService;
    @Autowired
    private INacosClusterService nacosClusterService;
    @Autowired
    private IProjectService      projectService;
    @Autowired
    private IApplyRecordService  applyRecordService;

    @Autowired
    private NacosNamespacesClient nacosNamespacesClient;


    @ApiOperation(value = "分页查询", notes = "分页查询命名空间")
    @GetMapping(value = {"{num:[0-9]+}/{size:[0-9]+}"})
    public PageResult<Namespace> page(Namespace record,
                                      @PathVariable(value = "num") int pageNum,
                                      @PathVariable(value = "size") int pageSize) {
        return ResultUtils.wrapPage(() -> {
            record.setType(NamespaceType.NACOS);
            Page<Namespace> page = namespaceService.page(record, pageNum, pageSize);
            return InvokerPage.build(page.getTotal(), page.getRecords());
        });
    }

    @ApiOperation(value = "添加", notes = "添加命名空间")
    @PostMapping
    public Result<Boolean> add(@RequestBody Namespace record) {
        return ResultUtils.wrapFail(() -> {
            checkSaveOrUpdate(record);
            record.setUid(UUIDGenerator.generateUid().toString());
            boolean syncOk = nacosNamespacesClient.add(record);
            if (!syncOk) {
                ExceptionUtils._throw(PubError.CONNECT, "create nacos namespace failed!");
            }
            return namespaceService.save(record);
        });
    }

    @ApiOperation(value = "修改", notes = "修改命名空间")
    @PutMapping({"{id:[0-9]+}"})
    public Result<Boolean> edit(@RequestBody Namespace record) {
        return ResultUtils.wrapFail(() -> {
            checkSaveOrUpdate(record);
            boolean syncOk = nacosNamespacesClient.edit(record);
            if (!syncOk) {
                ExceptionUtils._throw(PubError.CONNECT, "update nacos namespace failed!");
            }
            return namespaceService.updateById(record);
        });
    }

    private void checkSaveOrUpdate(Namespace record) {
        record.setType(NamespaceType.NACOS);
        if (record.getId() != null) {
            Namespace orig = namespaceService.getById(record.getId());
            record.setClusterId(orig.getClusterId());
            record.setUid(orig.getUid());
        }
    }

    @GetMapping({"{id:[0-9]+}"})
    public Result<NamespaceDto> find(@PathVariable Long id) {
        return ResultUtils.wrapFail(() -> {
            Namespace namespace = namespaceService.getById(id);
            if (namespace == null) return null;
            NacosCluster cluster = nacosClusterService.getById(namespace.getClusterId());
            NamespaceDto dto = BeanUtilsV2.clone(namespace, NamespaceDto.class);
            namespace.setAddr(cluster.getUrl());
            NacosNamespace nn = nacosNamespacesClient.fetch(namespace);
            if (nn != null) {
                dto.setConfigCount(nn.getConfigCount());
                dto.setQuota(nn.getQuota());
            }
            return dto;
        });
    }


    @ApiOperation(value = "删除", notes = "删除命名空间")
    @DeleteMapping({"{id:[0-9]+}"})
    public Result<Boolean> delete(@PathVariable Long id) {
        return ResultUtils.wrapFail(() -> {
            Namespace orig = namespaceService.getById(id);
            nacosNamespacesClient.delete(orig);
            return namespaceService.removeById(id);
        });
    }

    @ApiOperation(value = "同步", notes = "同步-NACOS命名空间")
    @GetMapping({"/sync/{clusterId}"})
    public Result<Boolean> sync(@PathVariable Long clusterId) {
        return ResultUtils.wrapFail(() -> {
            NacosCluster cluster = nacosClusterService.getById(clusterId);
            ExceptionUtils.assertEmpty(cluster, PubError.CONFIG, "nacos address");
            List<NacosNamespace> list = nacosNamespacesClient.fetchAll(cluster.getUrl());
            if (CommonUtils.isNotEmpty(list)) {
                saveNacosNamespaces(list, clusterId);
            }
            return true;
        });
    }

    private void saveNacosNamespaces(List<NacosNamespace> list, Long clusterId) {
        if (list.isEmpty()) return;
        list.forEach(e -> {
            Namespace record = new Namespace();
            record.setClusterId(clusterId);
            record.setType(NamespaceType.NACOS);
            record.setUid(e.getNamespace());
            record.setName(e.getNamespaceShowName());
            Namespace orig = namespaceService.getOne(Wrappers.lambdaQuery(record).eq(Namespace::getUid, record.getUid()));
            if (orig != null) {
                orig.setName(e.getNamespaceShowName());
                namespaceService.updateById(orig);
            } else {
                namespaceService.save(record);
            }
        });
    }

    @GetMapping({"{id:[0-9]+}/{projectId:[0-9]+}/users"})
    public Result<UserProjectNamespaceDto> findNamespaceProjectUsers(@PathVariable Long id, @PathVariable Long projectId) {
        return ResultUtils.wrapList(() -> namespaceService.findUsers(id, projectId));
    }

    @PostMapping({"{id:[0-9]+}/{projectId:[0-9]+}/users"})
    public Result<Integer> saveProjectUsers(@PathVariable Long id, @PathVariable Long projectId, @RequestBody List<String> userIds) {
        return ResultUtils.wrap(() -> namespaceService.assignUsers(id, projectId, userIds));
    }

    private void checkUserProject(String username, Long projectId) {
        boolean exists = projectService.exists(username, projectId);
        if (!exists) ExceptionUtils._throw(PubError.NOT_EXISTS, username + "+" + projectId);
    }


    @GetMapping({"{id}/projects"})
    public Result<VueRecord> findProjects(@PathVariable Long id, @RequestParam(value = "s", required = false) String name) {
        return ResultUtils.wrapList(() -> {
            List<Project> projects = projectService.findByNamespaceIdAndName(id, name);
            return VueRecordUtils.covertIdList(projects);
        });
    }
}

