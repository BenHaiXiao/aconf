package com.github.bh.aconf.domain.vo;

import java.util.Date;

/**
 * Created by wjc13 on 2017/2/17.
 */
public class UserVo {
    private Long id;
    private String passport;
    private String name;
    private String jobCode;
    private String telephone;
    private String email;
    private Integer role;
    private Date updateTime;
    private Date createTime;
    private Byte state;
    private String bssRole;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJobCode() {
        return jobCode;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public String getBssRole() {
		return bssRole;
	}

	public void setBssRole(String bssRole) {
		this.bssRole = bssRole;
	}

	@Override
    public String toString() {
        return "UserVo{" +
                "id=" + id +
                ", passport='" + passport + '\'' +
                ", name='" + name + '\'' +
                ", jobCode='" + jobCode + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", updateTime=" + updateTime +
                ", createTime=" + createTime +
                ", state=" + state +
                ", bssRole=" + bssRole +
                '}';
    }
}
