package com.ch.cloud.upms.ueditor;


import com.ch.pojo.FileInfo;

import java.io.Serializable;
import java.util.List;

/**
 * @author 01370603
 * @date 2018/12/24 13:51
 */
public class UEListResult implements Serializable {

    private String state;
    private int start;
    private int total;
    private List<FileInfo> list;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<FileInfo> getList() {
        return list;
    }

    public void setList(List<FileInfo> list) {
        this.list = list;
    }
}
