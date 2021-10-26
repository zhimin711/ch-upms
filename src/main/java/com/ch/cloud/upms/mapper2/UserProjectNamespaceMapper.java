package com.ch.cloud.upms.mapper2;

import com.ch.cloud.upms.model.Project;
import com.ch.cloud.upms.pojo.NamespaceDto;
import com.ch.cloud.upms.pojo.UserProjectNamespaceDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * decs:
 *
 * @author 01370603
 * @date 221/11/6
 */
@Mapper
public interface UserProjectNamespaceMapper {

    @Select("select USER_ID,PROJECT_ID,NAMESPACE_ID from rt_user_namespace where USER_ID=#{userId}")
    List<UserProjectNamespaceDto> findProjectNamespaceIdsByUserId(String userId);

    @Select("SELECT t1.* from bt_namespace t1" +
            " INNER JOIN rt_user_namespace t2 ON t1.id  = t2.NAMESPACE_ID" +
            " WHERE t2.project_id =#{projectId} and t2.USER_ID=#{userId}")
    List<NamespaceDto> findNamespacesByUserIdAndProjectId(String userId, Long projectId);

    @Insert("INSERT INTO rt_user_namespace (PROJECT_ID,USER_ID,NAMESPACE_ID) VALUES (#{projectId},#{userId},#{namespaceId})")
    int insert(@Param("projectId") Long projectId, @Param("userId") String userId, @Param("namespaceId") String namespaceId);

    @Delete("DELETE FROM rt_user_namespace where PROJECT_ID=#{projectId} and USER_ID=#{userId} and NAMESPACE_ID=#{namespaceId}")
    int delete(Long projectId, String userId, String namespaceId);

    @Delete("DELETE FROM rt_user_namespace where PROJECT_ID = #{projectId}")
    int deleteByProjectId(Long projectId);

    @Select("SELECT count(1) from rt_user_namespace where PROJECT_ID=#{projectId} and USER_ID=#{userId} and NAMESPACE_ID=#{namespaceId}")
    int exists(@Param("projectId") Long projectId, @Param("userId") String userId, @Param("namespaceId") String namespaceId);

    @Select("select USER_ID,PROJECT_ID,NAMESPACE_ID from rt_user_namespace where NAMESPACE_ID=#{namespaceId} and PROJECT_ID=#{projectId}")
    List<UserProjectNamespaceDto> findUsersByNamespaceIdAndProjectId(Long namespaceId, Long projectId);
}
