package com.ch.cloud.upms.mapstrut;

import com.ch.cloud.upms.dto.TenantDto;
import com.ch.cloud.upms.user.model.Tenant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * desc: Tenant实体与TenantDto之间的转换器
 *
 * @author zhimin
 * @since 2024/1/1
 */
@Mapper
public interface MapperTenant {

    MapperTenant INSTANCE = Mappers.getMapper(MapperTenant.class);

    /**
     * Tenant实体转换为TenantDto
     * @param entity Tenant实体
     * @return TenantDto
     */
    @Mapping(source = "departmentId", target = "deptId")
    TenantDto toClientDto(Tenant entity);
} 