package com.ch.cloud.upms.utils;

import com.ch.Constants;
import com.ch.Num;
import com.ch.Separator;
import com.ch.cloud.client.types.PermissionType;
import com.ch.cloud.upms.model.Permission;
import com.ch.pojo.VueRecord;
import com.ch.pojo.VueRecord2;
import com.ch.utils.CommonUtils;
import com.ch.utils.StringUtilsV2;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 01370603
 */


public class VueRecordUtils {


    public static List<VueRecord> convertTreeByType(List<Permission> records, String type) {
        if (CommonUtils.isEmpty(records)) {
            return Lists.newArrayList();
        }

        boolean isMenu = CommonUtils.isEquals(Num.S2, type);
        boolean isInterface = CommonUtils.isEquals(Num.S4, type);
        if (isMenu) {
            return convertCategory(records);
        } else if (isInterface) {
            return convertAuthInterfaceTree(records);
        }
        return convertAuthPermissionTree(records, type);
    }

    private static List<VueRecord> convertAuthPermissionTree(List<Permission> records, String type) {
        return records.stream().map(r -> convertAuthByType(r, type)).collect(Collectors.toList());
    }

    private static List<VueRecord> convertAuthInterfaceTree(List<Permission> records) {
        Map<String, List<Permission>> map = records.stream().collect(Collectors.groupingBy(Permission::getParentId));
        map.forEach((k, v) -> v.forEach(e -> {
            List<Permission> list = map.get(StringUtilsV2.linkStrIgnoreZero(Separator.COMMA_SIGN, k, e.getId().toString()));
            if (CommonUtils.isNotEmpty(list)) {
                e.setChildren(list);
            }
        }));
        return convertAuthPermissionTree(map.get("0"), null);
    }

    private static List<VueRecord> convertCategory(List<Permission> records) {
        List<VueRecord> categories = Lists.newArrayList();
        records.stream().filter(r -> CommonUtils.isEquals(Num.S1, r.getType())).forEach(r -> {
            VueRecord vueRecord = convertPermission(r);
            categories.add(vueRecord);
            if (r.getChildren() == null || r.getChildren().isEmpty()) {
                return;
            }
            List<Permission> list = r.getChildren().stream().filter(e -> CommonUtils.isEquals(Num.S1, e.getType())).collect(Collectors.toList());
            if (list.isEmpty()) {
                return;
            }
            vueRecord.setChildren(convertCategory(list));
            r.getChildren().forEach(e -> {
                if (CommonUtils.isEquals(Num.S1, e.getType())) {
                    VueRecord vueRecord1 = convertPermission(e);
                    vueRecord1.setLabel(r.getName() + " >> " + e.getName());
//                    categories.add(vueRecord1);
                }
            });

        });
        return categories;
    }

    private static VueRecord convertAuthByType(Permission record, String type) {
        VueRecord vueRecord = convertPermission(record);
//        boolean isCategory = CommonUtils.isEquals(Num.S1, type);
//        boolean isMenu = CommonUtils.isEquals(Num.S2, type);
        boolean isBtn = CommonUtils.isEquals(Num.S3, type);
        if (isBtn && !vueRecord.isDisabled()) {
            boolean disabled = CommonUtils.isEquals(record.getType(), "1") && CommonUtils.isEmpty(record.getChildren());
            vueRecord.setDisabled(disabled);
        }
        if (CommonUtils.isNotEmpty(record.getChildren())) {
            vueRecord.setChildren(convertAuthPermissionTree(record.getChildren(), type));
        }
        return vueRecord;
    }


    private static VueRecord convertPermission(Permission record) {
        VueRecord2 vueRecord = new VueRecord2();
        vueRecord.setLabel(record.getName());
        vueRecord.setValue(record.getId().toString());
        PermissionType type = PermissionType.from(record.getType(), record.getHidden());
        vueRecord.setKey(record.getCode() + Separator.VERTICAL_LINE + type.name());
        vueRecord.setDisabled(!CommonUtils.isEquals(Constants.ENABLED, record.getStatus()));
        return vueRecord;
    }
}
