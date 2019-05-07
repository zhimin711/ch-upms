package com.ch.cloud.upms.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhimin
 * @date 2018/12/29 10:54
 */
public class VueRecord implements Serializable {

    /**
     * 值
     */
    private String value;
    /**
     * 显示名称
     */
    private String label;
    /**
     * 禁用
     */
    private boolean disabled = false;
    /**
     * 子集
     */
    private List<VueRecord> children;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public List<VueRecord> getChildren() {
        return children;
    }

    public void setChildren(List<VueRecord> children) {
        this.children = children;
    }
}
