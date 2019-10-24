package com.ch.cloud.upms.mapper;

import com.ch.cloud.upms.model.StPermission;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface StPermissionMapper extends Mapper<StPermission> {

    List<StPermission> findByTypeAndRoleId(@Param("types") List<String> types, @Param("roleId") Long roleId);

    int deleteRolePermissions(Long roleId);

    int insertRolePermissions(@Param("roleId") Long roleId, @Param("permissionIds") List<Long> permissionIds);
}