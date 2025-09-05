package com.ch.cloud.upms.user.mapper;

import com.ch.cloud.upms.user.model.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 后台角色表 Mapper 接口
 * </p>
 *
 * @author zhimin.ma
 * @since 2020-03-25
 */
public interface RoleMapper extends BaseMapper<Role> {

    Role getCurrent(String username);

    List<Role> findByUserId(Long userId);
}
