package com.wktx.www.emperor.apiresult.staff.complaint;

/**
 * Created by yyj on 2018/3/6.
 * 我的员工---管理员工---发起投诉信息
 * {
 * "ret": 200,"msg": "",
 * "data": {
 *         "code":0,"msg":"",
 *         "info":{
 *                "id": "16","complaint_title": "投诉问题","complaint_content": "投诉内容"
 *                }
 *          }
 * }
 */

public class ComplaintData {
    private int code;
    private String msg;
    private ComplaintInfoData info;

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

    public ComplaintInfoData getInfo() {
        return info;
    }

    public void setInfo(ComplaintInfoData info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "ComplaintData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
