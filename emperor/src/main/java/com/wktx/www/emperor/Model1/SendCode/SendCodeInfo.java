package com.wktx.www.emperor.Model1.SendCode;

public class SendCodeInfo
{
    private int ret;
    private String msg;
    private SendCodeInfoData data;

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

    public SendCodeInfoData getData()
    {
        return this.data;
    }

    public void setData(SendCodeInfoData data)
    {
        this.data = data;
    }
}
