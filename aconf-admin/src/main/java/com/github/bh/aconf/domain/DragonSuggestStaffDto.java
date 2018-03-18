package com.github.bh.aconf.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

/**
 * @author xiaobenhai
 * Date: 2017/2/22
 * Time: 17:01
 */
public class DragonSuggestStaffDto {
    @JsonProperty("user_name")
    @SerializedName("user_name")
    private String username;
    @JsonProperty("nick_name")
    @SerializedName("nick_name")
    private String nickname;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "DragonSuggestStaffDto{" +
                "username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
