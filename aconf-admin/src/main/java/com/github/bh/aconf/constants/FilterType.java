package com.github.bh.aconf.constants;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.EnumSet;
import java.util.Map;

/**
 * @author xiaobenhai
 * Date: 2017/3/3
 * Time: 9:56
 */
public enum FilterType {
    SID(1, "sid"),
    UID(2, "uid"),
    OTHER(999, "other");

    private Integer code;
    private String msg;

    FilterType(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer code() {
        return code;
    }

    public String msg() {
        return msg;
    }

    private static final Map<String, FilterType> FILTER_TYPE_MAP = Maps.newHashMap();

    static {
        for (FilterType filterType : EnumSet.allOf(FilterType.class)) {
            FILTER_TYPE_MAP.put(filterType.msg, filterType);
        }
    }

    public static FilterType find(String msg) {
        if (StringUtils.isEmpty(msg)) {
            return OTHER;
        }
        FilterType filterType = FILTER_TYPE_MAP.get(msg);
        if (filterType == null) {
            return OTHER;
        }
        return filterType;
    }

    public boolean compare(Integer c) {
        return c != null && code.equals(c);
    }
}
