package com.github.bh.aconf.constants;

import com.google.common.collect.Maps;

import java.util.EnumSet;
import java.util.Map;

/**
 * @author xiaobenhai
 * Date: 2016/11/3
 * Time: 15:27
 */
public enum BusinessStatus {
    LOST_REQUIRED_PARAM(101, "lost required param"),  //缺少必填参数
    RELOAD_CONFIG_ERROR(102, "reload config error"),   //重载配置失败
    CREATE_NEW_ELEMENT_ERROR(103, "create new element error"),  //创建新业务时发生错误
    DELETE_ELEMENT_ERROR(104, "delete element error"),  //删除元素失败
    UPDATE_ELEMENT_ERROR(105, "update element error"),  //更新元素失败
    NO_THIS_ELEMENT(118, "no this element"),            //查无此元素
    NO_ANY_ELEMENT(119, "don't have any element, please create first"),            //查无此元素
    INVALID_BSS_ID(106, "invalid bss id"),   //无效的bssId
    INVALID_BSS_CODE(107, "invalid bss code"),   //无效的bssId
    NO_THIS_BSS(108, "no this bss"),   //无此业务
    COMMAND_IS_NULL(109, "command is null"),   //参数为空，不合法，无法操作
    NO_NEED_PARAMS(110, "the request contains no need params"),  //参数包含无效参数
    INVALID_CONFIG_ID(111, "invalid config id"),
    NO_THIS_CONFIG(112, "no this config"),   //无此配置项
    INVALID_FILTER_ID(113, "invalid filter id"),
    NO_THIS_FILTER(114, "no this filter"),   //无此配置项
    APP_REGISTER_ERROR(115, "can not register new app"),   //无法注册新的app
    AUTH_FAILED(116, "auth failed"),   //鉴权失败
    DUPLICATE_KEY(117, "duplicate key"),  //重复的配置项key
    INVALID_PARAMS(120, "invalid params"),  //无效参数
    NETWORK_ERROR(121, "network error"), //网络错误
    USER_ALREADY_ADDED(122, "user already added error"), //用户已经添加
    SUCCESS(200, "success"),
    RELOAD_CONFIG_SUCCESS(202, "reload config success"), //重载配置成功
    UNKNOWN(99, "unknown error");  //未知错误

    private Integer code;
    private String msg;

    private static Map<Integer, BusinessStatus> businessStatusMap = Maps.newHashMap();

    static {
        for (BusinessStatus filterType : EnumSet.allOf(BusinessStatus.class)) {
            businessStatusMap.put(filterType.code, filterType);
        }
    }

    BusinessStatus(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer code() {
        return code;
    }

    public String msg() {
        return msg;
    }

    public static BusinessStatus valueOf(Integer code) {
        BusinessStatus businessStatus = businessStatusMap.get(code);
        if (businessStatus != null) {
            return businessStatus;
        }
        return UNKNOWN;
    }

    public boolean compare(Integer c) {
        return c != null && code.equals(c);
    }

    public Map<String, String> toMap() {
        Map<String, String> map = Maps.newHashMap();
        map.put("code", String.valueOf(code));
        map.put("msg", msg);
        return map;
    }

}
