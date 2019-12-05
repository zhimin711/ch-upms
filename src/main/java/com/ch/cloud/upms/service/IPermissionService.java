package com.ch.cloud.upms.service;

import com.ch.cloud.upms.model.StPermission;
import com.ch.mybatis.service.IService;
import com.github.pagehelper.PageInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 01370603
 * @date 2018/12/22 20:17
 */
public interface IPermissionService extends IService<Long, StPermission> {

    StPermission findByCode(String code);

    PageInfo<StPermission> findTreePage(StPermission record, int pageNum, int pageSize);

    List<StPermission> findTreeByType(String type);

    List<StPermission> findByTypeAndRoleId(List<String> types, Long roleId);

    int updateRolePermissions(Long roleId, List<Long> permissionIds);

    List<StPermission> findByPid(String pid);

    String findNameByParseLastId(String parentId);
}
