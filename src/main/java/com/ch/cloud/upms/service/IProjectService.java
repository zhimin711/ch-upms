package com.ch.cloud.upms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ch.cloud.upms.model.Namespace;
import com.ch.cloud.upms.model.Project;
import com.ch.pojo.VueRecord2;

import java.util.List;

/**
 * <p>
 * 业务-项目表 服务类
 * </p>
 *
 * @author zhimin.ma
 * @since 2021-09-23
 */
public interface IProjectService extends IService<Project> {

    Page<Project> page(Project record, int pageNum, int pageSize);

    List<Namespace> findNamespaces(Long id);

    Integer assignNamespaces(Long id, List<Long> namespaceIds);
}
