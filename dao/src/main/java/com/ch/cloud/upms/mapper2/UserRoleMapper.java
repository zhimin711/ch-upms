package com.ch.cloud.upms.mapper2;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * desc:
 * </p>
 *
 * @author zhimin.ma
 * @since 2021/11/2
 */
@Mapper
public interface UserRoleMapper {

    @Select("select count(1) from st_user_role where USER_ID = #{userId} and ROLE_ID = #{roleId}")
    Boolean exists(@Param("userId") Long userId, @Param("roleId") Long roleId);
}
