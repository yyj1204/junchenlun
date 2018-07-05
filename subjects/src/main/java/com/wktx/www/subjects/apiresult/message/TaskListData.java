package com.wktx.www.subjects.apiresult.message;


import java.util.List;

/**
 * Created by yyj on 2018/2/8.
 * 任务消息列表
 * {
 * "ret": 200,"msg": "",
 * "data": {
 *         "code":0,"msg":"",
 *         "info":[{
 *                    "id": "4","report_id":"18","dismissal_id": "0","status": "1","demand_title": "测试安排工作标题",
 *                    "demand_content": "测试安排工内容","shop_name": "安时代的",
 *                    "shop_logo": "http://shop.jcl.55085.cn/public/upload/logo/2017/06-29/222e2f75798b8476e06aafe7c838f41b.png",
 *                    "end_time": "2018-03-15 17:07:29"}]
 *          }
 * }
 */

public class TaskListData {
    private int code;
    private String msg;
    private List<TaskListInfoData> info;

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
    public List<TaskListInfoData> getInfo() {
        return info;
    }
    public void setInfo(List<TaskListInfoData> info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "TaskListData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
