package com.wktx.www.emperor.apiresult.mine;

/**
 * Created by yyj on 2018/1/18.
 * 用户信息内容
 */

public class UserInfoData {

    private int user_id;
    private String username;
    private String nickname;
    private String avatar;
    private int sex;//0:保密 1:男 2:女

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "UserInfoData{" +
                "user_id=" + user_id +
                ", username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", sex=" + sex +
                '}';
    }
}
