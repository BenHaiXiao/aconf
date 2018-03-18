package com.github.bh.aconf.domain.command;

/**
 * @author xiaobenhai
 * Date: 2016/11/3
 * Time: 11:57
 * bss参数对象
 */
public class BssCommand {
    private Long id;
    private String code;
    private String name;
    private String description;
    private String creator;
    private String developer;
    private String tester;
    private String operator;
    private Byte valid;
    private Long appId;
    private Byte effectiveType;
    private String effectiveTime;
    private String failureTime;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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

    public String getTester() {
        return tester;
    }

    public void setTester(String tester) {
        this.tester = tester;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Byte getValid() {
        return valid;
    }

    public void setValid(Byte valid) {
        this.valid = valid;
    }

    public Byte getEffectiveType() {
        return effectiveType;
    }

    public void setEffectiveType(Byte effectiveType) {
        this.effectiveType = effectiveType;
    }

    @Override
    public String toString() {
        return "BssCommand{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", creator='" + creator + '\'' +
                ", developer='" + developer + '\'' +
                ", tester='" + tester + '\'' +
                ", operator='" + operator + '\'' +
                ", valid=" + valid +
                ", appId=" + appId +
                ", effectiveType=" + effectiveType +
                ", effectiveTime=" + effectiveTime +
                ", failureTime=" + failureTime +
                '}';
    }
}
