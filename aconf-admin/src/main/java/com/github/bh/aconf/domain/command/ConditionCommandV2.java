package com.github.bh.aconf.domain.command;

import java.util.List;

/**
 * @author xiaobenhai
 * Date: 2017/1/18
 * Time: 15:15
 */
public class ConditionCommandV2 {
    private Long id;
    private String name;
    private String value;
    private int seq;
    private List<FilterCommandV2> filters;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<FilterCommandV2> getFilters() {
        return filters;
    }

    public void setFilters(List<FilterCommandV2> filters) {
        this.filters = filters;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    @Override
    public String toString() {
        return "ConditionCommandV2{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", seq=" + seq +
                ", filters=" + filters +
                '}';
    }
}
