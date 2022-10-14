package com.ch.cloud.upms.mapper2;

import com.ch.cloud.upms.dto.ProjectRoleDto;
import com.ch.cloud.upms.model.Project;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * decs:
 *
 * @author 01370603
 * @since 2019/11/6
 */
@Mapper
public interface UserProjectMapper {

    @Select("select distinct PROJECT_ID from rt_user_project where USER_ID=#{userId}")
    List<Long> findProjectIdsByUserId(String userId);
    
    @Select("select PROJECT_ID as ID, GROUP_CONCAT(ROLE) as ROLE from rt_user_project where USER_ID=#{userId} group by PROJECT_ID")
    List<ProjectRoleDto> findProjectRoleByUserId(String userId);

    @Select("SELECT distinct t1.* from bt_project t1" +
            " INNER JOIN rt_user_project t2 ON t1.id  = t2.PROJECT_ID" +
            " WHERE t1.tenant_id =#{tenantId} and t2.USER_ID=#{userId}" +
            " ORDER by t1.TENANT_ID, t1.SORT ,t1.SORT ASC")
    List<Project> findProjectsByUserIdAndTenantId(String userId, Long tenantId);

    @Select("select distinct USER_ID from rt_user_project where PROJECT_ID=#{projectId}")
    List<String> findUserIdsByProjectId(Long projectId);

    @Select("select distinct USER_ID from rt_user_project where PROJECT_ID=#{projectId} and ROLE=#{role}")
    List<String> findUserIdsByProjectIdAndRole(Long projectId, String role);

    @Insert("INSERT INTO rt_user_project (PROJECT_ID,USER_ID) VALUES (#{projectId},#{userId})")
    int insert(@Param("projectId") Long projectId, @Param("userId") String userId);

    @Insert("INSERT INTO rt_user_project (PROJECT_ID,USER_ID,ROLE) VALUES (#{projectId},#{userId},#{role})")
    int insertFull(@Param("projectId") Long projectId, @Param("userId") String userId, @Param("role") String role);

    @Delete("DELETE FROM rt_user_project where PROJECT_ID=#{projectId} and USER_ID=#{userId}")
    int delete(Long projectId, String userId);

    @Delete("DELETE FROM rt_user_project where PROJECT_ID=#{projectId} and USER_ID=#{userId} and role=#{role}")
    int deleteFull(Long projectId, String userId, String role);

    @Delete("DELETE FROM rt_user_project where PROJECT_ID = #{projectId}")
    int deleteByProjectId(Long projectId);

    @Select("SELECT count(1) from rt_user_project where PROJECT_ID=#{projectId} and USER_ID=#{userId}")
    int exists(@Param("projectId") Long projectId, @Param("userId") String userId);

    @Update("update rt_user_project set USER_ID=#{userId} where PROJECT_ID=#{projectId} and role=#{role}")
    int updateByProjectIdAndRole(Long projectId, String userId, String role);

    @Delete("DELETE FROM rt_user_project where PROJECT_ID = #{projectId} and role=#{role}")
    int deleteByProjectIdAndRole(Long projectId, String role);
    
    @Select("select PROJECT_ID, USER_ID, ROLE FROM rt_user_project where PROJECT_ID=#{projectId} and USER_ID=#{userId}")
    List<ProjectRoleDto> listByUsernameAndProjectId(String username, Long projectId);
}
