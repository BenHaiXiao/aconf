package com.github.bh.aconf.domain.vo;

/**
 * @author xiaobenhai
 * Date: 2017/2/22
 * Time: 16:59
 * 与潜龙提示一致的员工信息vo
 */
public class DragonSuggestStaffVo {
    private String passport;
    private String realName;

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    @Override
    public String toString() {
        return "DragonSuggestStaffVo{" +
                "passport='" + passport + '\'' +
                ", realName='" + realName + '\'' +
                '}';
    }
}
