package com.ch.cloud.upms.user.mapper2;

import com.ch.cloud.upms.dto.ProjectRoleDto;
import com.ch.cloud.upms.dto.ProjectUserRoleDTO;
import com.ch.cloud.upms.user.model.Project;
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

    @Select("select t2.ID, GROUP_CONCAT(t1.ROLE) as ROLE from rt_user_project t1"
            + " inner join bt_project t2 on t1.PROJECT_ID = t2.ID"
            + " where t1.USER_ID=#{userId} group by t1.PROJECT_ID order by t2.tenant_id, t2.sort")
    List<ProjectRoleDto> findProjectRoleByUserId(String userId);

    @Select("SELECT distinct t1.* from bt_project t1" +
            " INNER JOIN rt_user_project t2 ON t1.id  = t2.PROJECT_ID" +
            " WHERE t1.tenant_id =#{tenantId} and t2.USER_ID=#{userId}" +
            " ORDER by t1.TENANT_ID, t1.SORT ,t1.SORT ASC")
    List<Project> findProjectsByUserIdAndTenantId(String userId, Long tenantId);

    @Select("select t1.PROJECT_ID,t1.USER_ID,t1.ROLE,t2.real_name from rt_user_project t1"
            + " inner join st_user t2 on t2.username = t1.user_id"
            + " where t1.PROJECT_ID=#{projectId}")
    List<ProjectUserRoleDTO> findUsersByProjectId(Long projectId);

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

    @Select("select t1.PROJECT_ID as ID, t1.ROLE, t2.CODE, t2.NAME, t2.manager, t2.tenant_name FROM rt_user_project t1" +
            " inner join bt_project t2 on t1.project_id = t2.id" +
            " where PROJECT_ID=#{projectId} and USER_ID=#{username}")
    List<ProjectRoleDto> listByUsernameAndProjectId(String username, Long projectId);

    @Select("select count(1) FROM rt_user_project where USER_ID=#{username} and PROJECT_ID=#{projectId} and ROLE=#{role}")
    int countProjectRole(String username, Long projectId, String role);

    @Select("<script>select ROLE FROM rt_user_project " +
            "where USER_ID=#{username} " +
            "and PROJECT_ID=#{projectId} " +
            "<if test='roles !=null and roles !=\"\"'>and FIND_IN_SET(ROLE,#{roles})</if>" +
            "</script>")
    List<String> listProjectRole(String username, Long projectId, String roles);
}
