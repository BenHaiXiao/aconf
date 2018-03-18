package com.github.bh.aconf.domain.vo;

import java.util.Date;
import java.util.List;

/**
 * @author xiaobenhai
 * Date: 2017/1/9
 * Time: 19:13
 */
public class ConfigVoV2 {
    private Long id;
    private String key;
    private String value;
    private List<ConditionVo> conditions;
    private Date updateTime;
    private long version;
    private String description;
    private long bssId;
    private CreatorVo creator;
    private Byte valid;
    private Byte sendDefault;
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

    public List<ConditionVo> getConditions() {
        return conditions;
    }

    public void setConditions(List<ConditionVo> conditions) {
        this.conditions = conditions;
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

    public CreatorVo getCreator() {
        return creator;
    }

    public void setCreator(CreatorVo creator) {
        this.creator = creator;
    }

    public Byte getValid() {
        return valid;
    }

    public void setValid(Byte valid) {
        this.valid = valid;
    }

    public Byte getSendDefault() {
        return sendDefault;
    }

    public void setSendDefault(Byte sendDefault) {
        this.sendDefault = sendDefault;
    }

    public Byte getValueType() {
        return valueType;
    }

    public void setValueType(Byte valueType) {
        this.valueType = valueType;
    }

    @Override
    public String toString() {
        return "ConfigVoV2{" +
                "id=" + id +
                ", key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", conditions=" + conditions +
                ", updateTime=" + updateTime +
                ", version=" + version +
                ", description='" + description + '\'' +
                ", bssId=" + bssId +
                ", creator=" + creator +
                ", valid=" + valid +
                ", sendDefault=" + sendDefault +
                ", valueType=" + valueType +
                '}';
    }
}
