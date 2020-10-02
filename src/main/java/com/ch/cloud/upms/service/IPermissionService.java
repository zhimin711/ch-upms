package com.ch.cloud.upms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch.cloud.upms.model.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 后台权限表 服务类
 * </p>
 *
 * @author zhimin.ma
 * @since 2020-03-26
 */
public interface IPermissionService extends IService<Permission> {

    Permission findByCode(String code);

    Page<Permission> findTreePage(Permission record, int pageNum, int pageSize);

    Page<Permission> findTreePage2(Permission record, int pageNum, int pageSize);

    List<Permission> findTreeByType(String type);

    List<Permission> findByTypeAndRoleId(List<String> types, Long roleId);

    List<Permission> find(Permission record);

    int updateRolePermissions(Long roleId, List<Long> permissionIds);

    List<Permission> findByPid(String pid);

    String findNameByParseLastId(String parentId);

    List<Permission> match(String urlPrefix, String method);

    int updateWithNull(Permission record);

    boolean delete(Long id);
}
