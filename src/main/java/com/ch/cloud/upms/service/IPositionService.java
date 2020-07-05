package com.ch.cloud.upms.service;

import com.ch.cloud.upms.model.Position;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 职位信息表 服务类
 * </p>
 *
 * @author zhimin.ma
 * @since 2020-07-04
 */
public interface IPositionService extends IService<Position> {

    List<Position> findByDepartmentId(Long departmentId);

    int saveDepartmentPositions(Long departmentId, List<Long> positionIds);
}
