package com.github.bh.aconf.common.constants;

/**
 * @author xiaobenhai
 */
public enum PushMetaStates {

    DELETED(100, "已删除"),

    EFFECTIVE(101, "生效"),

    PUSHING(102, "推送中"),

    PUSHED(103, "已推送"),

    INEFFECTIVE(104, "无效"),

    ARCHIVED(105, "已归档");

    private int value;
    private String message;

    PushMetaStates(int value, String message) {
        this.value = value;
        this.message = message;
    }

    public int getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }
}
