package com.ch.cloud.upms.user.controller.client;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch.cloud.upms.client.UpmsProjectClient;
import com.ch.cloud.upms.dto.ProjectDto;
import com.ch.cloud.upms.dto.ProjectUserRoleDTO;
import com.ch.cloud.upms.mapstrut.MapperProject;
import com.ch.cloud.upms.service.IProjectService;
import com.ch.cloud.upms.user.model.Project;
import com.ch.e.Assert;
import com.ch.e.PubError;
import com.ch.result.InvokerPage;
import com.ch.result.PageResult;
import com.ch.result.Result;
import com.ch.result.ResultUtils;
import com.ch.utils.CommonUtils;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * desc:
 *
 * @author zhimin
 * @since 2022/4/28 08:34
 */
@RestController
@RequestMapping("/projects")
public class ProjectClientController implements UpmsProjectClient {
    
    @Autowired
    private IProjectService projectService;
    
    @GetMapping({"users"})
    @Override
    public Result<ProjectUserRoleDTO> findUsers(@RequestParam Long projectId) {
        return ResultUtils.wrap(() -> projectService.findUsers(projectId));
    }
    
    @GetMapping("page/{num:[0-9]+}/{size:[0-9]+}")
    @Override
    public PageResult<ProjectDto> page(@PathVariable(value = "num") int pageNum,
            @PathVariable(value = "size") int pageSize, @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "tenantName", required = false) String tenantName) {
        return ResultUtils.wrapPage(() -> {
            
            Page<Project> page = projectService.page(new Page<>(pageNum, pageSize),
                    Wrappers.query(new Project()).eq("status", "1").like(CommonUtils.isNotEmpty(name), "name", name)
                            .like(CommonUtils.isNotEmpty(tenantName), "tenant_name", tenantName)
                            .orderByAsc(Lists.newArrayList("tenant_id", "sort", "id")));
            List<ProjectDto> list = page.getRecords().stream().map(MapperProject.INSTANCE::toClientDto)
                    .collect(Collectors.toList());
            return InvokerPage.build(page.getTotal(), list);
        });
    }
    
    @GetMapping("list")
    @Override
    public Result<ProjectDto> list(@RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "tenant", required = false) String tenant) {
        List<Project> projectList = projectService.list(
                Wrappers.query(new Project()).eq("status", "1").like(CommonUtils.isNotEmpty(name), "code", code)
                        .like(CommonUtils.isNotEmpty(name), "name", name)
                        .like(CommonUtils.isNotEmpty(tenant), "tenant_name", tenant));
        return ResultUtils.wrapList(
                () -> projectList.stream().map(MapperProject.INSTANCE::toClientDto).collect(Collectors.toList()));
    }
    
    @GetMapping("info")
    @Override
    public Result<ProjectDto> infoByIdOrCode(@RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "code", required = false) String code) {
        return ResultUtils.wrap(() -> {
            Assert.isFalse(id == null && CommonUtils.isEmpty(code), PubError.NON_NULL, "id or code");
            Project record;
            if (id != null) {
                record = projectService.getById(id);
            } else {
                record = projectService.findByCode(code);
            }
            return MapperProject.INSTANCE.toClientDto(record);
        });
    }
    
    @PostMapping("info")
    @Override
    public Result<ProjectDto> infoByIds(@RequestBody List<Long> ids) {
        List<Project> projectList = projectService.listByIds(ids);
        return ResultUtils.wrapList(
                () -> projectList.stream().map(MapperProject.INSTANCE::toClientDto).collect(Collectors.toList()));
    }
}
