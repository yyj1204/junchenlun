package com.wktx.www.subjects.apiresult.login.login8register;

/**
 * Created by yyj on 2018/1/18.
 * 注册&登录
 */

public class RegisterData {
    private int code;
    private String msg;
    private RegisterInfoData info;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public RegisterInfoData getInfo() {
        return info;
    }

    public void setInfo(RegisterInfoData info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "RegisterData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
