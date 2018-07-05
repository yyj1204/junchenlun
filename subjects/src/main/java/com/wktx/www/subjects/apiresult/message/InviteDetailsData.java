package com.wktx.www.subjects.apiresult.message;

/**
 * Created by yyj on 2018/2/8.
 * 公司邀请详情
 * {
 * "ret": 200,"msg": "",
 * "data": {
 *         "code":0,"msg":"",
 *         "info":{
 *                 "hire_id": "383","name": "美工","project_start_time": "2018-06-08 00:00:00",
 *                 "project_end_time": "2018-06-12 09:24:14","demand": "444","0","hire_price": "1.00",
 *                 "qq": "111","wechat": "111","phone": "","nickname": "劲氏软件",
 *                 "address_from": "北京市-市辖区-东城区","add_time": "2018-06-08 08:58:57",
 *                 "head_pic": "http://shop.jcl.55085.cn/public/upload/head_pic/2018/04-18/201804182015280419.jpg"
 *                    }
 *          }
 * }
 */

public class InviteDetailsData {
    private int code;
    private String msg;
    private InviteDetailsInfoData info;

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
    public InviteDetailsInfoData getInfo() {
        return info;
    }
    public void setInfo(InviteDetailsInfoData info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "FireDetailsData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
