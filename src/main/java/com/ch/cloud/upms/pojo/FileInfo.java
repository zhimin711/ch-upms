package com.ch.cloud.upms.pojo;

import java.io.Serializable;

/**
 * @author 01370603
 * @date 2018/12/24 19:41
 */
public class FileInfo implements Serializable {

    private long size;
    private String title;
    private String url;
    private String type;
    private String original;


    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }
}
