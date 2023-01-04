package com.ch.cloud.upms.mapper2;

import com.ch.cloud.upms.pojo.DepartmentDuty;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * desc:
 *
 * @author zhimin
 * @since 2022/2/17 8:55 AM
 */
@Mapper
public interface UserDepartmentPositionMapper {
    
    @Insert("INSERT INTO st_user_department_position VALUES (#{userId},#{departmentId},#{positionId},#{orgId})")
    int insertDepartmentPosition(@Param("userId") Long userId, @Param("departmentId") String departmentId,
            @Param("positionId") Long positionId, @Param("orgId") String orgId);
    
    @Delete("DELETE FROM st_user_department_position WHERE user_id=#{userId}")
    int deleteDepartmentPositionByUserId(Long userId);
    
    @Select("SELECT user_id, department_id as department, position_id as duty, org_id as orgId\n"
            + "    FROM st_user_department_position\n" + "    WHERE user_id=#{userId}")
    List<DepartmentDuty> findDepartmentPositionByUserId(Long userId);
    
    List<DepartmentDuty> findDepartmentPositionByUserIdLikeDepartmentId(Long userId, String departmentId);
    
    @Select("SELECT user_id, department_id as department, position_id as duty, org_id as orgId\n"
            + " position_id as duty FROM st_user_department_position\n"
            + "    WHERE department_id like concat(#{departmentId},'%')")
    List<DepartmentDuty> listByLikeDepartmentId(String departmentId);
    
    @Update("update st_user_department_position"
            + " set user_id=#{record.userId},department_id=#{record.department},position_id=#{record.duty},org_id=#{record.orgId}"
            + " where user_id=#{param.userId} and department_id=#{param.department} and position_id=#{param.duty}")
    int update(@Param("record") DepartmentDuty value, @Param("param") DepartmentDuty condition);
}
