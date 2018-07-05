package com.wktx.www.emperor.apiresult.main.home;

import java.util.List;

/**
 * Created by yyj on 2018/3/24.
 * 职业类型
 * {
 *   "ret": 200,"msg": "",
 *   "data": {
 *      "code": 0,"msg": "",
 *      "info": [{"id": "1","name": "美工","picture": "http://shop.jcl.55085.cn/public/upload/logo/2018/03-24/ef6065111eddefc2f06374aadb0784eb.png"}]
 *      }
 *}
 */

public class JobTypeData {
    private int code;
    private String msg;
    private List<JobTypeInfoData> info;

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

    public List<JobTypeInfoData> getInfo() {
        return info;
    }

    public void setInfo(List<JobTypeInfoData> info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "JobTypeData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
