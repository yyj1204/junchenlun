package com.wktx.www.subjects.apiresult.manage;

/**
 * Created by yyj on 2018/2/8.
 * 我的工作---工作管理---投诉详情
 * {
 * "ret": 200,"msg": "",
 * "data": {
 *         "code":0,"msg":"",
 *         "info":{
 *                  "id": "19","hire_id": "443","uid": "2672",
 *                  "complaint_title": "具体","complaint_content": "屠龙记里",
 *                  "add_time": "2018-06-25 02:28:22","update_time": "0","status": "0","remark": null
 *                    }
 *          }
 * }
 */

public class ComplaintDetailsData {
    private int code;
    private String msg;
    private ComplaintDetailsInfoData info;

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
    public ComplaintDetailsInfoData getInfo() {
        return info;
    }
    public void setInfo(ComplaintDetailsInfoData info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "ComplaintDetailsData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
