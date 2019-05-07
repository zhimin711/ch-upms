package com.ch.cloud.upms.service;

import com.ch.cloud.upms.model.StMenu;
import com.ch.cloud.upms.pojo.Menu;
import com.ch.mybatis.service.IService;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author 01370603
 * @date 2018/12/22 20:17
 */
public interface IMenuService extends IService<Long, StMenu> {

    List<StMenu> findTopByStatus(String status);

    List<StMenu> findTopByType(String type);

    StMenu findByCode(String code);

    PageInfo<StMenu> findTreePage(StMenu record, int pageNum, int pageSize);

    List<StMenu> findRouterByRoleId(Long roleId);

    List<StMenu> findPermissionByRoleId(Long roleId);

    List<StMenu> findTreeByType(String type);

    List<Menu> findMenuTreeByRoleId(Long roleId);
}
