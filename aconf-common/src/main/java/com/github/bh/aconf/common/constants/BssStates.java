package com.github.bh.aconf.common.constants;

/**
 * @author xiaobenhai
 * Date: 2017/1/11
 * Time: 16:09
 */
public enum BssStates {
    DELETE((byte) 0),   //已删除

    VALID((byte) 1),    //生效中

    INVALID((byte) 2),  //还没生效

    FINISHED((byte) 3),  //结束

    ALL((byte) 8),  //全部

    UNKNOWN((byte) 9);  //其他

    private byte value;

    BssStates(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

    public boolean isMe(Byte value) {
        return value != null && value == this.value;
    }
}
