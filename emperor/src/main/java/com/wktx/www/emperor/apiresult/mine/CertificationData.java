package com.wktx.www.emperor.apiresult.mine;

/**
 * Created by yyj on 2018/2/13.
 * 账户认证（个人&店铺）
 */

public class CertificationData {
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
        return "CertificationData{" +
                "msg='" + msg + '\'' +
                ", code=" + code +
                '}';
    }
}
