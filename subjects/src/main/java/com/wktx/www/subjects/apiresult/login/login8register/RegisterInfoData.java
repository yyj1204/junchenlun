package com.wktx.www.subjects.apiresult.login.login8register;

/**
 * Created by yyj on 2018/1/18.
 * 注册&&登录信息
 */

public class RegisterInfoData {

    private int user_id;
    private String token;
    private int is_new;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getIs_new() {
        return is_new;
    }

    public void setIs_new(int is_new) {
        this.is_new = is_new;
    }

    @Override
    public String toString() {
        return "RegisterInfoData{" +
                "user_id=" + user_id +
                ", token='" + token + '\'' +
                ", is_new=" + is_new +
                '}';
    }
}
