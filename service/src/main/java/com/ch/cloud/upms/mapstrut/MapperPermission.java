package com.ch.cloud.upms.mapstrut;

import com.ch.cloud.upms.dto.PermissionDto;
import com.ch.cloud.upms.user.model.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * desc:
 *
 * @author zhimin
 * @since 2022/5/24 23:04
 */
@Mapper
public interface MapperPermission {
    MapperPermission INSTANCE = Mappers.getMapper(MapperPermission.class);

    PermissionDto toClientDto(Permission entity);
}
