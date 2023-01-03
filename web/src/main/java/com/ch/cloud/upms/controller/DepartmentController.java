package com.ch.cloud.upms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch.Num;
import com.ch.Status;
import com.ch.cloud.upms.manage.IDepartmentManage;
import com.ch.cloud.upms.model.Department;
import com.ch.cloud.upms.model.Position;
import com.ch.cloud.upms.model.Tenant;
import com.ch.cloud.upms.service.IDepartmentService;
import com.ch.cloud.upms.service.IPositionService;
import com.ch.cloud.upms.service.ITenantService;
import com.ch.cloud.upms.utils.RequestUtils;
import com.ch.e.ExceptionUtils;
import com.ch.e.PubError;
import com.ch.pojo.VueRecord;
import com.ch.result.PageResult;
import com.ch.result.Result;
import com.ch.result.ResultUtils;
import com.ch.utils.AssertUtils;
import com.ch.utils.CommonUtils;
import com.ch.utils.VueRecordUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 部门表 前端控制器
 * </p>
 *
 * @author zhimin.ma
 * @since 2020-07-02
 */
@RestController
@Tag(name = "department-controller", description = "部门管理")
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    private IDepartmentService departmentService;
    @Autowired
    private IDepartmentManage departmentManage;
    @Autowired
    private IPositionService positionService;
    @Autowired
    private ITenantService tenantService;

    @GetMapping(value = {"/{num:[0-9]+}/{size:[0-9]+}"})
    @Operation(tags = "v1.1")
    public PageResult<Department> page(Department record,
                                       @PathVariable(value = "num") int pageNum,
                                       @PathVariable(value = "size") int pageSize) {
        Page<Department> pageInfo = departmentService.findTreePage(record, pageNum, pageSize);
        return PageResult.success(pageInfo.getTotal(), pageInfo.getRecords());
    }

    @PostMapping
    public Result<Boolean> add(@RequestBody Department record) {
        record.setCreateBy(RequestUtils.getHeaderUser());
        return ResultUtils.wrapFail(() -> {
            getAndCheck(record);
            return departmentService.save(record);
        });
    }

    @PutMapping({"/{id:[0-9]+}"})
    public Result<Boolean> edit(@PathVariable Long id, @RequestBody Department record) {
        return ResultUtils.wrapFail(() -> {
            getAndCheck(record);

            return departmentManage.update(record);
        });
    }

    @GetMapping({"/{id:[0-9]+}"})
    public Result<Department> detail(@PathVariable Long id) {
        return ResultUtils.wrapFail(() -> departmentManage.get(id));
    }

    private void getAndCheck(Department record) {
        if (CommonUtils.isEquals(record.getPid(), 0)) {
            return;
        }
        AssertUtils.isTrue(CommonUtils.isEquals(record.getPid(), record.getId()),PubError.NOT_ALLOWED, "id and pid is same");
        Department parent = departmentService.getById(record.getPid());
        AssertUtils.isNull(parent,PubError.NOT_EXISTS, record.getPid());
        record.setParentName(CommonUtils.isEquals(parent.getParentId(), Num.S0) ? parent.getName() : parent.getParentName() + "," + parent.getName());
    }

    @DeleteMapping("/{id:[0-9]+}")
    public Result<Boolean> delete(@PathVariable Long id) {
        return ResultUtils.wrapFail(() -> {
            Department dept = departmentManage.get(id);
            AssertUtils.isTrue(CommonUtils.isEquals(dept.getPid(), 0), PubError.NOT_ALLOWED, "top department not allow delete");
            return departmentManage.remove(id);
        });
    }

    @GetMapping("/{id:[0-9]+}/positions")
    public Result<Position> positions(@PathVariable Long id) {
        return ResultUtils.wrapList(() -> positionService.findByDepartmentId(id));
    }

    @PostMapping("/{id:[0-9]+}/positions")
    public Result<Integer> positions(@PathVariable Long id, @RequestBody List<Long> positionIds) {
        return ResultUtils.wrapFail(() -> positionService.saveDepartmentPositions(id, positionIds));
    }

    @GetMapping({"/tree/{pid:[0-9]+}"})
    public Result<VueRecord> tree(@PathVariable String pid) {
        return ResultUtils.wrapList(() -> {
            List<Department> records = departmentService.findTreeByPid(pid, true, null);
            return VueRecordUtils.covertIdTree(records);
        });
    }

    @GetMapping({"{pid:[0-9]+}/tree/{deptType:[0-9]+}"})
    public Result<VueRecord> treeType(@PathVariable String pid, @PathVariable Integer deptType) {
        return ResultUtils.wrapList(() -> {
            List<Department> records = departmentService.findTreeByPid(pid, true, deptType);
            return VueRecordUtils.covertIdTree(records);
        });
    }

    @GetMapping("/{id:[0-9]+}/positions/{name}")
    public Result<Position> findPositions(@PathVariable Long id, @PathVariable(required = false) String name) {
        return ResultUtils.wrapList(() -> positionService.findByDepartmentIdAndNameAndStatus(id, name, Status.ENABLED));
    }

    @GetMapping("/{id:[0-9]+}/tenants")
    public Result<Tenant> findTenants(@PathVariable Long id, @RequestParam(value = "s", required = false) String name) {
        return ResultUtils.wrapList(() -> {
            Department department = departmentManage.get(id);
            AssertUtils.isNull(department,PubError.NOT_EXISTS, id);
            String prefixDeptId = CommonUtils.isEquals(department.getParentId(), Num.S0) ? department.getId() + "" : department.getParentId() + "," + department.getId();
            return tenantService.findByDepartmentIdAndNameAndStatus(prefixDeptId, name, Status.ENABLED);
        });
    }

}

