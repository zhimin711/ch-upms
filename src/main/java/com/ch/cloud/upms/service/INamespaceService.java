package com.ch.cloud.upms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch.cloud.upms.model.Namespace;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 关系-命名空间表 服务类
 * </p>
 *
 * @author zhimin.ma
 * @since 2021-09-28
 */
public interface INamespaceService extends IService<Namespace> {

    Page<Namespace> page(Namespace record, int pageNum, int pageSize);
}
