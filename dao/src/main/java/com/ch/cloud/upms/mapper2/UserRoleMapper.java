package com.ch.cloud.upms.mapper2;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
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
    
    @Insert("INSERT INTO st_user_role (USER_ID,ROLE_ID) VALUES (#{userId},#{roleId})")
    int insert(@Param("userId") Long userId, @Param("roleId") Long roleId);
    
    @Delete("DELETE FROM st_user_role WHERE USER_ID=#{userId} AND ROLE_ID=#{roleId} and ROLE_ID != 1")
    int delete(@Param("userId") Long userId, @Param("roleId") Long roleId);
}
