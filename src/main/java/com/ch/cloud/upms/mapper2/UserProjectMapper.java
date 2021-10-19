package com.ch.cloud.upms.mapper2;

import com.ch.cloud.upms.model.Project;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * decs:
 *
 * @author 01370603
 * @date 2019/11/6
 */
@Mapper
public interface UserProjectMapper {

    @Select("select PROJECT_ID from rt_user_project where USER_ID=#{userId}")
    List<Long> findProjectIdsByUserId(String userId);

    @Select("SELECT t1.* from bt_project t1" +
            " INNER JOIN rt_user_project t2 ON t1.id  = t2.PROJECT_ID" +
            " WHERE t1.tenant_id =#{tenantId} and t2.USER_ID=#{userId}")
    List<Project> findProjectsByUserIdAndTenantId(String userId, Long tenantId);

    @Select("select USER_ID from rt_user_project where PROJECT_ID=#{projectId}")
    List<String> findUserIdsByProjectId(Long projectId);

    @Insert("INSERT INTO rt_user_project (PROJECT_ID,USER_ID) VALUES (#{projectId},#{userId})")
    int insert(@Param("projectId") Long projectId, @Param("userId") String userId);

    @Insert("INSERT INTO rt_user_project (PROJECT_ID,USER_ID,ROLE) VALUES (#{projectId},#{userId},#{role})")
    int insert(@Param("projectId") Long projectId, @Param("userId") String userId, @Param("role") String role);

    @Delete("DELETE FROM rt_user_project where PROJECT_ID=#{projectId} and USER_ID=#{userId}")
    int delete(Long projectId, String userId);

    @Delete("DELETE FROM rt_user_project where PROJECT_ID = #{projectId}")
    int deleteByProjectId(Long projectId);

    @Select("SELECT count(1) from rt_user_project where PROJECT_ID=#{projectId} and USER_ID=#{userId}")
    int exists(@Param("projectId") Long projectId, @Param("userId") String userId);

}
