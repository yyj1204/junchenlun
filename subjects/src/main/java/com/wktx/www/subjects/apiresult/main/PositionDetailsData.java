package com.wktx.www.subjects.apiresult.main;

/**
 * Created by yyj on 2018/5/8.
 * 职位详情
 * {
 *   "ret": 200,"msg": "",
 *   "data": {"code": 0,"msg": "",
 *         "info": {
 *              "id": "6","user_id": "2649","title": "6","content": "6","bgap_name": "淘宝","bgat_name": "服装内衣",
 *              "budget": "3.00","shop_name": "安时代的","nickname": "尚仁网络有限公司","address_from": "江苏省-常州市-武进区",
 *              "qq": "12347","weixin": "22","phone": "12345678907","add_time": "2018-03-08 15:53:02",
 *              "end_time": "2020-09-13 20:26:39","is_dated": "0",
 *              "shop_logo": "http://shop.jcl.55085.cn/public/upload/logo/2017/06-29/222e2f75798b8476e06aafe7c838f41b.png",
 *              "is_collect": 0}
 *              }
 * }
 */

public class PositionDetailsData {
    private int code;
    private String msg;
    private PositionDetailsInfoData info;

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

    public PositionDetailsInfoData getInfo() {
        return info;
    }

    public void setInfo(PositionDetailsInfoData info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "PositionDetailsData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
