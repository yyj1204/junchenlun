package com.wktx.www.subjects.apiresult.message;


import java.util.List;

/**
 * Created by yyj on 2018/6/9.
 * 交易消息列表
 * {
 * "ret": 200,"msg": "",
 * "data": {
 *         "code":0,"msg":"",
 *         "info": [ "id": "8","hire_id": "232","amount": "4000.00","add_time": "2018-05-17 16:38:34","remark": "支付薪资", "nickname": "阿饭","tow": "客服"]
 *         }
 * }
 */

public class DealListData {
    private int code;
    private String msg;
    private List<DealListInfoData> info;

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
    public List<DealListInfoData> getInfo() {
        return info;
    }
    public void setInfo(List<DealListInfoData> info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "DealListData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
