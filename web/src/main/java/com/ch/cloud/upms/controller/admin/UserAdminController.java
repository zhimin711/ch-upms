package com.ch.cloud.upms.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch.cloud.upms.fclient.SsoClientService;
import com.ch.cloud.upms.manage.IUserManage;
import com.ch.cloud.upms.model.Role;
import com.ch.cloud.upms.model.User;
import com.ch.cloud.upms.service.IRoleService;
import com.ch.cloud.upms.service.IUserService;
import com.ch.cloud.upms.utils.RequestUtils;
import com.ch.e.ExceptionUtils;
import com.ch.e.PubError;
import com.ch.result.PageResult;
import com.ch.result.Result;
import com.ch.result.ResultUtils;
import com.ch.utils.DateUtils;
import com.ch.utils.EncryptUtils;
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
public class UserAdminController {
    
    @Autowired
    private IUserService   userService;
    @Autowired
    private IUserManage userManage;
    @Autowired
    private IRoleService   roleService;

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
        return ResultUtils.wrapFail(() -> userService.saveWithAll(record));
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
//        record.setUpdateAt(DateUtils.current());
        return ResultUtils.wrapFail(() -> userService.updateWithAll(record));
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

}
