package com.github.bh.aconf.domain.command;

/**
 * @author xiaobenhai
 * Date: 2017/2/22
 * Time: 20:21
 * 管理用户command
 */
public class AdminUserCommand {
    private String passport;
    private String telephone;
    private Integer role;
    private String bssRole;
    
    public String getBssRole() {
		return bssRole;
	}

	public void setBssRole(String bssRole) {
		this.bssRole = bssRole;
	}

	public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "AdminUserCommand{" +
                "passport='" + passport + '\'' +
                ", telephone='" + telephone + '\'' +
                ", role=" + role +
                ", bssRole=" + bssRole +
                '}';
    }
}
