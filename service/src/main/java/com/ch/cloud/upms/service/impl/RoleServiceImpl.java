package com.ch.cloud.upms.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch.Constants;
import com.ch.cloud.upms.mapper.RoleMapper;
import com.ch.cloud.upms.model.Role;
import com.ch.cloud.upms.service.IRoleService;
import com.ch.utils.CommonUtils;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 后台角色表 服务实现类
 * </p>
 *
 * @author zhimin.ma
 * @since 2020-03-25
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Override
    public Role getCurrent(String username) {
        return getBaseMapper().getCurrent(username);
    }

    @Override
    public Role findByCode(String code) {
        if (CommonUtils.isEmpty(code)) return null;
        return super.query().eq("code", code).one();
    }

    @Override
    public List<Role> findRoleForUser(Long userId) {
        List<Role> records = this.findEnabled();
        List<Role> roles = this.findByUserId(userId);
        if (roles != null && !roles.isEmpty()) {
            List<Long> roleIds = roles.stream().map(Role::getId).collect(Collectors.toList());
            records.forEach(r -> {
                if (roleIds.contains(r.getId())) {
                    r.setStatus(Constants.DISABLED);
                }
            });
        }
        return roles;
    }

    @Override
    public List<Role> findByUserId(Long userId) {
        return getBaseMapper().findByUserId(userId);
    }

    @Override
    public List<Role> findEnabled() {
        return super.query().eq("status", "1").ne("type", "0").orderByAsc("code", "id").list();
    }

    @Override
    public Page<Role> page(Role record, int pageNum, int pageSize) {
        return super.query()
                .like(CommonUtils.isNotEmpty(record.getCode()), "code", record.getCode())
                .like(CommonUtils.isNotEmpty(record.getName()), "name", record.getName())
                .eq(CommonUtils.isNotEmpty(record.getStatus()), "status", record.getStatus())
                .in("type", Lists.newArrayList("1", "2"))
                .orderByDesc("type", "id").page(new Page<>(pageNum, pageSize));
    }
}
