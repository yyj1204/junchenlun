package com.wktx.www.emperor.apiresult.mine;


/**
 * Created by yyj on 2018/1/18.
 * 用户信息
 */

public class UserData {
    private int code;
    private String msg;
    private UserInfoData info;


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

    public UserInfoData getInfo() {
        return info;
    }

    public void setInfo(UserInfoData info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
