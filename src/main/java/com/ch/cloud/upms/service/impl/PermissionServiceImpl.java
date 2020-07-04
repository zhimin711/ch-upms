
package com.ch.cloud.upms.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ch.Constants;
import com.ch.NumS;
import com.ch.Status;
import com.ch.cloud.upms.mapper.PermissionMapper;
import com.ch.cloud.upms.model.Permission;
import com.ch.cloud.upms.service.IPermissionService;
import com.ch.e.PubError;
import com.ch.utils.CommonUtils;
import com.ch.utils.ExceptionUtils;
import com.ch.utils.StringExtUtils;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;

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
        Page<Permission> page = super.query().eq("type", NumS._1).eq("parent_id", NumS._0)
                .eq(status == Status.ENABLED, "status", status.getCode())
                .orderByAsc("sort", "parent_id", "id").page(new Page<>(pageNum, pageSize));
        if (page.getTotal() > 0) {
            page.getRecords().forEach(r -> r.setChildren(findChildrenByPidAndStatusAndType(r.getId().toString(), null, "0")));
        }

        return page;
    }


    private List<Permission> findTopCategory(Status status) {
        return super.query().eq("type", NumS._1).eq("parent_id", NumS._0)
                .eq(status == Status.ENABLED, "status", status.getCode())
                .orderByAsc("sort", "parent_id", "id").list();
    }

    @Override
    public List<Permission> findTreeByType(String type) {
        Status status = Lists.newArrayList(NumS._0, NumS._9).contains(type) ? Status.ENABLED : Status.UNKNOWN;
        List<Permission> records = findTopCategory(status);
        if (records.isEmpty()) {
            return Lists.newArrayList();
        }
        if (!CommonUtils.isEquals(type, NumS._1))
            records.forEach(r -> {
                List<Permission> children = findChildrenByPidAndStatusAndType(r.getId().toString(), status != Status.UNKNOWN ? status.getCode() : null, type);
                r.setChildren(children);
            });
        return records;
    }

    @Override
    public List<Permission> findByTypeAndRoleId(List<String> types, Long roleId) {
        return getBaseMapper().findByTypeAndRoleId(types, roleId);
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
        return findChildrenByPidAndStatusAndType(pid, null, NumS._0);
    }

    @Override
    public String findNameByParseLastId(String parentId) {
        if (CommonUtils.isEmpty(parentId)) return null;
        String idStr = StringExtUtils.lastStr(parentId, Constants.SEPARATOR_2);
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

        if (CommonUtils.isEquals(NumS._1, type)) {
            wrapper.eq("type", type);
        } else if (CommonUtils.isEquals(NumS._2, type)) {
            wrapper.in("type", Lists.newArrayList(NumS._1, NumS._2));
        } else if (!CommonUtils.isEquals(NumS._0, type)) {
            wrapper.ne("type", NumS._5);
        }
        wrapper.orderByAsc("sort", "id");
        List<Permission> children = wrapper.list();
        if (CommonUtils.isEmpty(children)) return Lists.newArrayList();
        children.forEach(r -> {
            String pid1 = StringExtUtils.linkStr(",", "0".equals(r.getParentId()) ? "" : r.getParentId(), r.getId().toString());
            if (Lists.newArrayList(NumS._1, NumS._2).contains(r.getType())
                    && (CommonUtils.isEquals(NumS._0, type) || CommonUtils.isEquals(NumS._9, type)
                    || (CommonUtils.isEquals(NumS._3, type) && CommonUtils.isEquals(NumS._1, r.getType())))) {
                r.setChildren(findChildrenByPidAndStatusAndType(pid1, status, type));
            }
        });
        return children;
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
}