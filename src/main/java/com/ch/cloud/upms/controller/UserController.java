package com.ch.cloud.upms.controller;

import com.ch.Status;
import com.ch.cloud.upms.model.StRole;
import com.ch.cloud.upms.model.StUser;
import com.ch.cloud.upms.service.IRoleService;
import com.ch.cloud.upms.service.IUserService;
import com.ch.e.CoreError;
import com.ch.result.PageResult;
import com.ch.result.Result;
import com.ch.result.ResultUtils;
import com.ch.utils.CommonUtils;
import com.ch.utils.EncryptUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping(value = {"{num}/{size}"})
    public PageResult<StUser> page(@RequestBody StUser record,
                                   @PathVariable(value = "num") int pageNum,
                                   @PathVariable(value = "size") int pageSize) {
        PageInfo<StUser> pageInfo = userService.findPage(record, pageNum, pageSize);
        return new PageResult<>(pageInfo.getList(), pageInfo.getTotal());
    }

    @PostMapping("save")
    public Result<Integer> add(@RequestBody StUser record) {
        StUser r = userService.findByUsername(record.getUsername());
        if (r != null) {
            return new Result<>(CoreError.EXISTS, "用户名已存在！");
        }
        return ResultUtils.wrapFail(() -> userService.save(record));
    }

    @PostMapping({"save/{id}"})
    public Result<Integer> edit(@PathVariable int id, @RequestBody StUser record) {
        return ResultUtils.wrapFail(() -> userService.update(record));
    }

    @PostMapping({"delete"})
    public Result<Integer> delete(Long id) {
        return ResultUtils.wrapFail(() -> userService.delete(id));
    }


    @GetMapping({"role/{id}"})
    public Result<StRole> findRoleForUser(@PathVariable Long id) {
        return ResultUtils.wrapList(() -> roleService.findRoleForUser(id));
    }

    @PostMapping({"role/{id}"})
    public Result<Integer> saveUserRole(@PathVariable Long id, @RequestBody List<Long> roleIds) {
        return ResultUtils.wrapFail(() -> userService.assignRole(id, roleIds));
    }

    @PostMapping("initPwd")
    public Result<String> initPwd(@RequestBody Long[] userIds) {
        Result<String> result = new Result<>(Status.FAILED);

        if (CommonUtils.isEmpty(userIds) || userIds.length > 1) {
            result.setStatus(Status.ERROR);
            result.setError(CoreError.ARGS, "不支持批量更新!");
            return result;
        }
        Long userId = userIds[0];
        StUser user = userService.find(userId);
        if (CommonUtils.isNotEmpty(user)) {
            String pwd = EncryptUtils.generate(8);
            user.setPassword(pwd);
            int c = userService.updatePassword(user);
            if (c > 0) {
                result.setStatus(Status.SUCCESS);
                result.put(pwd);
            } else {
                result.setError(CoreError.UPDATE, "更新密码失败!");
            }
        } else {
            result.setStatus(Status.ERROR);
            result.setError(CoreError.NOT_EXISTS, "用户不存在!");
        }
        return result;
    }

}
