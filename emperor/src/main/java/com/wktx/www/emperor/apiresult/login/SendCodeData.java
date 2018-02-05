package com.wktx.www.emperor.apiresult.login;

/**
 * Created by yyj on 2018/1/17.
 * 发送验证码
 */

public class SendCodeData {
    private int code;
    private String msg;

    public int getCode()
    {
        return this.code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getMsg()
    {
        return this.msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "SendCodeData{" +
                "msg='" + msg + '\'' +
                ", code=" + code +
                '}';
    }
}
