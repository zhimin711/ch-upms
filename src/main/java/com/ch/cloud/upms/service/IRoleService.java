package com.ch.cloud.upms.service;

import com.ch.cloud.upms.model.StRole;
import com.ch.mybatis.service.IService;

import java.util.List;

/**
 * @author 01370603
 * @date 2018/12/22 20:17
 */
public interface IRoleService extends IService<Long, StRole> {

    StRole findDefault(String username);

    StRole findByCode(String code);

    List<StRole> findRoleForUser(Long userId);

    List<StRole> findByUserId(Long userId);

    List<StRole> findEnabled();
}
