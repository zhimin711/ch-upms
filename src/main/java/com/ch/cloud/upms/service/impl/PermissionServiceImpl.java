package com.ch.cloud.upms.service.impl;

import com.ch.NumS;
import com.ch.Status;
import com.ch.cloud.upms.mapper.StPermissionMapper;
import com.ch.cloud.upms.model.StPermission;
import com.ch.cloud.upms.service.IPermissionService;
import com.ch.mybatis.service.BaseService;
import com.ch.utils.CommonUtils;
import com.ch.utils.StringExtUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PermissionServiceImpl extends BaseService<Long, StPermission> implements IPermissionService {

    @Resource
    private StPermissionMapper stPermissionMapper;

    @Override
    public StPermission findByCode(String code) {
        return null;
    }

    @Override
    public PageInfo<StPermission> findTreePage(StPermission record, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<StPermission> records = findTopCategory(Status.UNKNOWN);
        if (CommonUtils.isNotEmpty(records)) {
            records.forEach(r -> r.setChildren(findChildrenByPidAndStatusAndType(r.getId().toString(), null, "0")));
        }
        return new PageInfo<>(records);
    }


    public List<StPermission> findTopCategory(Status status) {
        Sqls sqls = Sqls.custom();
        sqls.andEqualTo("type", NumS._1).andEqualTo("parentId", NumS._0);
        if (status == Status.ENABLED) {
            sqls.andEqualTo("status", status.getCode());
        }
        Example example = Example.builder(StPermission.class)
                .andWhere(sqls)
                .orderByAsc("sort", "parentId", "id").build();
        return getMapper().selectByExample(example);
    }

    @Override
    public List<StPermission> findTreeByType(String type) {
        Status status = CommonUtils.isEquals(type, NumS._0) ? Status.ENABLED : Status.UNKNOWN;
        List<StPermission> records = findTopCategory(status);
        if (records.isEmpty()) {
            return Lists.newArrayList();
        }
        if (!CommonUtils.isEquals(type, NumS._1))
            records.forEach(r -> {
                List<StPermission> children = findChildrenByPidAndStatusAndType(r.getId().toString(), status != Status.UNKNOWN ? status.getCode() : null, type);
                r.setChildren(children);
            });
        return records;
    }

    @Override
    public List<StPermission> findByTypeAndRoleId(List<String> types, Long roleId) {
        return stPermissionMapper.findByTypeAndRoleId(types, roleId);
    }

    @Override
    public int updateRolePermissions(Long roleId, List<Long> permissionIds) {
        int c1 = stPermissionMapper.deleteRolePermissions(roleId);
        if (!permissionIds.isEmpty()) {
            c1 += stPermissionMapper.insertRolePermissions(roleId, permissionIds);
        }
        return c1;
    }

    public List<StPermission> findChildrenByPidAndStatusAndType(String pid, String status, String type) {
        if (CommonUtils.isEmpty(pid)) {
            return Lists.newArrayList();
        }
        Example example = new Example(StPermission.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("parentId", pid);
        if (CommonUtils.isNotEmpty(status)) {
            criteria.andEqualTo("status", status);
        }
        if (CommonUtils.isEquals(NumS._1, type)) {
            criteria.andEqualTo("type", type);
        } else if (CommonUtils.isEquals(NumS._2, type)) {
            criteria.andIn("type", Lists.newArrayList(NumS._1, NumS._2));
        } else if(!CommonUtils.isEquals(NumS._0, type)) {
            criteria.andNotEqualTo("type", NumS._5);
        }
        example.orderBy("sort").asc();
        example.orderBy("id").asc();
        List<StPermission> children = getMapper().selectByExample(example);
        if (CommonUtils.isEmpty(children)) return null;
        children.forEach(r -> {
            String pid1 = StringExtUtils.linkStr(",", "0".equals(r.getParentId()) ? "" : r.getParentId(), r.getId().toString());
            if (CommonUtils.isEquals(NumS._2, r.getType()) && CommonUtils.isEquals(type, NumS._0)) {
                r.setChildren(findChildrenByPidAndStatusAndType(pid1, status, type));
            }
        });
        return children;
    }

    @Override
    protected Mapper<StPermission> getMapper() {
        return stPermissionMapper;
    }
}
