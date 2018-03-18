package com.github.bh.aconf.domain.command;

/**
 * @author xiaobenhai
 * Date: 2017/2/10
 * Time: 11:59
 */
public class UserCommand {
    private String avatar;
    private String telephone;
    private String email;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserCommand{" +
                "avatar='" + avatar + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
