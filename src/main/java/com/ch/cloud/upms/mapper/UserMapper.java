package com.ch.cloud.upms.mapper;

import com.ch.cloud.upms.model.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 后台用户表 Mapper 接口
 * </p>
 *
 * @author zhimin.ma
 * @since 2020-03-25
 */
public interface UserMapper extends BaseMapper<User> {

    int insertAssignRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

    int deleteAssignRole(@Param("userId") Long userId, @Param("roleId") Long roleId);
}
