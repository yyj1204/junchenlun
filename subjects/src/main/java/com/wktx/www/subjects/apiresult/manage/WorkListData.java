package com.wktx.www.subjects.apiresult.manage;


import java.util.List;

/**
 * Created by yyj on 2018/2/8.
 * 我的工作、雇佣记录列表
 * {
 * "ret": 200,"msg": "",
 * "data": {
 *         "code":0,"msg":"",
 *         "info":[{
 *                    "hire_id": "392","nickname": "尚仁网络有限公司","address_from": "江苏省-常州市-武进区","status": "1","status_desc": "臣民拒绝邀请",
 *                    "dismissal_id": "0","head_pic": "http://shop.jcl.55085.cn/public/upload/head_pic/2018/04-12/201804121827586819.jpg",
 *                    "trusteeship_wages": "0.00","commission": "20", "project_start_time": "2018-07-02","project_end_time": "2018-08-01","tow": "客服"
 *                }]
 *          }
 * }
 */

public class WorkListData {
    private int code;
    private String msg;
    private List<WorkListInfoData> info;

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
    public List<WorkListInfoData> getInfo() {
        return info;
    }
    public void setInfo(List<WorkListInfoData> info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "WorkListData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
