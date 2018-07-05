package com.wktx.www.subjects.apiresult.main.condition;

/**
 * Created by 369 on 2018/6/1.
 * 参数（平台、风格、类目、职位、工作经验）
 */

public class ConditionData {
    private int code;
    private String msg;
    private ConditionInfoData info;

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

    public ConditionInfoData getInfo() {
        return info;
    }

    public void setInfo(ConditionInfoData info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "WorksConditionData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
