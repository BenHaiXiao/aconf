package com.github.bh.aconf.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xiaobenhai
 * Date: 2017/3/1
 * Time: 10:44
 * IP工具类
 */
public class IpUtils {
    private static final Pattern PATTERN_IP = Pattern.compile("(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)");
    private static final String IP_LOCALHOST = "127.0.0.1";

    /**
     * 获取用户的真实ip
     * 如果用了nginx做反向代理，getRemoteAddr获取到的ip实际上是本机ip
     * 若获取到的Ip不符合规范，则返回null
     *
     * @param request 用户请求
     * @return 真实ip（并不能保证100%真实，用户也可能有代理等）
     */
    public static String getRealIp(HttpServletRequest request) {
        String realIp = request.getHeader("X-Real-IP");
        if (StringUtils.isBlank(realIp)) {
            realIp = request.getRemoteAddr();
        }
        Matcher matcher = PATTERN_IP.matcher(realIp);
        if (matcher.find() && !realIp.equals(IP_LOCALHOST)) {
            return realIp;
        }
        return null;
    }
}
