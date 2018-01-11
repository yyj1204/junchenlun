package com.wktx.www.emperor.Model.Login;

import java.io.Serializable;

public class LoginInfoDataInfo implements Serializable
{
    private int user_id;
    private int is_new;
    private String token;

    public int getUser_id()
    {
        return this.user_id;
    }

    public void setUser_id(int user_id)
    {
        this.user_id = user_id;
    }

    public int getIs_new()
    {
        return this.is_new;
    }

    public void setIs_new(int is_new)
    {
        this.is_new = is_new;
    }

    public String getToken()
    {
        return this.token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public LoginInfoDataInfo(int user_id, String token)
    {
        this.user_id = user_id;
        this.token = token;
    }
}
