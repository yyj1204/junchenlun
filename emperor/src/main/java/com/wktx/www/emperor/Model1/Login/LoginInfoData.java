package com.wktx.www.emperor.Model1.Login;

import java.io.Serializable;

public class LoginInfoData implements Serializable
{
    private String msg;
    private int code;
    private LoginInfoDataInfo info;

    public String getMsg()
    {
        return this.msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public int getCode()
    {
        return this.code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public LoginInfoDataInfo getInfo()
    {
        return this.info;
    }

    public void setInfo(LoginInfoDataInfo info)
    {
        this.info = info;
    }
}
