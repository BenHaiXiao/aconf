package com.github.bh.aconf.domain.webdb;

/**
 * @author xiaobenhai
 * Date: 2017/3/3
 * Time: 11:31
 */
public class UserInfo {
    private String uid;
    private String nickname;
    private String passport;
    private String yyno;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getYyno() {
        return yyno;
    }

    public void setYyno(String yyno) {
        this.yyno = yyno;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "uid='" + uid + '\'' +
                ", nickname='" + nickname + '\'' +
                ", passport='" + passport + '\'' +
                ", yyno='" + yyno + '\'' +
                '}';
    }
}
