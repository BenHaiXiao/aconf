package com.github.bh.aconf.domain.vo;

import java.util.Date;
import java.util.List;

/**
 * @author xiaobenhai
 * Date: 2016/11/3
 * Time: 14:30
 * 业务视图对象
 */
public class BssVo {
    private Long id;
    private String code;
    private String name;
    private String creator;
    private String developer;
    private String tester;
    private String operator;
    private Date createTime;
    private Date updateTime;
    private String description;
    private Byte valid;
    private Byte effectiveType;
    private String effectiveTime;
    private String failureTime;
    private Long version;
    private List<FilterVo> filters;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<FilterVo> getFilters() {
        return filters;
    }

    public void setFilters(List<FilterVo> filters) {
        this.filters = filters;
    }

    public Byte getEffectiveType() {
        return effectiveType;
    }

    public void setEffectiveType(Byte effectiveType) {
        this.effectiveType = effectiveType;
    }

    public String getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(String effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public String getFailureTime() {
        return failureTime;
    }

    public void setFailureTime(String failureTime) {
        this.failureTime = failureTime;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getTester() {
        return tester;
    }

    public void setTester(String tester) {
        this.tester = tester;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Byte getValid() {
        return valid;
    }

    public void setValid(Byte valid) {
        this.valid = valid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
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

    @Override
    public String toString() {
        return "BssVo{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", creator='" + creator + '\'' +
                ", developer='" + developer + '\'' +
                ", tester='" + tester + '\'' +
                ", operator='" + operator + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", description='" + description + '\'' +
                ", valid=" + valid +
                ", effectiveType=" + effectiveType +
                ", effectiveTime=" + effectiveTime +
                ", failureTime=" + failureTime +
                ", version=" + version +
                ", filters=" + filters +
                '}';
    }
}
