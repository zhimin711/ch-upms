package com.ch.cloud.upms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch.cloud.upms.model.Namespace;
import com.ch.cloud.upms.model.Project;
import com.ch.cloud.upms.service.IProjectService;
import com.ch.cloud.upms.utils.RequestUtils;
import com.ch.pojo.VueRecord2;
import com.ch.result.PageResult;
import com.ch.result.Result;
import com.ch.result.ResultUtils;
import com.ch.utils.CommonUtils;
import com.ch.utils.DateUtils;
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
    private IProjectService projectService;

    @GetMapping(value = {"{num:[0-9]+}/{size:[0-9]+}"})
    public PageResult<Project> page(Project record,
                                    @PathVariable(value = "num") int pageNum,
                                    @PathVariable(value = "size") int pageSize) {
        Page<Project> pageInfo = projectService.page(record, pageNum, pageSize);
        return PageResult.success(pageInfo.getTotal(), pageInfo.getRecords());
    }

    @PostMapping
    public Result<Boolean> add(@RequestBody Project record) {
        record.setCreateBy(RequestUtils.getHeaderUser());
        convertParentCode(record);
        return ResultUtils.wrapFail(() -> projectService.save(record));
    }

    private void convertParentCode(Project record) {
        if (CommonUtils.isEmpty(record.getParentCode())) {
            record.setParentCode(null);
            record.setParentName(null);
        } else if (CommonUtils.isEquals(record.getParentCode(), record.getCode())) {
            record.setParentCode(null);
            record.setParentName(null);
        }
    }

    @PutMapping({"{id:[0-9]+}"})
    public Result<Boolean> edit(@PathVariable Long id, @RequestBody Project record) {
        record.setUpdateBy(RequestUtils.getHeaderUser());
        record.setUpdateAt(DateUtils.current());

        convertParentCode(record);
        return ResultUtils.wrapFail(() -> projectService.updateById(record));
    }

    @PostMapping({"delete"})
    public Result<Boolean> delete(Long id) {
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

//    @GetMapping({"{id:[0-9]+}/users"})
//    public Result<String> findProjectUser(@PathVariable Long id) {
//        return ResultUtils.wrapList(() -> {
//            if (id < 1) {
//                Result<UserInfo> res1 = upmsClientService.userInfo("");
//                return Lists.newArrayList(res1.getRows());
//            }
//        });
//    }

//    @PostMapping({"{id:[0-9]+}/users"})
//    public Result<Integer> saveProjectUser(@PathVariable Long id, @RequestBody List<String> userIds) {
//        return ResultUtils.wrap(() -> projectUserTool.assignUser(id, userIds));
//    }

    //    @GetMapping(value = {"user/tree"})
//    public Result<VueRecord2> treeByUser() {
//        List<Project> records = projectService.findByUserId(RequestUtils.getUser());
//        List<VueRecord2> tree = ProjectCodeUtils.covertIdTree(records);
//        return Result.success(tree);
//    }

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

