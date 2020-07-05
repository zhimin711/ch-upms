package com.ch.cloud.upms.service.impl;

import com.ch.cloud.upms.model.Position;
import com.ch.cloud.upms.mapper.PositionMapper;
import com.ch.cloud.upms.service.IPositionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 职位信息表 服务实现类
 * </p>
 *
 * @author zhimin.ma
 * @since 2020-07-04
 */
@Service
public class PositionServiceImpl extends ServiceImpl<PositionMapper, Position> implements IPositionService {

    @Override
    public List<Position> findByDepartmentId(Long departmentId) {
        List<Position> records = super.getBaseMapper().findByDepartmentId(departmentId);
        return records;
    }

    @Override
    public int saveDepartmentPositions(Long departmentId, List<Long> positionIds) {
        int c1 = getBaseMapper().deleteDepartmentPositions(departmentId);
        if (!positionIds.isEmpty()) {
            c1 += getBaseMapper().insertDepartmentPositions(departmentId, positionIds);
        }
        return c1;
    }
}
