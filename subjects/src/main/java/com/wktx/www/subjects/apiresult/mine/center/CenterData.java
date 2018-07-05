package com.wktx.www.subjects.apiresult.mine.center;

/**
 * Created by yyj on 2018/1/18.
 * 个人中心
 */

public class CenterData {
    private int code;
    private String msg;
    private CenterInfoData info;

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

    public CenterInfoData getInfo() {
        return info;
    }

    public void setInfo(CenterInfoData info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "CenterData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
