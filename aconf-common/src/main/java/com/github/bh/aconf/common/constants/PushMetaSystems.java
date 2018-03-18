package com.github.bh.aconf.common.constants;

/**
 * @author xiaobenhai
 */
public enum PushMetaSystems {

    ALL((byte) 0),

    PC((byte) 1),

    MOBILE((byte) 2);

    private byte value;

    PushMetaSystems(byte value) {
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
