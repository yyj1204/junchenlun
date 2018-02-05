package com.wktx.www.emperor.Model1.Login;

import java.io.Serializable;

public class LoginInfo implements Serializable
{
    private int ret;
    private String msg;
    private LoginInfoData data;

    public int getRet()
    {
        return this.ret;
    }

    public void setRet(int ret)
    {
        this.ret = ret;
    }

    public String getMsg()
    {
        return this.msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public LoginInfoData getData()
    {
        return this.data;
    }

    public void setData(LoginInfoData data)
    {
        this.data = data;
    }
}
