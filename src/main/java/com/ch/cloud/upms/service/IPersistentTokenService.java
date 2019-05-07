package com.ch.cloud.upms.service;

import com.ch.cloud.upms.model.PersistentToken;
import com.ch.mybatis.service.IService;

/**
 * Token 持久化
 *
 * @author 01370603
 * @date 2018/12/21 20:01
 */
public interface IPersistentTokenService extends IService<String, PersistentToken> {
    PersistentToken findByToken(String token);
}
