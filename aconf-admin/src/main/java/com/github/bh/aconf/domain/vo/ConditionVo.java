package com.github.bh.aconf.domain.vo;

import java.util.Date;
import java.util.List;

/**
 * @author xiaobenhai
 * Date: 2017/1/9
 * Time: 17:43
 */
public class ConditionVo {
    private Long id;
    private Long configId;
    private String name;
    private String value;
    private String creator;
    private Date createTime;
    private Date updateTime;
    private Integer state;
    private List<FilterVo> filters;
    private Byte valueType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getConfigId() {
        return configId;
    }

    public void setConfigId(Long configId) {
        this.configId = configId;
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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public List<FilterVo> getFilters() {
        return filters;
    }

    public void setFilters(List<FilterVo> filters) {
        this.filters = filters;
    }

    public Byte getValueType() {
        return valueType;
    }

    public void setValueType(Byte valueType) {
        this.valueType = valueType;
    }

    @Override
    public String toString() {
        return "ConditionVo{" +
                "id=" + id +
                ", configId=" + configId +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", creator='" + creator + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", state=" + state +
                ", filters=" + filters +
                ", valueType=" + valueType +
                '}';
    }
}
