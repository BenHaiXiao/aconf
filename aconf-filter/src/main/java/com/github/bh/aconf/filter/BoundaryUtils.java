package com.github.bh.aconf.filter;

import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import com.google.gson.reflect.TypeToken;
import com.github.bh.aconf.common.utils.JsonUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author xiaobenhai
 * Date: 2017/3/5
 * Time: 10:11
 */
public final class BoundaryUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(BoundaryUtils.class);

    private static final char MULTI_VALUE_SEPARATOR = ',';
    private static final char RANGE_SEPARATOR = '-';

    private BoundaryUtils() {
        // no-op
    }

    public static List<Long> parseForLongList(String boundary) {
        List<String> boundaries = splitJson(boundary);
        List<Long> expects = Lists.newArrayList();
        for (String eachBoundary : boundaries) {
            try {
                expects.add(Long.parseLong(eachBoundary));
            } catch (NumberFormatException e) {
                LOGGER.warn("boundary exists non-number : {}", eachBoundary);
            }
        }
        return expects;
    }

    public static List<String> splitJson(String boundary) {
        if (boundary.startsWith("[") && boundary.endsWith("]")) {
            return JsonUtils.fromJson(boundary, new TypeToken<List<String>>() {
            }.getType());
        } else {
            return Lists.newArrayList(boundary);
        }
    }

    public static String[] split(String boundary) {
        return split(boundary, MULTI_VALUE_SEPARATOR);
    }

    public static String[] split(String boundary, char separator) {
        if (StringUtils.isEmpty(boundary)) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        String[] boundaries = StringUtils.split(boundary, separator);
        if (ArrayUtils.isEmpty(boundaries)) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        return boundaries;
    }

    public static List<Range> parseForRange(String boundary) {
        String[] boundaries = split(boundary);
        List<Range> expects = Lists.newArrayList();
        for (String eachBoundary : boundaries) {
            if (StringUtils.contains(eachBoundary, RANGE_SEPARATOR)) {
                String[] range = split(eachBoundary, RANGE_SEPARATOR);
                if (ArrayUtils.isEmpty(range) || range.length != 2) { // 只支持2-10类似的格式
                    LOGGER.warn("boundary not standard : {}", boundary);
                    continue;
                }
                try {
                    long begin = Long.parseLong(range[0]);
                    long end = Long.parseLong(range[1]);
                    expects.add(Range.closed(begin, end));
                } catch (NumberFormatException e) {
                    LOGGER.warn("boundary exists non-number : {}", boundary);
                }
            } else {
                try {
                    long value = Long.parseLong(eachBoundary);
                    expects.add(Range.singleton(value));
                } catch (NumberFormatException e) {
                    LOGGER.warn("boundary exists non-number : {}", boundary);
                }
            }
        }
        return expects;
    }

    public static void main(String[] args) {
        List<Long> list = parseForLongList("224631");
        System.out.println(list);
        List<Long> list2 = parseForLongList("[\"224631\",\"2312999\"]");
        System.out.println(list2);
    }
}
