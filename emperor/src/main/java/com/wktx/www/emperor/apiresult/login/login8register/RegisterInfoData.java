package com.wktx.www.emperor.apiresult.login.login8register;

/**
 * Created by yyj on 2018/1/18.
 * 注册&&登录信息
 */

public class RegisterInfoData {

    private String user_id;
    private String token;
    private int is_new;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
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
