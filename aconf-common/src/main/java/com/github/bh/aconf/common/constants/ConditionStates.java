package com.github.bh.aconf.common.constants;

/**
 * @author xiaobenhai
 * Date: 2017/1/9
 * Time: 15:17
 */
public enum ConditionStates {
    DELETE(100),

    VALID(101),

    INVALID(102);

    private int value;

    ConditionStates(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public boolean isMe(Integer value) {
        if (value == null) {
            return false;
        }
        return value == this.value;
    }
}
