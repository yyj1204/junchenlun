package com.wktx.www.subjects.apiresult.login;


import java.io.Serializable;

/**
 * Created by yyj on 2018/01/18.
 * 账户信息
 */
public class AccountInfoData implements Serializable {

    private int user_id;//用户id
    private String token;//用户token
    private String phone;//用户phone
    private int is_new;//是否新用户

    public AccountInfoData() {
    }

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getIs_new() {
        return is_new;
    }

    public void setIs_new(int is_new) {
        this.is_new = is_new;
    }

    @Override
    public String toString() {
        return "AccountInfoData{" +
                "user_id=" + user_id +
                ", token='" + token + '\'' +
                ", is_new=" + is_new +
                '}';
    }
}
