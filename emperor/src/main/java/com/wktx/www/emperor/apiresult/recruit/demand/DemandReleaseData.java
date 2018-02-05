package com.wktx.www.emperor.apiresult.recruit.demand;

/**
 * Created by yyj on 2018/1/26.
 * 需求发布
 */

public class DemandReleaseData {
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
        return "DemandReleaseData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
