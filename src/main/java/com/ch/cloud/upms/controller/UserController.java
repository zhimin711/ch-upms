package com.ch.cloud.upms.controller;

import com.ch.Constants;
import com.ch.cloud.upms.fclient.SsoClientService;
import com.ch.cloud.upms.model.StRole;
import com.ch.cloud.upms.model.StUser;
import com.ch.cloud.upms.pojo.UserInfo;
import com.ch.cloud.upms.service.IRoleService;
import com.ch.cloud.upms.service.IUserService;
import com.ch.e.PubError;
import com.ch.result.PageResult;
import com.ch.result.Result;
import com.ch.result.ResultUtils;
import com.ch.utils.*;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户管理
 *
 * @author 01370603
 * @date 2018/12/22 22:35
 */

@RestController
@RequestMapping("user")
public class UserController {


    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;

    @Autowired
    private SsoClientService ssoClientService;

    @GetMapping(value = {"{num}/{size}"})
    public PageResult<StUser> page(StUser record,
                                   @PathVariable(value = "num") int pageNum,
                                   @PathVariable(value = "size") int pageSize) {
        PageInfo<StUser> pageInfo = userService.findPage(record, pageNum, pageSize);
        return PageResult.success(pageInfo.getTotal(), pageInfo.getList());
    }

    @PostMapping
    public Result<Integer> add(@RequestBody StUser record,
                               @RequestHeader(Constants.TOKEN_USER) String username) {
        StUser r = userService.findByUsername(record.getUsername());
        if (r != null) {
            return Result.error(PubError.EXISTS, "用户名已存在！");
        }
        record.setCreateBy(username);
        return ResultUtils.wrapFail(() -> userService.save(record));
    }

    @PutMapping({"{id}"})
    public Result<Integer> edit(@PathVariable Long id, @RequestBody StUser record,
                                @RequestHeader(Constants.TOKEN_USER) String username) {
        record.setUpdateBy(username);
        record.setUpdateAt(DateUtils.current());
        return ResultUtils.wrapFail(() -> userService.update(record));
    }

    //    @PostMapping({"delete"})
    public Result<Integer> delete(Long id) {
        return ResultUtils.wrapFail(() -> userService.delete(id));
    }

    @PostMapping("{id}/initPwd")
    public Result<String> initPwd(@PathVariable Long id,
                                  @RequestHeader(Constants.TOKEN_USER) String username) {
        return ResultUtils.wrapFail(() -> {
            StUser user = userService.find(id);
            if (user == null) {
                ExceptionUtils._throw(PubError.NOT_EXISTS);
            }
            String pwd = EncryptUtils.generate(8);
            Result<String> res = ssoClientService.encrypt(pwd);
            if (!res.isSuccess()) {
                ExceptionUtils._throw(PubError.UPDATE, "加密错误，请稍后重试!");
            }
            user.setPassword(res.get());
            user.setUpdateBy(username);
            user.setUpdateAt(DateUtils.current());
            int c = userService.updatePassword(user);
            if (c <= 0) {
                ExceptionUtils._throw(PubError.UPDATE, "更新密码失败!");
            }
            return pwd;
        });
    }

    @GetMapping({"roles"})
    public Result<StRole> findEnableRoles() {
        return ResultUtils.wrapList(() -> roleService.findEnabled());
    }

    @GetMapping({"{id}/roles"})
    public Result<StRole> findRoleForUser(@PathVariable Long id) {
        return ResultUtils.wrapList(() -> roleService.findByUserId(id));
    }

    @PostMapping({"{id}/roles"})
    public Result<Integer> saveUserRole(@PathVariable Long id, @RequestBody List<Long> roleIds) {
        return ResultUtils.wrap(() -> userService.assignRole(id, roleIds));
    }

    @GetMapping("{username}/info")
    public Result<StUser> findByUsername(@PathVariable String username) {
        return ResultUtils.wrapFail(() -> userService.findByUsername(username));
    }

    @GetMapping({"{username}/role"})
    public Result<StRole> findRolesByUsername(@PathVariable String username) {
        return ResultUtils.wrap(() -> roleService.getCurrent(username));
    }

    @GetMapping("valid")
    public Result<UserInfo> findValid(@RequestParam(value = "name", required = false) String idOrUsernameOrRealname) {
        return ResultUtils.wrapList(() -> {
            List<StUser> users;
            if (CommonUtils.isEmpty(idOrUsernameOrRealname)) {
                users = userService.findAllValid();
            } else if (CommonUtils.isNumeric(idOrUsernameOrRealname)) {
                users = userService.findByLikeUserId(idOrUsernameOrRealname);
            } else if (CharUtils.containsChinese(idOrUsernameOrRealname)) {
                users = userService.findByLikeRealname(idOrUsernameOrRealname);
            } else {
                users = userService.findByLikeUsername(idOrUsernameOrRealname);
            }
            return users.stream().map(r -> {
                UserInfo info = new UserInfo();
                BeanUtils.copyProperties(r, info);
                return info;
            }).collect(Collectors.toList());
        });
    }

}
