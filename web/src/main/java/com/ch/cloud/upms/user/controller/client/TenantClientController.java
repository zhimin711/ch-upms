package com.ch.cloud.upms.user.controller.client;

import com.ch.cloud.upms.client.UpmsTenantClient;
import com.ch.cloud.upms.dto.ProjectDto;
import com.ch.cloud.upms.user.model.Project;
import com.ch.cloud.upms.service.IProjectService;
import com.ch.cloud.upms.service.IUserService;
import com.ch.result.Result;
import com.ch.result.ResultUtils;
import com.ch.utils.BeanUtilsV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/tenants")
public class TenantClientController implements UpmsTenantClient {

    @Autowired
    private IUserService    userService;
    @Autowired
    private IProjectService projectService;

    @GetMapping({"{id:[0-9]+}/projects"})
    public Result<ProjectDto> findProjects(@PathVariable Long id) {
        return ResultUtils.wrapList(() -> {
            List<Project> projectList = projectService.findByTenantId(id);
            return projectList.stream().map(e -> BeanUtilsV2.clone(e, ProjectDto.class)).collect(Collectors.toList());
        });
    }
}
