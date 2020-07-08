package com.ch.cloud.upms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ch.cloud.upms.model.User;
import com.ch.cloud.upms.pojo.DepartmentDuty;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    int insertDepartmentPosition(@Param("userId") Long userId, @Param("departmentId") String departmentId, @Param("positionId") Long positionId);

    int deleteDepartmentPositionByUserId(Long userId);

    List<DepartmentDuty> findDepartmentPositionByUserId(Long userId);

    Page<User> pageBy(IPage<User> page, @Param(Constants.ENTITY) User user);
}
