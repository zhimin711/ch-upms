package com.ch.cloud.upms.service.impl;

import com.ch.Status;
import com.ch.cloud.upms.model.Position;
import com.ch.cloud.upms.mapper.PositionMapper;
import com.ch.cloud.upms.service.IPositionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch.utils.CommonUtils;
import com.ch.utils.SQLUtils;
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
        return super.getBaseMapper().findByDepartmentId(departmentId);
    }

    @Override
    public int saveDepartmentPositions(Long departmentId, List<Long> positionIds) {
        int c1 = getBaseMapper().deleteDepartmentPositions(departmentId);
        if (!positionIds.isEmpty()) {
            c1 += getBaseMapper().insertDepartmentPositions(departmentId, positionIds);
        }
        return c1;
    }

    @Override
    public List<Position> findByDepartmentIdAndNameAndStatus(Long departmentId, String name, Status status) {
        String likeName = name;
        if (CommonUtils.isNotEmpty(name)) {
            likeName = SQLUtils.likeAny(name);
        }
        return getBaseMapper().findByDepartmentIdAndNameAndStatus(departmentId, likeName, status != Status.UNKNOWN ? status.getCode() : null);
    }
}
