package com.ch.cloud.upms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ch.cloud.upms.model.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 后台权限表 Mapper 接口
 * </p>
 *
 * @author zhimin.ma
 * @since 2020-03-26
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    List<Permission> findByTypeAndRoleId(@Param("types") List<String> types, @Param("roleId") Long roleId);

    int deleteRolePermissions(Long roleId);

    int insertRolePermissions(@Param("roleId") Long roleId, @Param("permissionIds") List<Long> permissionIds);
}
