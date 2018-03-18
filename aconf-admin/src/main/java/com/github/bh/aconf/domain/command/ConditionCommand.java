package com.github.bh.aconf.domain.command;

/**
 * @author xiaobenhai
 * Date: 2017/1/9
 * Time: 17:30
 */
public class ConditionCommand {
    private String name;
    private String value;
    private String creator;

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
}
