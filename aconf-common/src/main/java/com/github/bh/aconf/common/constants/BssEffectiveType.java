package com.github.bh.aconf.common.constants;

/**
 * @author xiaobenhai
 * Date: 2017/3/15
 * Time: 17:52
 */
public enum BssEffectiveType {

    ALWAYS((byte) 0),   //永久

    TIMES((byte) 1),    //区间

    UNKNOWN((byte) 9);  //其他

    private byte value;

    BssEffectiveType(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

    public boolean isMe(Byte value) {
        return value != null && value == this.value;
    }
}
