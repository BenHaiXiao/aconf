package com.github.bh.aconf.constants;

import com.google.common.collect.Maps;

import java.util.EnumSet;
import java.util.Map;

/**
 * @author xiaobenhai
 * Date: 2017/2/9
 * Time: 15:59
 */
public enum UserRole {
    USER(2, "ROLE_USER", "普通用户", 1),
    ADMIN(1, "ROLE_ADMIN", "管理员", 2),
    CREATOR(0, "ROLE_CREATOR", "超级管理员", 3),
    UNKNOWN(-1, "", "", 0);

    UserRole(int code, String role, String name, int seq) {
        this.code = code;
        this.role = role;
        this.roleName = name;
        this.seq = seq;
    }

    private int code;
    private String role;
    private String roleName;
    private int seq;

    private static Map<String, UserRole> roleMap = Maps.newHashMap();
    private static Map<Integer, UserRole> roleCodeMap = Maps.newHashMap();

    static {
        for (UserRole userRole : EnumSet.allOf(UserRole.class)) {
            roleMap.put(userRole.role, userRole);
            roleCodeMap.put(userRole.code, userRole);
        }
    }

    public int code() {
        return code;
    }

    public String role() {
        return role;
    }

    public String roleName() {
        return roleName;
    }

    public static UserRole find(String role) {
        String[] roles = role.split(",");
        UserRole result = UNKNOWN;

        for (String roleResult : roles) {
            UserRole userRole = roleMap.get(roleResult);
            if (userRole != null && result.seq < userRole.seq) {
                result = userRole;
            }
        }
        return result;
    }

    public static UserRole find(Integer code) {
        if (code != null) {
            UserRole userRole = roleCodeMap.get(code);
            if (userRole != null) {
                return userRole;
            }
        }
        return UNKNOWN;
    }

    public boolean compare(String role) {
        return role != null && this.role.equals(role);
    }
}
