package com.github.bh.aconf.common.constants;

/**
 * @author xiaobenhai
 * Date: 2017/1/9
 * Time: 16:46
 */
public enum SendDefaultType {
    NOT_SEND((byte) 0),

    SEND((byte) 1);

    private byte value;

    SendDefaultType(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

    public boolean isMe(Byte value) {
        return value != null && value == this.value;
    }
}
