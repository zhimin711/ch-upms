package com.ch.cloud.upms.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch.cloud.upms.fclient.SsoClientService;
import com.ch.cloud.upms.model.Project;
import com.ch.cloud.upms.model.Role;
import com.ch.cloud.upms.model.User;
import com.ch.cloud.upms.pojo.NamespaceDto;
import com.ch.cloud.upms.pojo.UserInfo;
import com.ch.cloud.upms.service.IRoleService;
import com.ch.cloud.upms.service.IUserService;
import com.ch.cloud.upms.utils.RequestUtils;
import com.ch.e.ExceptionUtils;
import com.ch.e.PubError;
import com.ch.pojo.KeyValue;
import com.ch.pojo.VueRecord2;
import com.ch.result.PageResult;
import com.ch.result.Result;
import com.ch.result.ResultUtils;
import com.ch.utils.CharUtils;
import com.ch.utils.CommonUtils;
import com.ch.utils.DateUtils;
import com.ch.utils.EncryptUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/*
 *
 * 用户管理
 *
 * @author 01370603
 * @date 2018/12/22 22:35
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;

    @Autowired
    private SsoClientService ssoClientService;

    @GetMapping(value = {"{num:[0-9]+}/{size:[0-9]+}"})
    public PageResult<User> page(User record,
                                 @PathVariable(value = "num") int pageNum,
                                 @PathVariable(value = "size") int pageSize) {

        Page<User> page = userService.page(record, pageNum, pageSize);
        return PageResult.success(page.getTotal(), page.getRecords());
    }

    @PostMapping
    public Result<Boolean> add(@RequestBody User record) {

        User r = userService.findByUsername(record.getUsername());

        if (r != null) {
            return Result.error(PubError.EXISTS, "用户名已存在！");
        }
        record.setCreateBy(RequestUtils.getHeaderUser());
        return ResultUtils.wrapFail(() -> userService.save(record));
    }

    @GetMapping({"{id:[0-9]+}"})
    public Result<User> detail(@PathVariable Long id) {
        return ResultUtils.wrapFail(() -> {
            User record = userService.getById(id);
            record.setDutyList(userService.findDepartmentDuty(id));
            return record;
        });
    }

    @PutMapping({"{id:[0-9]+}"})
    public Result<Boolean> edit(@PathVariable Long id, @RequestBody User record) {
        record.setUpdateBy(RequestUtils.getHeaderUser());
        record.setUpdateAt(DateUtils.current());
        return ResultUtils.wrapFail(() -> userService.updateById(record));
    }

    //    @PostMapping({"delete"})
    public Result<Boolean> delete(Long id) {
        return ResultUtils.wrapFail(() -> userService.removeById(id));
    }

    @PostMapping("{id:[0-9]+}/initPwd")
    public Result<String> initPwd(@PathVariable Long id) {
        return ResultUtils.wrapFail(() -> {
            User user = userService.getById(id);
            if (user == null) {
                ExceptionUtils._throw(PubError.NOT_EXISTS);
            }
            String pwd = EncryptUtils.generate(8);
            Result<String> res = ssoClientService.encrypt(pwd);
            if (!res.isSuccess()) {
                ExceptionUtils._throw(PubError.UPDATE, "加密错误，请稍后重试!");
            }
            user.setPassword(res.get());
            user.setUpdateBy(RequestUtils.getHeaderUser());
            user.setUpdateAt(DateUtils.current());
            int c = userService.updatePassword(user);
            if (c <= 0) {
                ExceptionUtils._throw(PubError.UPDATE, "更新密码失败!");
            }
            return pwd;
        });
    }

    @PostMapping("changePwd")
    public Result<Integer> changePwd(@RequestBody KeyValue keyValue) {
        return ResultUtils.wrapFail(() -> {
            String username = RequestUtils.getHeaderUser();
            User user = userService.findByUsername(username);
            if (user == null) {
                ExceptionUtils._throw(PubError.NOT_EXISTS);
            }
            Result<Boolean> res = ssoClientService.matchEncrypt(keyValue.getKey(), user.getPassword());
            if (!res.isSuccess() || !res.get()) {
                ExceptionUtils._throw(PubError.USERNAME_OR_PASSWORD, "原密码错误，请输入正确密码!");
            }
            Result<String> res1 = ssoClientService.encrypt(keyValue.getValue());
            user.setPassword(res1.get());
            user.setUpdateBy(username);
            user.setUpdateAt(DateUtils.current());
            return userService.updatePassword(user);
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
        return ResultUtils.wrap(() -> userService.assignRole(id, roleIds));
    }

    @GetMapping("valid")
    public Result<UserInfo> findValid(@RequestParam(value = "name", required = false) String idOrUsernameOrRealName) {
        return ResultUtils.wrapList(() -> {
            List<User> users;
            if (CommonUtils.isEmpty(idOrUsernameOrRealName)) {
                users = userService.findAllValid();
            } else if (CommonUtils.isNumeric(idOrUsernameOrRealName)) {
                users = userService.findByLikeUserId(idOrUsernameOrRealName);
            } else if (CharUtils.containsChinese(idOrUsernameOrRealName)) {
                users = userService.findByLikeRealName(idOrUsernameOrRealName);
            } else {
                users = userService.findByLikeUsername(idOrUsernameOrRealName);
            }
            return users.stream().map(r -> {
                UserInfo info = new UserInfo();
                BeanUtils.copyProperties(r, info);
                return info;
            }).collect(Collectors.toList());
        });
    }
/*
    @GetMapping({"tenants"})
    public Result<TenantDto> findTenants() {
        return ResultUtils.wrapList(() -> {
            String username = RequestUtils.getHeaderUser();
            if (CommonUtils.isEmpty(username)) {
                return null;
            }
            List<Tenant> tenantList = userService.findTenantsByUsername(RequestUtils.getHeaderUser());
            return tenantList.stream().map(e -> {
                TenantDto record = new TenantDto();
                record.setId(e.getId());
                record.setName(e.getName());
                record.setDeptId(e.getDepartmentId());
                return record;
            }).collect(Collectors.toList());
        });
    }*/

    @GetMapping({"tenant/{tenantId:[0-9]+}/projects"})
    public Result<VueRecord2> findProjects(@PathVariable Long tenantId) {
        return ResultUtils.wrapList(() -> {
            List<Project> projectList = userService.findProjectsByUsernameAndTenantId(RequestUtils.getHeaderUser(), tenantId);
            return projectList.stream().map(e -> {
                VueRecord2 record = new VueRecord2();
                record.setValue(e.getId() + "");
                record.setLabel(e.getName());
                record.setKey(e.getCode() + "");
                return record;
            }).collect(Collectors.toList());
        });
    }

    @GetMapping({"project/{projectId:[0-9]+}/namespaces"})
    public Result<VueRecord2> findNamespaces(@PathVariable Long projectId) {
        return ResultUtils.wrapList(() -> {
            List<NamespaceDto> projectList = userService.findNamespacesByUsernameAndProjectId(RequestUtils.getHeaderUser(), projectId);
            return projectList.stream().map(e -> {
                VueRecord2 record = new VueRecord2();
                record.setValue(e.getId() + "");
                record.setLabel(e.getName());
                record.setKey(e.getUid());
                return record;
            }).collect(Collectors.toList());
        });
    }
}
