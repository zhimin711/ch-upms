/*
package com.ch.cloud.upms.service.impl;

import com.ch.Constants;
import com.ch.NumS;
import com.ch.StatusS;
import com.ch.cloud.upms.mapper.StMenuMapper;
import com.ch.cloud.upms.model.StMenu;
import com.ch.cloud.upms.pojo.Menu;
import com.ch.cloud.upms.service.IMenuService;
import com.ch.mybatis.service.BaseService;
import com.ch.mybatis.utils.ExampleUtils;
import com.ch.t.ConditionType;
import com.ch.utils.CommonUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.stream.Collectors;

*/
/**
 * @author 01370603
 * @since 2018/12/22 20:18
 *//*

@Service
public class MenuServiceImpl extends BaseService<Long, StMenu> implements IMenuService {

    @Autowired(required = false)
    StMenuMapper menuMapper;

    @Override
    protected Mapper<StMenu> getMapper() {
        return menuMapper;
    }

    @Override
    public List<StMenu> findTopByStatus(String status) {
        Example example = new Example(StMenu.class);
        if (CommonUtils.isEmpty(status)) {
            example.createCriteria()
                    .andIsNull("pid");
        } else {
            example.createCriteria()
                    .andEqualTo("status", status)
                    .andIsNull("pid");
        }
        example.orderBy("sort").asc();
        example.orderBy("id").asc();
        return menuMapper.selectByExample(example);
    }

    //    @Override
    public List<StMenu> findChildrenByStatus(String pid, String status) {
        if (CommonUtils.isEmpty(pid)) {
            return Lists.newArrayList();
        }
        Example example = new Example(StMenu.class);
        if (CommonUtils.isNotEmpty(status)) {
            example.createCriteria()
                    .andEqualTo("status", status)
                    .andEqualTo("pid", pid);
        } else {
            example.createCriteria()
                    .andEqualTo("pid", pid);
        }
        example.orderBy("sort").asc();
        example.orderBy("id").asc();
        return menuMapper.selectByExample(example);
    }

    //    @Override
    public List<StMenu> findChildrenOfType(String pid, String type) {
        if (CommonUtils.isEmpty(pid)) {
            return Lists.newArrayList();
        }
        Example example = new Example(StMenu.class);
        List<String> typeList = Lists.newArrayList("1", "2");
        if (CommonUtils.isNotEmpty(type) && typeList.contains(type)) {
            example.createCriteria()
                    .andEqualTo("type", StatusS.ENABLED)
                    .andEqualTo("pid", pid);
        } else if (CommonUtils.isNotEmpty(type)) {
            example.createCriteria()
                    .andIn("type", typeList)
                    .andEqualTo("pid", pid);
        } else {
            example.createCriteria()
                    .andEqualTo("pid", pid);
        }
        example.orderBy("sort").asc();
        example.orderBy("id").asc();
        return menuMapper.selectByExample(example);
    }

    @Override
    public List<StMenu> findTopByType(String type) {
        Example example = new Example(StMenu.class);
        example.createCriteria()
                .andEqualTo("type", type)
                .andIsNull("pid");
        example.orderBy("sort").asc();
        example.orderBy("id").asc();
        return menuMapper.selectByExample(example);
    }

    @Override
    public List<Menu> findMenuTreeByRoleId(Long roleId) {
        List<StMenu> topList = menuMapper.findMenuByRoleIdAndPid(roleId, null);
        if (CommonUtils.isEmpty(topList)) {
            return Lists.newArrayList();
        }
        return topList.parallelStream().map(r -> {
            Menu menu = new Menu();
            BeanUtils.copyProperties(r, menu);
            menu.setIndex(r.getCode());
            menu.setTitle(r.getName());
            List<Menu> children = this.findChildrenVo(roleId, r.getId().toString());
            menu.setSubs(children);
            return menu;
        }).collect(Collectors.toList());
    }

    private List<Menu> findChildrenVo(Long roleId, String pid) {
        List<StMenu> children = menuMapper.findMenuByRoleIdAndPid(roleId, pid);
        if (CommonUtils.isEmpty(children)) return null;
        return children.parallelStream().map(r -> {
            Menu menu = new Menu();
            BeanUtils.copyProperties(r, menu);
            menu.setIndex(r.getCode());
            menu.setTitle(r.getName());
            String p = r.getId().toString();
            if (CommonUtils.isNotEmpty(r.getPid())) {
                p = r.getPid() + "," + p;
            }
            List<Menu> children2 = this.findChildrenVo(roleId, p);
            menu.setSubs(children2);
            return menu;
        }).collect(Collectors.toList());
    }

    @Override
    public StMenu findByCode(String code) {
        if (CommonUtils.isEmpty(code)) return null;
        StMenu record = new StMenu();
        record.setCode(code);
        return getMapper().selectOne(record);
    }

    @Override
    public PageInfo<StMenu> findTreePage(StMenu record, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Example example = getExample();
        Example.Criteria criteria = example.createCriteria();
        ExampleUtils.dynCond(criteria, record);
        record.pidCond = ConditionType.NULL;
        dynCondition(criteria, record);
        example.orderBy("sort").asc();
        example.orderBy("pid").asc();
        example.orderBy("id").asc();
        List<StMenu> records = getMapper().selectByExample(example);
        if (CommonUtils.isNotEmpty(records)) {
            records.forEach(r -> {
                String p = r.getId().toString();
                if (CommonUtils.isNotEmpty(r.getPid())) {
                    p = r.getPid() + "," + p;
                }
                r.setChildren(this.findChildren(p));
            });
        }
        return new PageInfo<>(records);
    }

    @Override
    public List<StMenu> findRouterByRoleId(Long roleId) {
        if (roleId == null) return Lists.newArrayList();
        return menuMapper.findRouterByRoleId(roleId);
    }

    @Override
    public List<StMenu> findPermissionByRoleId(Long roleId) {
        if (roleId == null) return Lists.newArrayList();
        return menuMapper.findPermissionByRoleId(roleId);
    }

    @Override
    public List<StMenu> findTreeByType(String type) {
        if (CommonUtils.isEmpty(type)) return Lists.newArrayList();
        List<StMenu> records = this.findTopByType(NumS._1);
        records.forEach(r -> {
            String p = r.getId().toString();
            if (CommonUtils.isNotEmpty(r.getPid())) {
                p = r.getPid() + "," + p;
            }
            r.setChildren(this.findChildrenOfType(p, type));
        });
        return records;
    }


    private List<StMenu> findChildren(String pid) {
        if (pid == null) return null;
        Example example = getExample();
        example.createCriteria().andEqualTo("pid", pid);
        example.orderBy("sort").orderBy("id");
        List<StMenu> children = getMapper().selectByExample(example);
        if (CommonUtils.isEmpty(children)) return null;
        children.forEach(r -> {
            String p = r.getId().toString();
            if (CommonUtils.isNotEmpty(r.getPid())) {
                p = r.getPid() + "," + p;
            }
            r.setChildren(this.findChildren(p));
        });
        return children;
    }

    private int dynCondition(Example.Criteria criteria, StMenu record) {
        int c = 0;
        if (record.pidCond == ConditionType.NULL) {
            criteria.andIsNull("pid");
            c++;
        } else if (CommonUtils.isNotEmpty(record.getPid())) {
            criteria.andEqualTo("pid", record.getId());
            c++;
        }
        return c;
    }

    @Override
    public int updateWithNull(StMenu record) {
        StMenu r = this.find(record.getId());
        if (r == null) return 0;
//        record.setType(r.getType());
        record.setCreateAt(r.getCreateAt());
        record.setCreateBy(r.getCreateBy());
        return super.updateWithNull(record);
    }

}
*/
