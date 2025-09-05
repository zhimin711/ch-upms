package com.ch.cloud.upms.mapstrut;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


import com.ch.cloud.upms.dto.UserDto;
import com.ch.cloud.upms.dto.UsernameDTO;
import com.ch.cloud.upms.user.model.User;

@Mapper
public interface MapperUser {

    MapperUser INSTANCE = Mappers.getMapper(MapperUser.class);

    UserDto toDto(User user);

    User toEntity(UserDto userDto);

    UsernameDTO toUsernameDTO(UserDto user);
}
