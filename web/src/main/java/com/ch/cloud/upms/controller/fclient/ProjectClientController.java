package com.ch.cloud.upms.controller.fclient;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch.cloud.upms.client.UpmsProjectClientService;
import com.ch.cloud.upms.dto.ProjectDto;
import com.ch.cloud.upms.dto.UserDto;
import com.ch.cloud.upms.mapstrut.MapperProject;
import com.ch.cloud.upms.model.Project;
import com.ch.cloud.upms.service.IProjectService;
import com.ch.cloud.upms.service.IUserService;
import com.ch.result.InvokerPage;
import com.ch.result.PageResult;
import com.ch.result.Result;
import com.ch.result.ResultUtils;
import com.ch.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * desc:
 *
 * @author zhimin
 * @date 2022/4/28 08:34
 */
@RestController
@RequestMapping("/projects")
public class ProjectClientController implements UpmsProjectClientService {

    @Autowired
    private IUserService userService;

    @Autowired
    private IProjectService projectService;

    @GetMapping({"{projectId:[0-9]+}/users"})
    public Result<UserDto> findUsers(@PathVariable Long projectId) {
        return ResultUtils.wrapList(() -> {
//            List<Project> projectList = userService.findProjectsByUsernameAndTenantId(RequestUtils.getHeaderUser(), projectId);
//            return projectList.stream().map(e -> {
//                VueRecord2 record = new VueRecord2();
//                record.setValue(e.getId() + "");
//                record.setLabel(e.getName());
//                record.setKey(e.getCode() + "");
//                return record;
//            }).collect(Collectors.toList());
            return null;
        });
    }

    @GetMapping("page/{num:[0-9]+}/{size:[0-9]+}")
    @Override
    public PageResult<ProjectDto> page(ProjectDto projectDto,
                                       @PathVariable(value = "num") int pageNum,
                                       @PathVariable(value = "size") int pageSize) {
        return ResultUtils.wrapPage(() -> {

            Page<Project> page = projectService.page(new Page<>(pageNum, pageSize), Wrappers.query(new Project())
                    .eq("status", "1")
                    .like(CommonUtils.isNotEmpty(projectDto.getName()), "name", projectDto.getName())
                    .like(CommonUtils.isNotEmpty(projectDto.getTenantName()), "tenant_name", projectDto.getName()));
            List<ProjectDto> list = page.getRecords().stream()
                    .map(MapperProject.INSTANCE::toClientDto).collect(Collectors.toList());
            return InvokerPage.build(page.getTotal(), list);
        });
    }

    @GetMapping("list")
    @Override
    public Result<ProjectDto> list(@RequestParam String name, @RequestParam String tenant) {
        List<Project> projectList = projectService.list(Wrappers.query(new Project())
                .eq("status", "1")
                .like(CommonUtils.isNotEmpty(name), "name", name)
                .like(CommonUtils.isNotEmpty(tenant), "tenant_name", tenant));
        return ResultUtils.wrapList(() -> projectList.stream()
                .map(MapperProject.INSTANCE::toClientDto).collect(Collectors.toList()));
    }

    @PostMapping
    @Override
    public Result<ProjectDto> findByIds(@RequestBody List<Long> ids) {
        List<Project> projectList = projectService.listByIds(ids);
        return ResultUtils.wrapList(() -> projectList.stream()
                .map(MapperProject.INSTANCE::toClientDto).collect(Collectors.toList()));
    }
}
