package com.ch.cloud.upms.controller;

import com.ch.Constants;
import com.ch.StatusS;
import com.ch.cloud.upms.model.StRole;
import com.ch.cloud.upms.model.StUser;
import com.ch.cloud.upms.pojo.UserInfo;
import com.ch.cloud.upms.service.IRoleService;
import com.ch.cloud.upms.service.IUserService;
import com.ch.e.PubError;
import com.ch.result.PageResult;
import com.ch.result.Result;
import com.ch.result.ResultUtils;
import com.ch.utils.CharUtils;
import com.ch.utils.CommonUtils;
import com.ch.utils.DateUtils;
import com.ch.utils.EncryptUtils;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
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
    public Result<String> initPwd(@PathVariable Long id) {
        StUser user = userService.find(id);
        if (CommonUtils.isNotEmpty(user)) {
            String pwd = EncryptUtils.generate(8);
            user.setPassword(pwd);
            int c = userService.updatePassword(user);
            if (c > 0) {
                return Result.success(pwd);
            } else {
                return Result.error(PubError.UPDATE, "更新密码失败!");
            }
        } else {
            return Result.error(PubError.NOT_EXISTS);
        }
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
