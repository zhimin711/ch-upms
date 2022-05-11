package com.ch.cloud.upms.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch.cloud.upms.model.ApplyRecord;
import com.ch.cloud.upms.model.Namespace;
import com.ch.cloud.upms.model.Project;
import com.ch.cloud.upms.pojo.NamespaceDto;
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
import com.ch.utils.BeanUtilsV2;
import com.ch.utils.VueRecordUtils;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/namespace")
public class NamespaceController {

    @Autowired
    private INamespaceService   namespaceService;
    @Autowired
    private IProjectService     projectService;
    @Autowired
    private IApplyRecordService applyRecordService;


    @ApiOperation(value = "分页查询", notes = "分页查询命名空间")
    @GetMapping(value = {"{num:[0-9]+}/{size:[0-9]+}"})
    public PageResult<Namespace> page(Namespace record,
                                      @PathVariable(value = "num") int pageNum,
                                      @PathVariable(value = "size") int pageSize) {
        return ResultUtils.wrapPage(() -> {
            Page<Namespace> page = namespaceService.page(record, pageNum, pageSize);
            return InvokerPage.build(page.getTotal(), page.getRecords());
        });
    }

    @GetMapping({"{id:[0-9]+}"})
    public Result<NamespaceDto> find(@PathVariable Long id) {
        return ResultUtils.wrapFail(() -> {
            Namespace namespace = namespaceService.getById(id);
            if (namespace == null) return null;
            NamespaceDto dto = BeanUtilsV2.clone(namespace, NamespaceDto.class);
            return dto;
        });
    }


    @ApiOperation(value = "删除", notes = "删除命名空间")
    @DeleteMapping({"{id:[0-9]+}"})
    public Result<Boolean> delete(@PathVariable Long id) {
        return ResultUtils.wrapFail(() -> {
            Namespace orig = namespaceService.getById(id);
            return namespaceService.removeById(id);
        });
    }

    @PostMapping({"apply/{projectId:[0-9]+}"})
    public Result<Boolean> apply(@PathVariable Long projectId, @RequestBody List<Long> namespaceIds) {
        return ResultUtils.wrap(() -> {
            String username = RequestUtils.getHeaderUser();
            checkUserProject(username, projectId);
            ApplyRecord record = new ApplyRecord();
            record.setCreateBy(username);
            record.setType("1");
            record.setDataKey(projectId + "");
            List<ApplyRecord> list = applyRecordService.list(Wrappers.query(record).in("status", Lists.newArrayList(ApproveStatus.STAY.getCode())));
            if (!list.isEmpty()) {
                ExceptionUtils._throw(PubError.EXISTS, "已提交申请,请联系管理员审核！");
            }
            Project project = projectService.getById(projectId);

            JSONObject object = new JSONObject();
            object.put("userId", username);
            object.put("projectId", projectId);
            object.put("projectName", project.getName());
            object.put("namespaceIds", namespaceIds);

            List<String> names = Lists.newArrayList();
            for (Long nid : namespaceIds) {
                Namespace n = namespaceService.getById(nid);
                names.add(n.getName());
            }
            object.put("namespaceNames", String.join("|", names));

            record.setContent(object.toJSONString());

            return applyRecordService.save(record);
        });
    }

    private void checkUserProject(String username, Long projectId) {
        boolean exists = projectService.exists(username, projectId);
        if (!exists) ExceptionUtils._throw(PubError.NOT_EXISTS, username + "+" + projectId);
    }


    @GetMapping({"{uid}/projects"})
    public Result<VueRecord> findProjects(@PathVariable String uid, @RequestParam(value = "s", required = false) String name) {
        return ResultUtils.wrapList(() -> {
            Namespace q = new Namespace();
            q.setUid(uid);
            Namespace ns = namespaceService.getOne(Wrappers.query(q));
            if (ns == null) ExceptionUtils._throw(PubError.NOT_EXISTS, uid);
            List<Project> projects = projectService.findByNamespaceIdAndName(ns.getId(), name);
            return VueRecordUtils.covertIdList(projects);
        });
    }


//    @GetMapping({"{id:[0-9]+}/{projectId:[0-9]+}/users"})
    public Result<UserProjectNamespaceDto> findProjectUser(@PathVariable Long id, @PathVariable Long projectId) {
        return ResultUtils.wrapList(() -> namespaceService.findUsers(id, projectId));
    }

//    @PostMapping({"{id:[0-9]+}/{projectId:[0-9]+}/users"})
    public Result<Integer> saveProjectUsers(@PathVariable Long id, @PathVariable Long projectId, @RequestBody List<String> userIds) {
        return ResultUtils.wrap(() -> namespaceService.assignUsers(id, projectId, userIds));
    }
}

