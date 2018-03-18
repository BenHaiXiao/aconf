package com.github.bh.aconf.domain.vo;

/**
 * @author xiaobenhai
 * Date: 2016/12/28
 * Time: 9:42
 */
public class AppRegisterVo {
    private String name;
    private Long appid;
    private String token;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAppid() {
        return appid;
    }

    public void setAppid(Long appid) {
        this.appid = appid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "AppRegisterVo{" +
                "name='" + name + '\'' +
                ", appid=" + appid +
                ", token='" + token + '\'' +
                '}';
    }
}
