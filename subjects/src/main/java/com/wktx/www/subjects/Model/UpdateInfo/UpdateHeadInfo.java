package com.wktx.www.subjects.Model.UpdateInfo;

public class UpdateHeadInfo
{
    private int ret;
    private String msg;
    private UpdateHeadInfoData data;

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

    public UpdateHeadInfoData getData()
    {
        return this.data;
    }

    public void setData(UpdateHeadInfoData data)
    {
        this.data = data;
    }
}
