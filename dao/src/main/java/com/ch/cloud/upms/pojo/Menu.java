package com.ch.cloud.upms.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * Vue 菜单
 *
 * @author 01370603
 * @since 2018/12/22 16:45
 */
public class Menu implements Serializable {

    private String icon;
    private String index;
    private String title;

    private List<Menu> subs;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Menu> getSubs() {
        return subs;
    }

    public void setSubs(List<Menu> subs) {
        this.subs = subs;
    }
}

