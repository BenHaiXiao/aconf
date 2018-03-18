package com.github.bh.aconf.common.constants;

import com.google.common.collect.Maps;

import java.util.EnumSet;
import java.util.Map;

/**
 * @author xiaobenhai
 * Date: 2017/2/16
 * Time: 14:35
 */
public enum ValueType {
    VALUE((byte) 0),  //普通数值

    EXPRESSION((byte) 1),  //表达式

    RESOURCE((byte) 2),   //资源

    UNKNOWN((byte) 9);

    private byte value;

    private static Map<Byte, ValueType> valueTypeMap = Maps.newConcurrentMap();

    static {
        for (ValueType valueType : EnumSet.allOf(ValueType.class)) {
            valueTypeMap.put(valueType.getValue(), valueType);
        }
    }

    ValueType(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

    public static ValueType find(byte value) {
        ValueType valueType = valueTypeMap.get(value);
        if (valueType != null) {
            return valueType;
        }
        return UNKNOWN;
    }

    public boolean isMe(Byte valueType) {
        return value == valueType;
    }
}
