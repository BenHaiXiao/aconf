package com.github.bh.aconf.domain;

import java.util.List;

/**
 * @author xiaobenhai
 * Date: 2016/11/9
 * Time: 17:22
 */
public class Page<T> {
    private int totalSize;
    private List<T> list;

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
