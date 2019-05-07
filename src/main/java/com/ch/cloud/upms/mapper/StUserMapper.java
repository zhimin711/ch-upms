package com.ch.cloud.upms.mapper;

import com.ch.cloud.upms.model.StUser;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface StUserMapper extends Mapper<StUser> {


    int insertAssignRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

    int deleteAssignRole(@Param("userId") Long userId, @Param("roleId") Long roleId);
}