package com.ch.cloud.upms.manage.impl;

import com.ch.Status;
import com.ch.cloud.upms.manage.IDictManage;
import com.ch.cloud.upms.base.model.Dict;
import com.ch.cloud.upms.service.IDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * desc: DictManageImpl
 * </p>
 *
 * @author Zhimin.Ma
 * @since 2022/7/28
 */
@Component
@CacheConfig(cacheNames = "dict")
public class DictManageImpl implements IDictManage {

    @Autowired
    private IDictService dictService;

    @Override
    @Cacheable
    public Dict findByCode(String code) {
        Dict dict = dictService.findByCode(code);
        if (dict == null) return null;
        List<Dict> records = dictService.findByPidAndName(dict.getId(),null, Status.ENABLED);
        dict.setChildren(records);
        return dict;
    }

}
