package com.wktx.www.subjects.apiresult.manage;

/**
 * Created by yyj on 2018/6/25.
 * 我的工作详情
 * {"ret": 200,"msg": "",
 * "data": {"code": 0,"msg": "",
 *          "info": {
 *                 "hire_id": "410","nickname": "尚仁网络旗舰店","head_pic": "/public/upload/head_pic/2018/05-21/201805211507309664.jpeg", "commission": "0",
 *                 "sales_id": "0","project_start_time": "2018-06-23 00:00:00","project_end_time": "2018-07-22 23:59:59",dismissal_id": "28",tow:"客服",
 *                 "paid_wages": "0.00","status": "1",,"signIn": "1","reportNum": "0","sw_id": "26",
 *                 "arrangementWork": [{
 *                       "id": "4","report_id":"18","demand_title": "测试安排工作标题","demand_content": "测试安排工内容","shop_name": "安时代的",
 *                       "shop_logo": "http://shop.jcl.55085.cn/public/upload/logo/2017/06-29/222e2f75798b8476e06aafe7c838f41b.png",
 *                       "end_time": "2018-03-15 17:07:29"}]
 *                       }
 *          }
 * }
 */

public class WorkDetailsData {
    private int code;
    private String msg;
    private WorkDetailsInfoData info;

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

    public WorkDetailsInfoData getInfo() {
        return info;
    }

    public void setInfo(WorkDetailsInfoData info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "WorkDetailsData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
