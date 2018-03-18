package com.github.bh.aconf.common.constants;

import com.google.common.collect.Maps;

import java.util.EnumSet;
import java.util.Map;

/**
 * 有效状态值。
 *
 * @author xiaobenhai
 */
public enum ValidStatus {

    INVALID((byte) 0),

    VALID((byte) 1),

    UNKNOWN((byte) 9);

    private byte value;

    private static Map<Byte, ValidStatus> validStatusMap = Maps.newConcurrentMap();

    static {
        for (ValidStatus validStatus : EnumSet.allOf(ValidStatus.class)) {
            validStatusMap.put(validStatus.getValue(), validStatus);
        }
    }

    ValidStatus(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

    public static ValidStatus find(byte value) {
        ValidStatus validStatus = validStatusMap.get(value);
        if (validStatus != null) {
            return validStatus;
        }
        return UNKNOWN;
    }
}
