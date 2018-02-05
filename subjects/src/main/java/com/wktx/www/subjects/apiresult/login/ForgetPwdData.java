package com.wktx.www.subjects.apiresult.login;

/**
 * Created by yyj on 2018/1/17.
 * 忘记密码
 */

public class ForgetPwdData {
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
        return "ForgetPwdData{" +
                "msg='" + msg + '\'' +
                ", code=" + code +
                '}';
    }
}
