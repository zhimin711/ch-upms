package com.ch.cloud.upms.utils;

import com.ch.Constants;
import com.ch.cloud.upms.model.StMenu;
import com.ch.pojo.VueRecord;
import com.ch.utils.CommonUtils;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 01370603
 */
public class VueRecordUtils {

    /**
     * 转换权限
     *
     * @param record
     * @return
     */
    public static VueRecord convertMenu(StMenu record) {
        VueRecord vueRecord = new VueRecord();
        vueRecord.setLabel(record.getName());
        vueRecord.setValue(record.getId().toString());
        vueRecord.setDisabled(!CommonUtils.isEquals(Constants.ENABLED, record.getStatus()));
        if (CommonUtils.isNotEmpty(record.getChildren())) {
            vueRecord.setChildren(convertMenus(record.getChildren()));
        }
        return vueRecord;
    }

    /**
     * 批量转换权限
     *
     * @param records
     * @return
     */
    public static List<VueRecord> convertMenus(List<StMenu> records) {
        if (CommonUtils.isEmpty(records)) {
            return Lists.newArrayList();
        }
        return records.stream().map(VueRecordUtils::convertMenu).collect(Collectors.toList());
    }

    public static VueRecord convertMenuByType(StMenu record, String type) {
        VueRecord vueRecord = new VueRecord();
        vueRecord.setLabel(record.getName());
        vueRecord.setValue(record.getId().toString());
        boolean disabled = !CommonUtils.isEquals(Constants.ENABLED, record.getStatus());
        if (!disabled
                && Lists.newArrayList("3", "4").contains(type)
                && CommonUtils.isEquals(record.getType(), "1")
                && CommonUtils.isEmpty(record.getChildren())) {
            disabled = true;
        }
        vueRecord.setDisabled(disabled);
        if (CommonUtils.isNotEmpty(record.getChildren())) {
            vueRecord.setChildren(convertMenus(record.getChildren()));
        }
        return vueRecord;
    }

    public static List<VueRecord> convertMenusByType(List<StMenu> records, String type) {
        if (CommonUtils.isEmpty(records)) {
            return Lists.newArrayList();
        }
        return records.stream().map(r -> convertMenuByType(r, type)).collect(Collectors.toList());
    }
}
