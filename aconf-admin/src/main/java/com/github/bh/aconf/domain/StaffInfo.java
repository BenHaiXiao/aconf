package com.github.bh.aconf.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

/**
 * hiido udb 返回的员工信息
 * <p>
 * Created by lixiliang
 * on 2015/1/21.
 */
public class StaffInfo {
    @JsonProperty("job_code")
    @SerializedName("job_code")
    private String jobCode;
    private String email;
    private String passport;
    @JsonProperty("nickname")
    @SerializedName("nickname")
    private String nickName;
    @JsonProperty("realname")
    @SerializedName("realname")
    private String realName;
    @JsonProperty("dept_name")
    @SerializedName("dept_name")
    private String deptName;
    private String role;
    private String avatar;
    private String telephone;
    private Long userId;

    public String getJobCode() {
        return jobCode;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "StaffInfo{" +
                "jobCode='" + jobCode + '\'' +
                ", email='" + email + '\'' +
                ", passport='" + passport + '\'' +
                ", nickName='" + nickName + '\'' +
                ", realName='" + realName + '\'' +
                ", deptName='" + deptName + '\'' +
                ", role='" + role + '\'' +
                ", avatar='" + avatar + '\'' +
                ", telephone='" + telephone + '\'' +
                ", userId=" + userId +
                '}';
    }
}
