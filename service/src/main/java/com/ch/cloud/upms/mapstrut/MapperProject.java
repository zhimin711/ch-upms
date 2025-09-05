package com.ch.cloud.upms.mapstrut;

import com.ch.cloud.upms.dto.ProjectDto;
import com.ch.cloud.upms.user.model.Project;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * desc:
 *
 * @author zhimin
 * @since 2022/5/11 22:25
 */
@Mapper
public interface MapperProject {

    MapperProject INSTANCE = Mappers.getMapper(MapperProject.class);

    ProjectDto toClientDto(Project entity);
}
