package com.ch.cloud.upms.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch.StatusS;
import com.ch.cloud.upms.mapper.UserMapper;
import com.ch.cloud.upms.model.Role;
import com.ch.cloud.upms.model.User;
import com.ch.cloud.upms.service.IRoleService;
import com.ch.cloud.upms.service.IUserService;
import com.ch.utils.CommonUtils;
import com.ch.utils.DateUtils;
import com.ch.utils.EncryptUtils;
import com.google.common.collect.Lists;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * <p>
 * 后台用户表 服务实现类
 * </p>
 *
 * @author zhimin.ma
 * @since 2020-03-25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private IRoleService roleService;

    @Override
    public User findByUsername(String username) {
        if (CommonUtils.isEmpty(username)) return null;
        User record = new User();
        record.setUsername(username);
        return super.getOne(Wrappers.query(record));
    }

    @Override
    public int updatePassword(User record) {
        if (record != null && CommonUtils.isNotEmpty(record.getId(), record.getPassword())) {
            User r = new User();
            r.setId(record.getId());
            r.setPassword(record.getPassword());
            r.setUpdateBy(record.getUpdateBy());
            r.setUpdateAt(DateUtils.current());
            boolean isUpdated = super.updateById(r);
            if (isUpdated) return 1;
        }
        return 0;
    }

    @Override
    public int assignRole(Long id, List<Long> roleIds) {
        List<Role> roleList = roleService.findByUserId(id);

        List<Long> uRoleIds = roleList.stream().filter(r -> !CommonUtils.isEquals(r.getType(), StatusS.DISABLED)).map(Role::getId).collect(Collectors.toList());
        List<Long> uRoleIds2 = roleList.stream().filter(r -> CommonUtils.isEquals(r.getType(), StatusS.DISABLED)).map(Role::getId).collect(Collectors.toList());

        AtomicInteger c = new AtomicInteger();
        if (!roleIds.isEmpty()) {
            roleIds.stream().filter(r -> !uRoleIds.contains(r) && !uRoleIds2.contains(r)).forEach(r -> c.getAndAdd(getBaseMapper().insertAssignRole(id, r)));
            uRoleIds.stream().filter(r -> !roleIds.contains(r) && !uRoleIds2.contains(r)).forEach(r -> c.getAndAdd(getBaseMapper().deleteAssignRole(id, r)));
        } else if (!uRoleIds.isEmpty()) {
            uRoleIds.forEach(r -> c.getAndAdd(getBaseMapper().deleteAssignRole(id, r)));
        }
        return c.get();
    }

    @Override
    public List<User> findByLikeUserId(String userId) {
        if(CommonUtils.isEmpty(userId)) return Lists.newArrayList();
        return super.query().like("user_id", userId).eq("status", StatusS.ENABLED).list();
    }

    @Override
    public List<User> findByLikeRealName(String realName) {
        return super.query().like("real_name", realName).eq("status", StatusS.ENABLED).list();
    }

    @Override
    public List<User> findByLikeUsername(String username) {
        return super.query().likeRight("username", username).eq("status", StatusS.ENABLED).list();
    }

    @Override
    public List<User> findAllValid() {
        return super.query().eq("status", StatusS.ENABLED).list();
    }

    @Override
    public boolean save(User record) {
        if (record != null) {
            if (CommonUtils.isEmpty(record.getPassword())) {
                record.setPassword(EncryptUtils.generate());
            }
            record.setUserId(RandomStringUtils.randomNumeric(10));
        }
        return super.save(record);
    }

    @Override
    public boolean updateById(User record) {
        record.setUserId(null);
        record.setUsername(null);
        //不更新密码
        record.setPassword(null);

        return super.updateById(record);
    }

    @Override
    public Page<User> page(User record, int pageNum, int pageSize) {
        return super.query()
                .like(CommonUtils.isNotEmpty(record.getUserId()), "user_id", record.getUserId())
                .like(CommonUtils.isNotEmpty(record.getUsername()), "username", record.getUsername())
                .like(CommonUtils.isNotEmpty(record.getRealName()), "real_name", record.getRealName())
                .like(CommonUtils.isNotEmpty(record.getEmail()), "email", record.getEmail())
                .eq(CommonUtils.isNotEmpty(record.getStatus()), "status", record.getStatus())
                .orderByAsc("user_id").page(new Page<>(pageNum, pageSize));
    }
}
