package com.ch.cloud.upms.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch.Num;
import com.ch.Separator;
import com.ch.Status;
import com.ch.cloud.upms.user.mapper.DepartmentMapper;
import com.ch.cloud.upms.user.model.Department;
import com.ch.cloud.upms.service.IDepartmentService;
import com.ch.utils.CommonUtils;
import com.ch.utils.StringUtilsV2;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @author zhimin.ma
 * @since 2020-07-02
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {
    
    @Override
    public List<Department> findTreeByPid(String pid, boolean containsParent, Integer deptType) {
        Department parent = null;
        if (!CommonUtils.isEquals("0", pid) && containsParent) {
            parent = super.getById(pid);
            if (parent == null) {
                return Lists.newArrayList();
            }
        }
        List<Department> list = super.query().eq("parent_id", pid).list();
        if (list.isEmpty()) {
            return list;
        }
        list.forEach(r -> {
            String pid2 = StringUtilsV2.linkStrIgnoreZero(Separator.COMMA_SIGN, r.getParentId(), r.getId().toString());
            List<Department> subList = super.query().likeRight("parent_id", pid2)
                    .le(deptType != null && deptType > 0, "date_type", deptType).list();
            if (subList.isEmpty()) {
                return;
            }
            Map<String, List<Department>> subMap = assembleTree(subList);
            r.setChildren(subMap.get(pid2));
            if (r.getChildren() != null) {
                r.getChildren().sort(Comparator.comparing(Department::getSort));
            }
        });
        list.sort(Comparator.comparing(Department::getSort));
        if (parent != null) {
            parent.setChildren(list);
            return Lists.newArrayList(parent);
        }
        return list;
    }
    
    @Override
    public Page<Department> findTreePage(Department record, int pageNum, int pageSize) {
        Status status = Status.forEnabled(record.getStatus());
        Page<Department> page = super.query().eq("pid", Num.S0).eq(status == Status.ENABLED, "status", status.getCode())
                .orderByAsc("sort", "parent_id", "id").page(new Page<>(pageNum, pageSize));
        if (page.getTotal() > 0) {
            page.getRecords().forEach(r -> r.setChildren(findChildrenByPid(
                    StringUtilsV2.linkStrIgnoreZero(Separator.COMMA_SIGN, r.getParentId(), r.getId().toString()))));
        }
        return page;
    }
    
    @Override
    public String findCascadeId(Long id) {
        Department leaf = super.getById(id);
        if (leaf == null) {
            return null;
        }
        return StringUtilsV2.linkStrIgnoreZero(Separator.COMMA_SIGN, leaf.getParentId(), leaf.getId().toString());
    }
    
    @Override
    public List<String> findNames(List<Long> ids) {
        List<Department> list = super.query().select("name").in("id", ids).orderByAsc("parent_id").list();
        if (CommonUtils.isEmpty(list)) {
            return Lists.newArrayList();
        }
        return list.stream().map(Department::getName).collect(Collectors.toList());
    }
    
    @Override
    public String findCascadeName(List<Long> ids) {
        List<String> list = findNames(ids);
        return String.join(Separator.OBLIQUE_LINE, list);
    }
    
    private List<Department> findChildrenByPid(String pid) {
        return findTreeByPid(pid, false, null);
    }
    
    private Map<String, List<Department>> assembleTree(List<Department> subList) {
        Map<String, List<Department>> subMap = subList.stream().collect(Collectors.groupingBy(Department::getParentId));
        subMap.forEach((k, v) -> v.forEach(r -> {
            r.setChildren(
                    subMap.get(StringUtilsV2.linkStr(Separator.COMMA_SIGN, r.getParentId(), r.getId().toString())));
            if (r.getChildren() != null) {
                r.getChildren().sort(Comparator.comparing(Department::getSort));
            }
        }));
        return subMap;
    }
}
