package com.github.bh.aconf.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 版本号转化器。
 * <pre>
 * {@code
 * 将x.y.z转化成数字型。
 * a.b.c --> 每个版本号使用2位的十六进制表示 --> AABBCC  --> 再转成十进制
 * 如: 7.134.0  --> 7使用16进制: 07, 134使用16进制: 86, 0使用16进制: 00 --> 078600 --> 78600 --> 再转成10进制: 493056
 * }
 * </pre>
 *
 * @author xiaobenhai
 */
public final class VersionConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger(VersionConverter.class);

    private static final char VERSION_SEPARATOR = '.';
    private static final long MAX_LONG_VERSION_NUMBER = 16777215; // 16进制为：FFFFFF
    private static final int MIN_HEX_STRING_LENGTH = 5;
    private static final int MAX_HEX_STRING_LENGTH = 6;
    private static final long MAX_VERSION_NUMBER = 255; // 每个小号的最大容忍数字
    public static Pattern VERSION_PATTERN = Pattern.compile("(\\d+)(\\.\\d+)*");

    private VersionConverter() {
        // no-op
    }

    public static long convert(String version) {
    	if (StringUtils.isEmpty(version)) {
            LOGGER.warn("version string is empty, return 0");
            return 0;
        }
    	Matcher matcher = VERSION_PATTERN.matcher(version);
    	String temp = null;
		if (matcher.find()) {
			temp = matcher.group(0);
		} else {
			return 0;  //异常版本
		}
    	String[] versions = StringUtils.split(temp, VERSION_SEPARATOR);
        if (ArrayUtils.isEmpty(versions) || versions.length > 3) {
            LOGGER.warn("version string is not standard (major.minor.patch) : {}, return 0", version);
            return 0;
        }
        String majorHex = to2BitHexString(versions[0]);
        String minorHex = to2BitHexString(versions.length > 1 ? versions[1] : "0");
        String patchHex = to2BitHexString(versions.length > 2 ? versions[2] : "0");
        return toDecimalLongFromHex(majorHex + minorHex + patchHex);
    }
    
    private static String to2BitHexString(String str) {
        String hex = "0";
        try {
            int decimal = Integer.parseInt(str);
            if (decimal > MAX_VERSION_NUMBER) {
                LOGGER.warn("version number {} exceed {}", str, MAX_VERSION_NUMBER);
            } else {
                hex = Integer.toHexString(decimal);
            }
        } catch (NumberFormatException e) {
            LOGGER.warn("str is not number", e);
        }
        return StringUtils.leftPad(hex, 2, '0');
    }

    private static long toDecimalLongFromHex(String hex) {
        return Long.parseLong(hex, 16);
    }

    public static String parse(long versionNumber) {
        if (versionNumber <= 0 || versionNumber > MAX_LONG_VERSION_NUMBER) {
            LOGGER.warn("version number is {}, return empty string", versionNumber);
            return StringUtils.EMPTY;
        }
        String hex = Long.toHexString(versionNumber);
        if (StringUtils.isEmpty(hex) || hex.length() > MAX_HEX_STRING_LENGTH || hex.length() < MIN_HEX_STRING_LENGTH) {
            LOGGER.warn("version hex is {}, length is not right({}-{}), return empty string", hex, MIN_HEX_STRING_LENGTH, MAX_HEX_STRING_LENGTH);
            return StringUtils.EMPTY;
        }
        String majorHex = StringUtils.substring(hex, 0, hex.length() - 4);
        String minorHex = StringUtils.substring(hex, majorHex.length(), hex.length() - 2);
        String patchHex = StringUtils.substring(hex, -2, -1);
        int major = toDecimalIntFromHex(majorHex);
        int minor = toDecimalIntFromHex(minorHex);
        int patch = toDecimalIntFromHex(patchHex);
        return StringUtils.join(new int[]{major, minor, patch}, VERSION_SEPARATOR);
    }

    private static int toDecimalIntFromHex(String hex) {
        return Integer.parseInt(hex, 16);
    }

}
