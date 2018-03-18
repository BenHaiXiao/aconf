package com.github.bh.aconf.domain;

import java.util.List;

/**
 * @author xiaobenhai
 * Date: 2017/3/14
 * Time: 17:30
 */
public class StaffInfoDto {
    private int page;
    private int pageSize;
    private int totalRows;
    private List<StaffInfo> data;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public List<StaffInfo> getData() {
        return data;
    }

    public void setData(List<StaffInfo> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "StaffInfoDto{" +
                "page=" + page +
                ", pageSize=" + pageSize +
                ", totalRows=" + totalRows +
                ", data=" + data +
                '}';
    }
}
