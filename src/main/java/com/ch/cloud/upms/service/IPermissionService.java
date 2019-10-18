package com.ch.cloud.upms.service;

import com.ch.cloud.upms.model.StPermission;
import com.ch.mybatis.service.IService;

/**
 * @author 01370603
 * @date 2018/12/22 20:17
 */
public interface IPermissionService extends IService<Long, StPermission> {

    StPermission findByCode(String code);
}
