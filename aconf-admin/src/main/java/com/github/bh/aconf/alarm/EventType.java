package com.github.bh.aconf.alarm;

/**
 * @author xiaobenhai
 * Date: 2017/3/21
 * Time: 11:08
 */
public enum EventType {
    CREATED("创建了"),
    UPDATED("更新了"),
    DELETED("删除了");

    EventType(String message) {
        this.message = message;
    }

    private String message;

    public String getMessage() {
        return message;
    }
}
