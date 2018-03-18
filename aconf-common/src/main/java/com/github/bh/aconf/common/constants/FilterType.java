package com.github.bh.aconf.common.constants;

import com.google.common.collect.Maps;

import java.util.EnumSet;
import java.util.Map;

/**
 * @author xiaobenhai
 * Date: 2017/3/23
 * Time: 15:11
 */
public enum FilterType {
    NORMAL(0),

    EXTENSION_STRING(1),

    EXTENSION_NUMBER(2);

    private byte value;

    FilterType(Integer value) {
        this.value = value.byteValue();
    }

    public static Map<Byte, FilterType> filterTypeMap = Maps.newHashMap();

    static {
        for (FilterType filterType : EnumSet.allOf(FilterType.class)) {
            filterTypeMap.put(filterType.getValue(), filterType);
        }
    }

    public byte getValue() {
        return value;
    }

    public boolean isMe(Byte value) {
        return value != null && value == this.value;
    }

    public boolean isMe(FilterType filterType) {
        return this == filterType;
    }

    public static FilterType valueOf(Integer value) {
        return valueOf(value.byteValue());
    }

    public static FilterType valueOf(Byte value) {
        if (value != null) {
            return filterTypeMap.get(value);
        }
        return null;
    }
}
