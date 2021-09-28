package com.ch.cloud.upms.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch.cloud.upms.mapper.NamespaceMapper;
import com.ch.cloud.upms.model.Namespace;
import com.ch.cloud.upms.service.INamespaceService;
import com.ch.utils.CommonUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 关系-命名空间表 服务实现类
 * </p>
 *
 * @author zhimin.ma
 * @since 2021-09-28
 */
@Service
public class NamespaceServiceImpl extends ServiceImpl<NamespaceMapper, Namespace> implements INamespaceService {

    @Override
    public Page<Namespace> page(Namespace record, int pageNum, int pageSize) {
        return super.query()
                .likeRight(CommonUtils.isNotEmpty(record.getName()), "name", record.getName())
                .orderByAsc("id").page(new Page<>(pageNum, pageSize));
    }
}
