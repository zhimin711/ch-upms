package com.ch.cloud.upms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ch.cloud.upms.model.Namespace;
import com.ch.cloud.upms.model.Project;

import java.util.List;

/**
 * <p>
 * 业务-项目表 Mapper 接口
 * </p>
 *
 * @author zhimin.ma
 * @since 2021-09-23
 */
public interface ProjectMapper extends BaseMapper<Project> {

    List<Namespace> findNamespaces(Long id);

    int insertAssignNamespace(Long projectId, Long namespaceId);

    int deleteAssignNamespace(Long projectId, Long namespaceId);

    List<Project> findByNamespaceIdAndName(Long namespaceId, String name);
}
