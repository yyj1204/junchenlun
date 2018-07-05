package com.wktx.www.subjects.apiresult;

/**
 * Created by yyj on 2018/1/17.
 */

public class CommonSimpleData {
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
        return "CommonSimpleData{" +
                "msg='" + msg + '\'' +
                ", code=" + code +
                '}';
    }
}
