package com.github.bh.aconf.domain.command;

/**
 * @author xiaobenhai
 * Date: 2016/12/27
 * Time: 14:39
 * 着重使用Code作为查找依据的Command，主要用于向外部提供API
 */
public class BssCodeCommand {
    private String code;
    private String name;
    private String description;
    private String creator;
    private String developer;
    private String tester;
    private String operator;
    private Long effectiveTime;
    private Long failureTime;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Long getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(Long effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public Long getFailureTime() {
        return failureTime;
    }

    public void setFailureTime(Long failureTime) {
        this.failureTime = failureTime;
    }

    @Override
    public String toString() {
        return "BssCodeCommand{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", creator='" + creator + '\'' +
                ", developer='" + developer + '\'' +
                ", tester='" + tester + '\'' +
                ", operator='" + operator + '\'' +
                ", effectiveTime=" + effectiveTime +
                ", failureTime=" + failureTime +
                '}';
    }
}
