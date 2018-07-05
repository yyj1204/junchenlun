package com.wktx.www.subjects.apiresult.mine.resume;


/**
 * Created by 369 on 2018/6/1.
 * 我的简历
 */

public class ResumeData {
    private int code;
    private String msg;
    private ResumeInfoData info;

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

    public ResumeInfoData getInfo() {
        return info;
    }

    public void setInfo(ResumeInfoData info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "ResumeData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
