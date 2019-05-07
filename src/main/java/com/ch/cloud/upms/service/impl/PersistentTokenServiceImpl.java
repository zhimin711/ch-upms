package com.ch.cloud.upms.service.impl;

import com.ch.cloud.upms.mapper.PersistentTokenMapper;
import com.ch.cloud.upms.model.PersistentToken;
import com.ch.cloud.upms.service.IPersistentTokenService;
import com.ch.mybatis.service.BaseService;
import com.ch.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author 01370603
 * @date 2018/12/21 20:02
 */
@Service
public class PersistentTokenServiceImpl extends BaseService<String, PersistentToken> implements IPersistentTokenService {

    @Autowired(required = false)
    private PersistentTokenMapper persistentTokenMapper;

    @Override
    protected Mapper<PersistentToken> getMapper() {
        return persistentTokenMapper;
    }

    @Override
    public PersistentToken findByToken(String token) {
        if (CommonUtils.isEmpty(token)) return null;
        PersistentToken record = new PersistentToken();
        record.setToken(token);
        return getMapper().selectOne(record);
    }
}
