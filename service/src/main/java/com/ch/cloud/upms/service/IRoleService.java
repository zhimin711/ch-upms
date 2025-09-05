package com.ch.cloud.upms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch.cloud.upms.user.model.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 后台角色表 服务类
 * </p>
 *
 * @author zhimin.ma
 * @since 2020-03-25
 */
public interface IRoleService extends IService<Role> {

    Role getCurrent(String username);

    Role findByCode(String code);

    List<Role> findRoleForUser(Long userId);

    List<Role> findByUserId(Long userId);

    List<Role> findEnabled();

    Page<Role> page(Role record, int pageNum, int pageSize);
}
