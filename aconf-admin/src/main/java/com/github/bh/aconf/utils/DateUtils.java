package com.github.bh.aconf.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author xiaobenhai
 * Date: 2017/3/1
 * Time: 17:06
 */
public class DateUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public static Date parse(String dateStr) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        try {
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            LOGGER.warn("parse formatted time failed. DateStr={}", dateStr);
        }
        return null;
    }

    public static String format(Date date) {
        if (date == null) {
            return null;
        }
        return dateFormat.format(date);
    }
}
