package com.ch.cloud.upms.service.impl;

import com.ch.Constants;
import com.ch.cloud.upms.mapper.StRoleMapper;
import com.ch.cloud.upms.model.StRole;
import com.ch.cloud.upms.service.IRoleService;
import com.ch.mybatis.service.BaseService;
import com.ch.mybatis.utils.ExampleUtils;
import com.ch.utils.CommonUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 01370603
 * @date 2018/12/22 20:17
 */
@Service
public class RoleServiceImpl extends BaseService<Long, StRole> implements IRoleService {

    @Autowired(required = false)
    private StRoleMapper stRoleMapper;

    @Override
    protected Mapper<StRole> getMapper() {
        return stRoleMapper;
    }

    @Override
    public StRole findDefault(String username) {
        return stRoleMapper.findDefault(username);
    }

    @Override
    public StRole findByCode(String code) {
        if (CommonUtils.isEmpty(code)) return null;
        StRole record = new StRole();
        record.setCode(code);
        return getMapper().selectOne(record);
    }

    @Override
    public List<StRole> findRoleForUser(Long userId) {
        List<StRole> records = this.findValid();
        List<StRole> roles = this.findByUserId(userId);
        if (roles != null && !roles.isEmpty()) {
            List<Long> roleIds = roles.stream().map(StRole::getId).collect(Collectors.toList());
            records.forEach(r -> {
                if (roleIds.contains(r.getId())) {
                    r.setStatus(Constants.DISABLED);
                }
            });
        }
        return records;
    }

    @Override
    public List<StRole> findByUserId(Long userId) {
        return stRoleMapper.findByUserId(userId);
    }

    private List<StRole> findValid() {
        Example e = getExample();
        e.createCriteria().andEqualTo("status", "1").andNotEqualTo("type", "0");
        e.orderBy("code").asc().orderBy("id").asc();
        return getMapper().selectByExample(e);
    }

    @Override
    public PageInfo<StRole> findPage(StRole record, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Example e = getExample();
        ExampleUtils.dynCond(e.createCriteria(), record);
        e.orderBy("type").asc()
                .orderBy("id").asc();
        List<StRole> records = getMapper().selectByExample(e);

        return new PageInfo<>(records);
    }

}
