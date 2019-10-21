package com.ch.cloud.upms.service.impl;

import com.ch.NumS;
import com.ch.StatusS;
import com.ch.cloud.upms.mapper.StPermissionMapper;
import com.ch.cloud.upms.model.StPermission;
import com.ch.cloud.upms.service.IPermissionService;
import com.ch.mybatis.service.BaseService;
import com.ch.mybatis.utils.ExampleUtils;
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
        List<StPermission> records = findTopCategory();
        if (CommonUtils.isNotEmpty(records)) {
            records.forEach(r -> r.setChildren(findChildrenByPidAndStatusAndType(r.getId().toString(), null, null)));
        }
        return new PageInfo<>(records);
    }


    public List<StPermission> findTopCategory() {
        Example example = Example.builder(StPermission.class).andWhere(Sqls.custom().andEqualTo("type", NumS._1).andEqualTo("parentId", NumS._0)).orderByAsc("sort", "parentId", "id").build();
        return getMapper().selectByExample(example);
    }

    @Override
    public List<StPermission> findTreeByType(String type) {
        List<StPermission> records = findTopCategory();
        if (records.isEmpty()) {
            return Lists.newArrayList();
        }
        records.forEach(r -> r.setChildren(findChildrenByPidAndStatusAndType(r.getId().toString(), null, type)));
        return records;
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
        }
        example.orderBy("sort").asc();
        example.orderBy("id").asc();
        List<StPermission> children = getMapper().selectByExample(example);
        if (CommonUtils.isEmpty(children)) return null;
        children.forEach(r -> {
            String pid1 = StringExtUtils.linkStr(",", "0".equals(r.getParentId()) ? "" : r.getParentId(), r.getId().toString());
            r.setChildren(findChildrenByPidAndStatusAndType(r.getId().toString(), status, type));
        });
        return children;
    }

    @Override
    protected Mapper<StPermission> getMapper() {
        return stPermissionMapper;
    }
}
