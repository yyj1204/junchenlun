package com.wktx.www.emperor.apiresult.recruit.resume;

import java.util.List;

/**
 * Created by yyj on 2018/2/8.
 * 简历---评价列表
 * {
 * "ret": 200,"msg": "",
 * "data": {
 *         "code":0,"msg":"",
 *         "info":[{
 *                 "username": "ph_da92e307ae9ce43843c563119cdd00e7","nickname": "阿饭","id": "11","uid": "2649","service_attitude": "5",
 *                 "ability": "4","response_speed": "3","images": [],"evaluation_content": "很后悔","add_time": "1531813392"
 *                }]
 *          }
 * }
 */

public class EvaluateData {
    private int code;
    private String msg;
    private List<EvaluateInfoData> info;

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
    public List<EvaluateInfoData> getInfo() {
        return info;
    }
    public void setInfo(List<EvaluateInfoData> info) {
        this.info = info;
    }


    @Override
    public String toString() {
        return "EvaluateData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", info=" + info +
                '}';
    }
}
