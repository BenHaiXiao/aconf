package com.github.bh.aconf.domain.command;

/**
 * @author xiaobenhai
 * Date: 2016/12/28
 * Time: 9:49
 */
public class AppRegisterCommand {
    private String name;
    private String description;

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

    @Override
    public String toString() {
        return "AppRegisterCommand{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
