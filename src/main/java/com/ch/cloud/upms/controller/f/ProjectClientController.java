package com.ch.cloud.upms.controller.f;

import com.ch.cloud.upms.model.Project;
import com.ch.cloud.upms.service.IUserService;
import com.ch.cloud.upms.utils.RequestUtils;
import com.ch.pojo.VueRecord2;
import com.ch.result.Result;
import com.ch.result.ResultUtils;
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
 * @date 2022/4/28 08:34
 */
@RestController
@RequestMapping("/projects")
public class ProjectClientController {

    @Autowired
    private IUserService userService;

    @GetMapping({"{projectId:[0-9]+}/users"})
    public Result<VueRecord2> findUsers(@PathVariable Long projectId) {
        return ResultUtils.wrapList(() -> {
            List<Project> projectList = userService.findProjectsByUsernameAndTenantId(RequestUtils.getHeaderUser(), projectId);
            return projectList.stream().map(e -> {
                VueRecord2 record = new VueRecord2();
                record.setValue(e.getId() + "");
                record.setLabel(e.getName());
                record.setKey(e.getCode() + "");
                return record;
            }).collect(Collectors.toList());
        });
    }
}
