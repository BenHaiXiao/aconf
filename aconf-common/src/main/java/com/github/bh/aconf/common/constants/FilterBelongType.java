package com.github.bh.aconf.common.constants;

/**
 * @author xiaobenhai
 */
public enum FilterBelongType {

    BSS((byte) 0),

    ITEM((byte) 1),

    CONDITION((byte) 2);

    private byte value;

    FilterBelongType(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

    public boolean isMe(Byte value) {
        if (value == null) {
            return false;
        }
        return value.byteValue() == this.value;
    }
}
