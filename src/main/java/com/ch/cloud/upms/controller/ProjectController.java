package com.ch.cloud.upms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch.Separator;
import com.ch.cloud.upms.model.Namespace;
import com.ch.cloud.upms.model.Project;
import com.ch.cloud.upms.pojo.UserInfo;
import com.ch.cloud.upms.service.IDepartmentService;
import com.ch.cloud.upms.service.IProjectService;
import com.ch.cloud.upms.utils.RequestUtils;
import com.ch.pojo.VueRecord2;
import com.ch.result.PageResult;
import com.ch.result.Result;
import com.ch.result.ResultUtils;
import com.ch.utils.CommonUtils;
import com.ch.utils.DateUtils;
import com.ch.utils.StringUtilsV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 业务-项目表 前端控制器
 * </p>
 *
 * @author zhimin.ma
 * @since 2021-09-23
 */
@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private IProjectService    projectService;
    @Autowired
    private IDepartmentService departmentService;

    @GetMapping(value = {"{num:[0-9]+}/{size:[0-9]+}"})
    public PageResult<Project> page(Project record,
                                    @PathVariable(value = "num") int pageNum,
                                    @PathVariable(value = "size") int pageSize) {
        Page<Project> pageInfo = projectService.page(record, pageNum, pageSize);
        return PageResult.success(pageInfo.getTotal(), pageInfo.getRecords());
    }

    @PostMapping
    public Result<Boolean> add(@RequestBody Project record) {
        checkSaveOrUpdate(record);
        record.setCreateBy(RequestUtils.getHeaderUser());
        return ResultUtils.wrapFail(() -> projectService.save(record));
    }

    private void checkSaveOrUpdate(Project record) {
        List<Long> deptIds = StringUtilsV2.parseIds(record.getDepartment());
        List<String> names = departmentService.findNames(deptIds);
        record.setDepartmentName(String.join(Separator.OBLIQUE_LINE, names));
    }

    @GetMapping({"{id:[0-9]+}"})
    public Result<Project> get(@PathVariable Long id) {
        return ResultUtils.wrapFail(() -> projectService.getWithUserById(id));
    }

    @PutMapping({"{id:[0-9]+}"})
    public Result<Boolean> edit(@RequestBody Project record) {
        return ResultUtils.wrapFail(() -> {
            checkSaveOrUpdate(record);
            record.setUpdateBy(RequestUtils.getHeaderUser());
            record.setUpdateAt(DateUtils.current());

            return projectService.updateById(record);
        });
    }

    @DeleteMapping({"{id:[0-9]+}"})
    public Result<Boolean> delete(@PathVariable Long id) {
        return ResultUtils.wrapFail(() -> projectService.removeById(id));
    }

//    @GetMapping(value = {"tree/{type}"})
//    public Result<VueRecord> tree(@PathVariable String type) {
//        List<Project> tree = projectService.findTreeBy(type);
//        List<VueRecord> records = VueRecordUtils.covertCodeTree(tree);
//        return Result.success(records);
//    }

//    @GetMapping({"users"})
//    public Result<UserInfo> findUsers() {
//        return ResultUtils.wrapList(() -> projectUserTool.findUserInfo(0L));
//    }

    @GetMapping({"{id:[0-9]+}/users"})
    public Result<String> findProjectUser(@PathVariable Long id) {
        return ResultUtils.wrapList(() -> projectService.findUsers(id));
    }

    @PostMapping({"{id:[0-9]+}/users"})
    public Result<Integer> saveProjectUsers(@PathVariable Long id, @RequestBody List<String> userIds) {
        return ResultUtils.wrap(() -> projectService.assignUsers(id, userIds));
    }

    @GetMapping({"{id:[0-9]+}/namespaces"})
    public Result<VueRecord2> findProjectNamespaces(@PathVariable Long id) {
        return ResultUtils.wrapList(() -> {
            List<Namespace> list = projectService.findNamespaces(id);
            return list.stream().map(e -> {
                VueRecord2 record2 = new VueRecord2();
                record2.setLabel(e.getName());
                record2.setValue(e.getId() + "");
                record2.setKey(e.getUid());
                return record2;
            }).collect(Collectors.toList());
        });
    }

    @PostMapping({"{id:[0-9]+}/namespaces"})
    public Result<Integer> saveProjectNamespaces(@PathVariable Long id, @RequestBody List<Long> namespaceIds) {
        return ResultUtils.wrap(() -> projectService.assignNamespaces(id, namespaceIds));
    }
}

