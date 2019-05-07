package com.ch.cloud.upms.service;

import com.ch.cloud.upms.model.StUser;
import com.ch.mybatis.service.IService;

import java.util.List;

/**
 * @author 01370603
 * @date 2018/12/21 18:00
 */
public interface IUserService extends IService<Long, StUser> {

    StUser findByUsername(String username);

    int updatePassword(StUser user);

    int assignRole(Long id, List<Long> roleIds);
}
