package com.github.bh.aconf.domain.webdb;

/**
 * @author xiaobenhai
 * Date: 2017/3/3
 * Time: 11:32
 */
public class SessionInfo {
    private String sid;
    private String asid;
    private String name;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getAsid() {
        return asid;
    }

    public void setAsid(String asid) {
        this.asid = asid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SessionInfo{" +
                "sid='" + sid + '\'' +
                ", asid='" + asid + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
