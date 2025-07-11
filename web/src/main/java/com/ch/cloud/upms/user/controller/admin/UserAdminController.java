package com.ch.cloud.upms.user.controller.admin;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch.Separator;
import com.ch.cloud.sso.client.SsoPasswordClient;
import com.ch.cloud.upms.enums.DepartmentType;
import com.ch.cloud.upms.manage.IDepartmentManage;
import com.ch.cloud.upms.manage.IUserManage;
import com.ch.cloud.upms.user.model.Department;
import com.ch.cloud.upms.user.model.Role;
import com.ch.cloud.upms.user.model.User;
import com.ch.cloud.upms.user.pojo.DepartmentDuty;
import com.ch.cloud.upms.service.IDepartmentService;
import com.ch.cloud.upms.service.IPositionService;
import com.ch.cloud.upms.service.IRoleService;
import com.ch.cloud.upms.service.IUserService;
import com.ch.cloud.upms.utils.RequestUtils;
import com.ch.e.Assert;
import com.ch.e.ExUtils;
import com.ch.e.PubError;
import com.ch.result.PageResult;
import com.ch.result.Result;
import com.ch.result.ResultUtils;
import com.ch.utils.CommonUtils;
import com.ch.utils.DateUtils;
import com.ch.core.utils.StrUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 *
 * 用户管理
 *
 * @author 01370603
 * @since 2018/12/22 22:35
 */
@Slf4j
@RestController
@RequestMapping("/user")
@Tag(name = "user-admin-controller", description = "用户管理-管理端")
public class UserAdminController {
    
    @Autowired
    private IUserService userService;
    
    @Autowired
    private IUserManage userManage;
    
    @Autowired
    private IDepartmentManage departmentManage;
    
    @Autowired
    private IRoleService roleService;
    
    @Autowired
    private IDepartmentService departmentService;
    
    @Autowired
    private IPositionService positionService;
    
    @Autowired
    private SsoPasswordClient ssoPasswordClient;
    
    @GetMapping(value = {"{num:[0-9]+}/{size:[0-9]+}"})
    public PageResult<User> page(User record, @PathVariable(value = "num") int pageNum,
            @PathVariable(value = "size") int pageSize) {
        
        if (CommonUtils.isNumeric(record.getDepartment())) {
            Department dept = departmentManage.get(Long.valueOf(record.getDepartment()));
            if (dept != null) {
                String key = dept.getParentId();
                String key2 = StrUtil.linkStrIgnoreZero(Separator.COMMA_SIGN, dept.getParentId(),
                        dept.getId().toString());
                if (DepartmentType.isTeam(dept.getDeptType())) {
                    record.setDepartmentId(key);
                    record.setDepartment(key2);
                } else {
                    record.setDepartmentId(key2);
                    record.setDepartment(null);
                }
            }
        }
        Page<User> page = userService.page(record, pageNum, pageSize);
        
        if (!page.getRecords().isEmpty()) {
            page.getRecords().forEach(r -> {
                if (CommonUtils.isNotEmpty(r.getDepartmentId(), record.getDepartmentId()) && !r.getDepartmentId()
                        .startsWith(record.getDepartmentId())) {
                    String[] deptIds = r.getDepartment().split("\\|");
                    r.setDepartmentName("");
                    for (String deptId : deptIds) {
                        //                        userDepartmentPositionMapper.findDepartmentPositionByUserIdLikeDepartmentId(r.getId(), deptId);
                        Department dept = departmentManage.get(StrUtil.lastId(deptId));
                        String pn = StrUtil.linkStr(Separator.COMMA_SIGN, dept.getParentName(), dept.getName());
                        r.setDepartmentName(StrUtil.linkStr(Separator.VERTICAL_LINE, r.getDepartmentName(), pn));
                    }
                    r.setDepartment("1");
                } else {
                    r.setDepartment("0");
                }
                
            });
        }
        return PageResult.success(page.getTotal(), page.getRecords());
    }
    
    @PostMapping
    public Result<Boolean> add(@RequestBody User record) {
        
        User r = userService.findByUsername(record.getUsername());
        
        if (r != null) {
            return Result.error(PubError.EXISTS, "用户名已存在！");
        }
        record.setCreateBy(RequestUtils.getHeaderUser());
        return ResultUtils.wrapFail(() -> userManage.saveWithAll(record));
    }
    
    @GetMapping({"{id:[0-9]+}"})
    public Result<User> detail(@PathVariable Long id) {
        return ResultUtils.wrapFail(() -> {
            User record = userService.getById(id);
            
            List<DepartmentDuty> records = userService.findDepartmentDuty(id);
            records.forEach(e -> {
                List<String> names = departmentService.findNames(
                        StrUtil.parseIds(CommonUtils.isEmpty(e.getOrgId()) ? e.getDepartment() : e.getOrgId()));
                e.setDepartmentName(String.join(",", names));
                e.setDutyName(positionService.getById(e.getDuty()).getName());
            });
            record.setDutyList(records);
            return record;
        });
    }
    
    @PutMapping({"{id:[0-9]+}"})
    public Result<Boolean> edit(@PathVariable Long id, @RequestBody User record) {
        record.setUpdateBy(RequestUtils.getHeaderUser());
        //        record.setUpdateAt(DateUtils.current());
        return ResultUtils.wrapFail(() -> userManage.updateWithAll(record));
    }
    
    //    @PostMapping({"delete"})
    public Result<Boolean> delete(Long id) {
        return ResultUtils.wrapFail(() -> userService.removeById(id));
    }
    
    @PostMapping("{id:[0-9]+}/initPwd")
    public Result<String> initPwd(@PathVariable Long id) {
        return ResultUtils.wrapFail(() -> {
            User user = userService.getById(id);
            Assert.notNull(user, PubError.NOT_EXISTS);
            String pwd = RandomUtil.randomString(6);
            Result<String> res = ssoPasswordClient.encrypt(pwd);
            Assert.isTrue(res.isSuccess(), PubError.UPDATE, "加密错误，请稍后重试!");
            user.setPassword(res.get());
            user.setUpdateBy(RequestUtils.getHeaderUser());
            user.setUpdateAt(DateUtils.current());
            int c = userService.updatePassword(user);
            Assert.isTrue(c > 0, PubError.UPDATE, "更新密码失败!");
            return pwd;
        });
    }
    
    @GetMapping({"roles"})
    public Result<Role> findEnableRoles() {
        return ResultUtils.wrapList(() -> roleService.findEnabled());
    }
    
    @GetMapping({"{id:[0-9]+}/roles"})
    public Result<Role> findRoleForUser(@PathVariable Long id) {
        return ResultUtils.wrapList(() -> roleService.findByUserId(id));
    }
    
    @PostMapping({"{id:[0-9]+}/roles"})
    public Result<Integer> saveUserRole(@PathVariable Long id, @RequestBody List<Long> roleIds) {
        return ResultUtils.wrap(() -> userManage.assignRole(id, roleIds));
    }
    
}
