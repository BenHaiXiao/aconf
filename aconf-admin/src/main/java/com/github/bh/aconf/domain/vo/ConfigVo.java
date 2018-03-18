package com.github.bh.aconf.domain.vo;

import java.util.Date;
import java.util.List;

/**
 * @author xiaobenhai
 * Date: 2016/11/3
 * Time: 15:53
 */
public class ConfigVo {
    private Long id;
    private String key;
    private String value;
    private List<FilterVo> filters;
    private Date updateTime;
    private long version;
    private String description;
    private long bssId;
    private String creator;
    private Byte valid;
    private Byte valueType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getBssId() {
        return bssId;
    }

    public void setBssId(long bssId) {
        this.bssId = bssId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Byte getValid() {
        return valid;
    }

    public void setValid(Byte valid) {
        this.valid = valid;
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
        return "ConfigVo{" +
                "id=" + id +
                ", key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", filters=" + filters +
                ", updateTime=" + updateTime +
                ", version=" + version +
                ", description='" + description + '\'' +
                ", bssId=" + bssId +
                ", creator='" + creator + '\'' +
                ", valid=" + valid +
                ", valueType=" + valueType +
                '}';
    }
}
