package com.ch.cloud.upms.mapper2;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * decs:
 *
 * @author 01370603
 * @date 2019/11/6
 */
@Mapper
public interface ProjectUserMapper {

    @Select("select PROJECT_ID from rt_project_user where USER_ID=#{userId}")
    List<Long> findProjectIdsByUserId(String userId);

    //    @Select("select t1.CODE from rt_project_user t inner join bt_project_code t1 on t.PROJECT_ID = t1.ID where USER_ID=#{userId}")
    @Select("select CONCAT(IF (t1.PARENT_CODE IS NULL or t1.PARENT_CODE = '', '', CONCAT(t1.PARENT_CODE, ':')), t1.`CODE`) PROJECT_CODE" +
            " from rt_project_user t inner join bt_project t1 on t.PROJECT_ID = t1.ID where t.USER_ID=#{userId}")
    List<String> findProjectCodesByUserId(String userId);

    @Select("select USER_ID from rt_project_user where PROJECT_ID=#{projectId}")
    List<String> findUserIdsByProjectId(Long projectId);

    @Delete("delete from rt_project_user where PROJECT_ID = #{projectId}")
    int deleteUserIds(Long projectId);

    @Select("select CONCAT(IF(t1.PARENT_CODE IS NULL or t1.PARENT_CODE = '', '', CONCAT(t1.PARENT_CODE, ',')),CONCAT(IF (t1.PARENT_CODE IS NULL or t1.PARENT_CODE = '', '', CONCAT(t1.PARENT_CODE, ':')), t1.`CODE`)) PROJECT_CODE" +
            " from rt_project_user t inner join bt_project t1 on t.PROJECT_ID = t1.ID where t.USER_ID=#{userId}")
    List<String> findProjectCodesContainsParentCodeByUserId(String username);

    @Select("select count(1) from rt_project_user where PROJECT_ID=#{projectId} and USER_ID=#{userId}")
    int exists(@Param("projectId") Long projectId, @Param("userId") String userId);

//    int insertUserIds(@Param("projectId") Long projectId, @Param("userIds")  List<String> userIds);
}
