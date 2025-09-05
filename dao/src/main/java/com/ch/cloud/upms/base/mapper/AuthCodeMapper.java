package com.ch.cloud.upms.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ch.cloud.upms.base.model.AuthCode;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthCodeMapper extends BaseMapper<AuthCode> {
    // 可扩展自定义SQL
} 