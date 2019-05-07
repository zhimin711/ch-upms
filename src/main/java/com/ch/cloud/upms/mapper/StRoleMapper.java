package com.ch.cloud.upms.mapper;

import com.ch.cloud.upms.model.StRole;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface StRoleMapper extends Mapper<StRole> {
    StRole findDefault(String username);

    List<StRole> findByUserId(Long userId);
}