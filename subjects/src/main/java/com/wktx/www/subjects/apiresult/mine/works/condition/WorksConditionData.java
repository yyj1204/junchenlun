package com.wktx.www.subjects.apiresult.mine.works.condition;

/**
 * Created by 369 on 2018/6/1.
 * 我的作品---参数（类目、设计类型）
 */

public class WorksConditionData {
    private int code;
    private String msg;
    private WorksConditionInfoData info;

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

    public WorksConditionInfoData getInfo() {
        return info;
    }

    public void setInfo(WorksConditionInfoData info) {
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
