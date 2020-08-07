package com.ch.cloud.upms.utils;

import com.ch.Constants;
import com.ch.NumS;
import com.ch.cloud.upms.model.Permission;
import com.ch.pojo.VueRecord;
import com.ch.utils.CommonUtils;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 01370603
 */


public class VueRecordUtils {


    public static List<VueRecord> convertParentsByType(List<Permission> records, String type) {
        if (CommonUtils.isEmpty(records)) {
            return Lists.newArrayList();
        }

        boolean isMenu = CommonUtils.isEquals(NumS._2, type);
        if (isMenu) {
            return convertCategory(records);
        }
        return records.stream().map(r -> convertAuthByType(r, type)).collect(Collectors.toList());
    }

    private static List<VueRecord> convertCategory(List<Permission> records) {
        List<VueRecord> categories = Lists.newArrayList();
        records.stream().filter(r -> CommonUtils.isEquals(NumS._1, r.getType())).forEach(r -> {
            VueRecord vueRecord = convertPermission(r);
            categories.add(vueRecord);
            if (r.getChildren() == null || r.getChildren().isEmpty()) {
                return;
            }
            List<Permission> list = r.getChildren().stream().filter(e -> CommonUtils.isEquals(NumS._1, e.getType())).collect(Collectors.toList());
            if (list.isEmpty()) {
                return;
            }
            vueRecord.setChildren(convertCategory(list));
            r.getChildren().forEach(e -> {
                if (CommonUtils.isEquals(NumS._1, e.getType())) {
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
//        boolean isCategory = CommonUtils.isEquals(NumS._1, type);
//        boolean isMenu = CommonUtils.isEquals(NumS._2, type);
        boolean isBtn = CommonUtils.isEquals(NumS._3, type);
        if (isBtn && !vueRecord.isDisabled()) {
            boolean disabled = CommonUtils.isEquals(record.getType(), "1") && CommonUtils.isEmpty(record.getChildren());
            vueRecord.setDisabled(disabled);
        }
        if (CommonUtils.isNotEmpty(record.getChildren())) {
            vueRecord.setChildren(convertParentsByType(record.getChildren(), type));
        }
        return vueRecord;
    }


    private static VueRecord convertPermission(Permission record) {
        VueRecord vueRecord = new VueRecord();
        vueRecord.setLabel(record.getName());
        vueRecord.setValue(record.getId().toString());
        vueRecord.setDisabled(!CommonUtils.isEquals(Constants.ENABLED, record.getStatus()));
        return vueRecord;
    }
}
