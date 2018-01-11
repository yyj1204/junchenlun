package com.wktx.www.subjects.Model.Service;

public class ServiceInfoData {
    private String msg;
    private int code;
    private ServiceInfoDataInfo info;

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ServiceInfoDataInfo getInfo() {
        return this.info;
    }

    public void setInfo(ServiceInfoDataInfo info) {
        this.info = info;
    }
}
