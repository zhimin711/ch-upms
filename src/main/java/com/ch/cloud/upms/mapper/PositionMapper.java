package com.ch.cloud.upms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ch.cloud.upms.model.Position;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 职位信息表 Mapper 接口
 * </p>
 *
 * @author zhimin.ma
 * @since 2020-07-04
 */
public interface PositionMapper extends BaseMapper<Position> {

    List<Position> findByDepartmentId(Long departmentId);

    int deleteDepartmentPositions(Long departmentId);

    int insertDepartmentPositions(@Param("departmentId") Long departmentId, @Param("positionIds") List<Long> positionIds);
}
