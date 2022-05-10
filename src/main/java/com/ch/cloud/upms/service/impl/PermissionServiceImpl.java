
package com.ch.cloud.upms.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch.Num;
import com.ch.Separator;
import com.ch.Status;
import com.ch.cloud.upms.mapper.PermissionMapper;
import com.ch.cloud.upms.model.Permission;
import com.ch.cloud.upms.service.IPermissionService;
import com.ch.e.PubError;
import com.ch.utils.BeanUtilsV2;
import com.ch.utils.CommonUtils;
import com.ch.e.ExceptionUtils;
import com.ch.utils.StringUtilsV2;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 后台权限表 服务实现类
 * </p>
 *
 * @author zhimin.ma
 * @since 2020-03-26
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

    @Override
    public Permission findByCode(String code) {
        if (CommonUtils.isEmpty(code)) return null;
        return super.query().eq("code", code).one();
    }

    @Override
    public Page<Permission> findTreePage(Permission record, int pageNum, int pageSize) {
        Status status = Status.forEnabled(record.getStatus());
        Page<Permission> page = super.query().eq("type", Num.S1).eq("parent_id", Num.S0)
                .eq(status == Status.ENABLED, "status", status.getCode())
                .orderByAsc("sort", "parent_id", "id").page(new Page<>(pageNum, pageSize));
        if (page.getTotal() > 0) {
            page.getRecords().forEach(r -> r.setChildren(findChildrenByPidAndStatusAndType(r.getId().toString(), null, "0")));
        }

        return page;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<Permission> pageTree(Permission record, int pageNum, int pageSize) {
        if (CommonUtils.isEmpty(record.getParentId())) {
            record.setParentId("0");
        }
        LambdaQueryChainWrapper<Permission> query = super.lambdaQuery()
                .eq(CommonUtils.isNotEmpty(record.getStatus()), Permission::getStatus, record.getStatus())
                .eq(CommonUtils.isNotEmpty(record.getParentId()), Permission::getParentId, record.getParentId())
                .eq(CommonUtils.isNotEmpty(record.getType()), Permission::getType, record.getType())
                .likeRight(CommonUtils.isNotEmpty(record.getUrl()), Permission::getUrl, record.getUrl())
                .eq(CommonUtils.isNotEmpty(record.getCode()), Permission::getCode, record.getCode());
        query.orderByAsc(Permission::getSort, Permission::getParentId, Permission::getId);
        Page<Permission> page = query.page(new Page<>(pageNum, pageSize));
        if (page.getTotal() > 0) {
            findChildrenTree(page.getRecords());
        }

        return page;
    }

    private void findChildrenTree(List<Permission> records) {
        if (records == null || records.isEmpty()) return;
        records.forEach(r -> {
            String pid2 = StringUtilsV2.linkStrIgnoreZero(Separator.COMMA_SIGN, r.getParentId(), r.getId().toString());
            List<Permission> subList = super.query().likeRight("parent_id", pid2).list();
            if (subList.isEmpty()) return;
            Map<String, List<Permission>> subMap = assembleTree(subList);
            r.setChildren(subMap.get(pid2));
            if (r.getChildren() != null) r.getChildren().sort(Comparator.comparing(Permission::getSort));
        });
    }

    @Override
    public List<Permission> findChildren(Permission permission) {
        if (!CommonUtils.isNotEmpty(permission.getParentId(), permission.getId())) {
            return Lists.newArrayList();
        }
        String pid2 = StringUtilsV2.linkStrIgnoreZero(Separator.COMMA_SIGN, permission.getParentId(), permission.getId().toString());
        return super.query().eq("parent_id", pid2)
                .like(CommonUtils.isNotEmpty(permission.getStatus()), "status", permission.getStatus())
                .like(CommonUtils.isNotEmpty(permission.getCode()), "code", permission.getCode())
                .like(CommonUtils.isNotEmpty(permission.getName()), "name", permission.getName())
                .list();
    }


    private List<Permission> findTopCategory(Status status) {
        return super.query().eq("type", Num.S1).eq("parent_id", Num.S0)
                .eq(status == Status.ENABLED, "status", status.getCode())
                .orderByAsc("sort", "parent_id", "id").list();
    }

    @Override
    public List<Permission> findTreeByType(String type) {
        Status status = Lists.newArrayList(Num.S0, Num.S4, Num.S9).contains(type) ? Status.ENABLED : Status.UNKNOWN;
        if (CommonUtils.isEquals(type, Num.S4)) {
            return findAuthPermissionTree(type);
        }
        List<Permission> records = findTopCategory(status);
        if (records.isEmpty()) {
            return Lists.newArrayList();
        }
        if (!CommonUtils.isEquals(type, Num.S1))
            records.forEach(r -> {
                List<Permission> children = findChildrenByPidAndStatusAndType(r.getId().toString(), status != Status.UNKNOWN ? status.getCode() : null, type);
                r.setChildren(children);
            });
        return records;
    }

    private List<Permission> findAuthPermissionTree(String type) {
        List<Permission> list = getBaseMapper().findByTypeAndRoleId(Lists.newArrayList(type), null);
        if (list.isEmpty()) return null;
        Set<Long> pidSet = Sets.newHashSet();
        list.forEach(e -> {
            List<Long> ids = StringUtilsV2.parseIds(e.getParentId());
            pidSet.addAll(ids);
        });
        List<Permission> list2 = super.query().in("id", pidSet).eq("status", Status.ENABLED.getCode()).list();
        list2.addAll(list);
        return list2;
    }

    @Override
    public List<Permission> findByTypeAndRoleId(List<String> types, Long roleId) {
        return getBaseMapper().findByTypeAndRoleId(types, roleId);
    }

    @Override
    public List<Permission> find(Permission record) {
        return getBaseMapper().selectList(Wrappers.query(record));
    }

    @Override
    public int updateRolePermissions(Long roleId, List<Long> permissionIds) {
        int c1 = getBaseMapper().deleteRolePermissions(roleId);
        if (!permissionIds.isEmpty()) {
            c1 += getBaseMapper().insertRolePermissions(roleId, permissionIds);
        }
        return c1;
    }

    @Override
    public List<Permission> findByPid(String pid) {
        return findChildrenByPidAndStatusAndType(pid, null, Num.S0);
    }

    @Override
    public String findNameByParseLastId(String parentId) {
        if (CommonUtils.isEmpty(parentId)) return null;
        String idStr = StringUtilsV2.lastStr(parentId, Separator.COMMA_SIGN);
        if (!CommonUtils.isNumeric(idStr)) {
            return null;
        }
        Permission record = super.getById(Long.valueOf(idStr));
        if (record != null) return record.getName();
        return null;
    }

    @Override
    public List<Permission> match(String urlPrefix, String method) {
        return super.query().likeRight("url", urlPrefix)
                .eq(CommonUtils.isNotEmpty(method), "method", method)
                .list();
    }

    public List<Permission> findChildrenByPidAndStatusAndType(String pid, String status, String type) {
        if (CommonUtils.isEmpty(pid)) {
            return Lists.newArrayList();
        }

        QueryChainWrapper<Permission> wrapper = super.query().eq("parent_id", pid).eq(CommonUtils.isNotEmpty(status), "status", status);

        if (CommonUtils.isEquals(Num.S1, type)) {
            wrapper.eq("type", type);
        } else if (CommonUtils.isEquals(Num.S2, type)) {
            wrapper.in("type", Lists.newArrayList(Num.S1, Num.S2));
        } else if (!CommonUtils.isEquals(Num.S0, type)) {
            wrapper.ne("type", Num.S5);
        }
        wrapper.orderByAsc("sort", "id");
        List<Permission> children = wrapper.list();
        if (CommonUtils.isEmpty(children)) return Lists.newArrayList();
        children.forEach(r -> {
            String pid1 = StringUtilsV2.linkStr(",", "0".equals(r.getParentId()) ? "" : r.getParentId(), r.getId().toString());
            if (Lists.newArrayList(Num.S1, Num.S2).contains(r.getType())
                    && (CommonUtils.isEquals(Num.S0, type) || CommonUtils.isEquals(Num.S9, type)
                    || (CommonUtils.isEquals(Num.S3, type) && CommonUtils.isEquals(Num.S1, r.getType())))) {
                r.setChildren(findChildrenByPidAndStatusAndType(pid1, status, type));
            }
        });
        return children;
    }

    public List<Permission> findTreeBy(Permission record) {
        if (CommonUtils.isEmpty(record.getParentId())) {
            record.setParentId("0");
        }
        Map<String, Object> params = BeanUtilsV2.getDeclaredFieldValueMap(record);
        List<Permission> list = super.query().allEq(params).list();
        if (list.isEmpty()) return list;
        findChildrenTree(list);
        list.sort(Comparator.comparing(Permission::getSort));
        return list;
    }

    private Map<String, List<Permission>> assembleTree(List<Permission> subList) {
        Map<String, List<Permission>> subMap = subList.stream().collect(Collectors.groupingBy(Permission::getParentId));
        subMap.forEach((k, v) -> v.forEach(r -> {
            r.setChildren(subMap.get(StringUtilsV2.linkStr(Separator.COMMA_SIGN, r.getParentId(), r.getId().toString())));
            if (r.getChildren() != null) r.getChildren().sort(Comparator.comparing(Permission::getSort));
        }));
        return subMap;
    }


    @Override
    public int updateWithNull(Permission record) {
//        UpdateChainWrapper<Permission> wrapper = super.update().eq("id", record.getId());
//        Map<String, Object> valueMap = BeanExtUtils.getDeclaredFieldValueMap(record, false);
//        valueMap.forEach(wrapper::set);
        LambdaUpdateWrapper<Permission> wrapper = Wrappers.<Permission>lambdaUpdate().eq(Permission::getId, record.getId())
                .set(Permission::getCode, record.getCode())
                .set(Permission::getMethod, record.getMethod())
                .set(Permission::getIcon, record.getIcon())
                .set(Permission::getHidden, record.getHidden())
                .set(Permission::getSys, record.getSys())
                .set(Permission::getName, record.getName())
                .set(Permission::getParentId, record.getParentId())
                .set(Permission::getParentName, record.getParentName())
                .set(Permission::getRedirect, record.getRedirect())
                .set(Permission::getSort, record.getSort())
                .set(Permission::getStatus, record.getStatus())
                .set(Permission::getType, record.getType())
                .set(Permission::getUpdateAt, record.getUpdateAt())
                .set(Permission::getUpdateBy, record.getUpdateBy())
                .set(Permission::getUrl, record.getUrl());

        boolean isUpdated = super.update(wrapper);
        if (CommonUtils.isNotEmpty(record.getChildren())) {
            record.getChildren().forEach(e -> {
                e.setParentId(record.getParentId() + "," + record.getId());
                e.setUpdateBy(record.getUpdateBy());
                e.setUpdateAt(record.getUpdateAt());
                super.updateById(e);
            });
        }
        return isUpdated ? 1 : 0;
    }

    @Override
    public boolean delete(Long id) {
        if (id == null) return false;
        List<Permission> records = this.findByPid(id + "");
        if (!records.isEmpty()) {
            ExceptionUtils._throw(PubError.NOT_ALLOWED, "存在下级权限，不允许删除！");
        }
        return super.removeById(id);
    }

    @Override
    public int updateRoleAuthPermissions(Long roleId, List<Long> permissionIds) {
        int c1 = getBaseMapper().deleteRoleAuthPermissions(roleId);
        if (!permissionIds.isEmpty()) {
            c1 += getBaseMapper().insertRolePermissions(roleId, permissionIds);
        }
        return c1;
    }
}