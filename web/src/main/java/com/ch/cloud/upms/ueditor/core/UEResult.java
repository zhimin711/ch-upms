package com.ch.cloud.upms.ueditor.core;

import java.io.Serializable;

/**
 * @author 01370603
 * @since 2018/12/24 13:51
 */
public class UEResult implements Serializable {

    private String state = "ERROR";
    private String original;
    private String size;
    private String title;
    private String type;
    private String url;
    private String md5;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isSuccess() {
        return state != null && "SUCCESS".equals(state);
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
}
