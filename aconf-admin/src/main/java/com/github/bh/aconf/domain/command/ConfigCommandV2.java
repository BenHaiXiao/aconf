package com.github.bh.aconf.domain.command;

import java.util.List;

/**
 * @author xiaobenhai
 * Date: 2017/1/18
 * Time: 15:11
 */
public class ConfigCommandV2 {
    private String key;
    private String value;
    private String description;
    private Byte sendDefault;
    private List<ConditionCommandV2> conditions;
    private List<Long> deletedConditionIds;
    private List<Long> deletedFilterIds;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Byte getSendDefault() {
        return sendDefault;
    }

    public void setSendDefault(Byte sendDefault) {
        this.sendDefault = sendDefault;
    }

    public List<ConditionCommandV2> getConditions() {
        return conditions;
    }

    public void setConditions(List<ConditionCommandV2> conditions) {
        this.conditions = conditions;
    }

    public List<Long> getDeletedConditionIds() {
        return deletedConditionIds;
    }

    public void setDeletedConditionIds(List<Long> deletedConditionIds) {
        this.deletedConditionIds = deletedConditionIds;
    }

    public List<Long> getDeletedFilterIds() {
        return deletedFilterIds;
    }

    public void setDeletedFilterIds(List<Long> deletedFilterIds) {
        this.deletedFilterIds = deletedFilterIds;
    }

    @Override
    public String toString() {
        return "ConfigCommandV2{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", description='" + description + '\'' +
                ", sendDefault=" + sendDefault +
                ", conditions=" + conditions +
                ", deletedConditionIds=" + deletedConditionIds +
                ", deletedFilterIds=" + deletedFilterIds +
                '}';
    }
}
