/*
 * Copyright (c) 2014 yy.com. 
 *
 * All Rights Reserved.
 *
 * This program is the confidential and proprietary information of 
 * YY.INC. ("Confidential Information").  You shall not disclose such
 * Confidential Information and shall use it only in accordance with
 * the terms of the license agreement you entered into with yy.com.
 */
package com.github.bh.aconf.utils;

import com.github.bh.aconf.domain.StaffInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * @author alvin hwang
 */
public final class AuthUtils {
    private static final String SESSION_ADMIN_USER_KEY = "admin_user";
    private AuthUtils() {
        // no-op
    }


    public static StaffInfo getAdminUser(HttpServletRequest request) {
        StaffInfo staffInfo = new StaffInfo();
        return staffInfo;
    }

    public static StaffInfo getAdminUser(String passport) {
        StaffInfo staffInfo = new StaffInfo();
        return staffInfo;
    }

}
