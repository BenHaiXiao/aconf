package com.github.bh.aconf.service.domain;

/**
 * @author xiaobenhai
 * Date: 2017/2/14
 * Time: 19:17
 */
public enum ResponseCodes {

    SUCCESS(0, "成功"),

    NOPASS(1, "没通过过滤器规则"),

    INEFFECTIVE(2, "还没生效"),

    // 通用错误
    FAIL(99, "失败");


    private int code;
    private String message;

    ResponseCodes(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
